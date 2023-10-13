package com.example.demo.processor;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class ProcessActuator {

    public static final String TMP_WORKING_DIR = "./tmp";

    public static <T extends BaseProcessor> void syncSeqExecution(LinkedList<List<String>> commands, T t) throws Exception {
        syncSeqExecution(commands, null, t);
    }

    public static <T extends BaseProcessor> void syncSeqExecution(LinkedList<List<String>> commands, String workingDirPath, T t) throws Exception {
        if (StringUtils.isBlank(workingDirPath)) {
            workingDirPath = TMP_WORKING_DIR;
        }

        LinkedList<ProcessBuilder> pbs = new LinkedList<>();
        for (List<String> command : commands) {
            if (command.isEmpty()) {
                break;
            }

            ProcessBuilder pb = new ProcessBuilder(command);
            pb.directory(new File(workingDirPath));
            pbs.add(pb);
        }

        for (ProcessBuilder pb : pbs) {
            System.out.println("Executing command: " + pb.command());
            Process process = pb.start();
            try (var reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("ok -> " + line);
                    t.parse(line);
                }
            }
            try (var reader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("err -> "+ line);
                    t.parseError(line);
                }
            }
            process.waitFor();
            if (t.isHasErr()) {
                break;
            }
        }

    }
}
