package com.example.demo;

import cn.hutool.core.util.RuntimeUtil;
import com.example.demo.client.TerraformClient;
import com.example.demo.client.TerraformOptions;
import com.example.demo.entity.message.ChangeSummary;
import com.example.demo.processor.PlanJsonProcessor;
import com.example.demo.processor.ProcessActuator;
import com.example.demo.processor.TestProcessor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {

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
            terraformClient.setStartTask(System.out::println);
            terraformClient.setOutputListener(processor::parsePlanJson);
            terraformClient.setErrorListener(System.out::println);
            terraformClient.setEndTask(System.out::println);

            File file = new File("./tmp");
            terraformClient.setWorkingDirectory(file);

            terraformClient.planJson().get();

            String planJson = processor.getPlanJson();
            System.out.println(planJson);
            ChangeSummary summary = processor.getChangeSummary();
            System.out.println(summary);
        }
    }

    @Test
    void test01() throws Exception {
        var processBuilder = new ProcessBuilder();

//        processBuilder.command("terraform", "plan", "-no-color", "-out=./tfplan", "-json", "&&", "terraform", "show", "-no-color", "-json", "./tfplan");

//        processBuilder.command("terraform", "plan", "-no-color", "-out=./tfplan", "-json");
//        processBuilder.command("terraform", "plan", "-no-color -out=./tfplan -json");
//        processBuilder.command("terraform", "plan", "-no-color", "-out=./tfplan", "-json", "-var", "'ecs_name=test_name'", "'ecs_admin_passwd=123123'");
//        processBuilder.command("terraform", "plan", "-no-color", "-out=./tfplan", "-json", "-var 'ecs_name=test_name' 'ecs_admin_passwd=123123'");
//        processBuilder.command("terraform", "plan", "-no-color", "-out=./tfplan", "-json", "-var-file=./values.tfvars");
        processBuilder.command("terraform", "plan", "-no-color", "-out=./tfplan", "-json", "-var-file=./values.tfvars.json");

        processBuilder.directory(new File("./tmp"));

        var process = processBuilder.start();

        System.out.println("\n=======================\n");
        try (var reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {

            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
        System.out.println("\n=======================\n");
    }

    @Test
    void test02() throws Exception {
        var processBuilder = new ProcessBuilder();

//        processBuilder.command("terraform", "init", "&&", "terraform", "plan");
//        processBuilder.command("terraform", "init", "&&", "terraform", "plan", "-no-color", "-out=./tfplan", "-json", "-var-file=./values.tfvars.json");
//        processBuilder.command("terraform", "plan", "-no-color", "-out=./tfplan", "-json", "-var-file=./values.tfvars.json");
//        processBuilder.command("bash", "-c", "ls -l | grep log");
        processBuilder.command("bash", "-c", "terraform init && terraform plan -no-color -out=./tfplan -json -var-file=./values.tfvars.json");

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

        final List<String> command01 = Arrays.asList("terraform", "init", "-no-color");
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

        String command = "ls -l | grep log";
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
}
