package com.example.demo;

import cn.hutool.core.util.RuntimeUtil;
import com.example.demo.client.TerraformClient;
import com.example.demo.client.TerraformOptions;
import com.example.demo.entity.message.ChangeSummary;
import com.example.demo.processor.PlanJsonProcessor;
import com.example.demo.processor.ProcessActuator;
import com.example.demo.processor.TestProcessor;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
class CommandTests {

    @Test
    void contextLoads() {
    }

    /**
     * terraform plan -no-color -out=./tfplan -json -var-file=./values.tfvars.json && terraform show -no-color -json ./tfplan
     *
     * @throws Exception Exception
     */
    @Test
    public void testPlanJson() throws Exception {
        TerraformOptions options = new TerraformOptions();
        try (TerraformClient terraformClient = new TerraformClient(options)) {

            PlanJsonProcessor processor = new PlanJsonProcessor();
            terraformClient.setOutputListener(processor::parsePlanJson);
            terraformClient.setErrorListener(System.out::println);

            File file = new File("./tmp");
            terraformClient.setWorkingDirectory(file);

            terraformClient.planJson().get();

            System.out.println("processor.isSuccessful() -> " + processor.isSuccessful()); // false ??
            System.out.println("processor.isCompleted() -> " + processor.isCompleted()); // false ??

            String planJson = processor.getPlanJson();
            System.out.println("planJson -> " + planJson); // null ??
            ChangeSummary summary = processor.getChangeSummary();
            System.out.println("summary -> " + summary.toString());
        }
    }

    @Test
    void test02() throws Exception {
        var processBuilder = new ProcessBuilder();

//        processBuilder.command("terraform", "init", "&&", "terraform", "plan");
//        processBuilder.command("terraform", "init", "&&", "terraform", "plan", "-no-color", "-out=./tfplan", "-json", "-var-file=./values.tfvars.json");
//        processBuilder.command("terraform", "plan", "-no-color", "-out=./tfplan", "-json", "-var-file=./values.tfvars.json");
//        processBuilder.command("bash", "-c", "ls -l | grep log");
//        processBuilder.command("bash", "-c", "terraform init && terraform plan -no-color -out=./tfplan -json -var-file=./values.tfvars.json");

        final List<String> command01 = Arrays.asList("terraform", "init", "-no-color", "-plugin-dir=./Users/flyhy/workspace/demo/tools/build/resources/main/huaweicloud");
        processBuilder.command(command01);

        processBuilder.directory(new File("./tmp"));

        var process = processBuilder.start();

        System.out.println("\n=======================\n");

        var planJsonProcessor = new PlanJsonProcessor();
        try (var reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {

            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                planJsonProcessor.parsePlanJson(line);
            }
        }

        try (var reader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
        process.waitFor();
        System.out.println("\n=======================\n");
    }

    @Test
    void Test03() throws Exception {


        ClassPathResource resource = new ClassPathResource("plugins");
        String absolutePath = resource.getFile().getAbsolutePath();

        final List<String> command01 = Arrays.asList("terraform", "init", "-no-color", "-plugin-dir=" + absolutePath);
        final List<String> command02 = Arrays.asList("terraform", "plan", "-no-color", "-out=./tfplan", "-json", "-var-file=./values.tfvars.json");
        final List<String> command03 = Arrays.asList("terraform", "show", "-no-color", "-json", "./tfplan");

        LinkedList<List<String>> commands = new LinkedList<>();
        commands.add(command01);
        commands.add(command02);
        commands.add(command03);

        TestProcessor processor = new TestProcessor();
        ProcessActuator.syncSeqExecution(commands, processor);

        if (processor.isHasErr()) {
            System.out.println(processor.getErrMsg());
        }

        if (processor.isCompleted()) {
            System.out.println(processor.getPlanJson());
            System.out.println(processor.getChangeSummary());
        }

    }

    @Test
    void HutoolTest() throws Exception {
        File workspace = new File("./tmp");

//        String command = "ls -l | grep log";
        String command = "bash -c ls -l | grep log";
//        String command = "terraform init";
//        String command = "terraform init && terraform plan -no-color -out=./tfplan -json -var-file=./values.tfvars.json";
        Process process = RuntimeUtil.exec(null, workspace, command);

        try (var reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {

            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println("ok -> " + line);
            }
        }

        try (var reader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("err -> " + line);
            }
        }
        process.waitFor();
    }

    @Test
    void ApacheCommandTest() throws Exception {
        File workspace = new File("./tmp");

        CommandLine cmdLine = new CommandLine("bash");
        cmdLine.addArgument("-c");
        cmdLine.addArgument("terraform plan -no-color -out=./tfplan -json -var-file=./values.tfvars.json && terraform show -no-color -json ./tfplan", false);

        // 创建执行器，在输出流中打印输出并带有前缀
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream, errorStream);

        DefaultExecutor executor = new DefaultExecutor();
        executor.setWorkingDirectory(workspace);
        executor.setStreamHandler(streamHandler);

        DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();

        // 执行命令
        executor.execute(cmdLine, resultHandler);

        resultHandler.waitFor();

        TestProcessor processor = new TestProcessor();
        try (var reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(outputStream.toByteArray())))) {
            String line;

            while ((line = reader.readLine()) != null) {
//                System.out.println("ok -> " + line);
                processor.parse(line);
            }
        }

        try (var reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(errorStream.toByteArray())))) {
            String line;
            while ((line = reader.readLine()) != null) {
//                System.out.println("err -> " + line);
                processor.parseError(line);
            }
        }

        if (processor.isHasErr()) {
            System.out.println(processor.getErrMsg());
        }

        if (processor.isCompleted()) {
            System.out.println(processor.getPlanJson());
            System.out.println(processor.getChangeSummary());
        }
    }
}
