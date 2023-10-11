package com.example.demo;

import com.example.demo.client.TerraformClient;
import com.example.demo.client.TerraformOptions;
import com.example.demo.entity.message.ChangeSummary;
import com.example.demo.processor.PlanJsonProcessor;
import com.example.demo.processor.ProcessActuator;
import com.example.demo.processor.TestProcessor;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
    public void testC() {
        LinkedList<CompletableFuture<Void>> futureList = new LinkedList<>();

        // 添加CompletableFuture到列表中
        futureList.add(CompletableFuture.runAsync(() -> System.out.println("Task 1")));
        futureList.add(CompletableFuture.runAsync(() -> System.out.println("Task 2")));
        futureList.add(CompletableFuture.runAsync(() -> System.out.println("Task 3")));

        // 串行调用CompletableFutures
        CompletableFuture<Void> finalFuture = CompletableFuture.completedFuture(null);

        for (CompletableFuture<Void> future : futureList) {
            finalFuture = finalFuture.thenCompose((v) -> future);
        }

        // 等待所有任务完成
        try {
            finalFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    void test1() throws Exception {
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
    void test2() throws Exception {
        var processBuilder = new ProcessBuilder();

        processBuilder.command("terraform", "plan", "-no-color", "-out=./tfplan", "-json", "-var-file=./values.tfvars.json");

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
        System.out.println("\n=======================\n");
    }

    @Test
    void test3() throws Exception {

        LinkedList<ProcessBuilder> processBuilderList = new LinkedList<>();

        var processBuilder1 = new ProcessBuilder();
        processBuilder1.command("terraform", "init", "-no-color");
        processBuilder1.directory(new File("./tmp"));

        var processBuilder2 = new ProcessBuilder();
        processBuilder2.command("terraform", "plan", "-no-color", "-out=./tfplan", "-json", "-var-file=./values.tfvars.json");
        processBuilder2.directory(new File("./tmp"));

        var processBuilder3 = new ProcessBuilder();
        processBuilder3.command("terraform", "show", "-no-color", "-json", "./tfplan");
        processBuilder3.directory(new File("./tmp"));

//        processBuilderList.add(processBuilder1);
        processBuilderList.add(processBuilder2);
        processBuilderList.add(processBuilder3);

        // 串行调用
        var planJsonProcessor = new PlanJsonProcessor();
        for (int i = 0; i < processBuilderList.size(); i++) {
            ProcessBuilder builder = processBuilderList.get(i);
            var process = builder.start();
            System.out.println("\n=========== index: " + i + "============\n");
            try (var reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {

                String line;

                while ((line = reader.readLine()) != null) {
                    if (StringUtils.isNotBlank(line)) {
//                        System.out.println(line);
                        planJsonProcessor.parsePlanJson(line);
                    }
                }
            }

            process.waitFor();

//            System.out.println("index : " + i + " error -> " + planJsonProcessor.getErrorBuilder().toString());

        }
        if (planJsonProcessor.isSuccessful() && planJsonProcessor.isCompleted()) {
            System.out.println(planJsonProcessor.getPlanJson());
            System.out.println(planJsonProcessor.getChangeSummary());
        }
    }

    @Test
    void test4() throws Exception {

        LinkedList<ProcessBuilder> processBuilderList = new LinkedList<>();

        var processBuilder1 = new ProcessBuilder();
        processBuilder1.command("terraform", "init", "-no-color");
        processBuilder1.directory(new File("./tmp"));

        var processBuilder2 = new ProcessBuilder();
        processBuilder2.command("terraform", "plan", "-no-color", "-out=./tfplan", "-json", "-var-file=./values.tfvars.json");
        processBuilder2.directory(new File("./tmp"));

        var processBuilder3 = new ProcessBuilder();
        processBuilder3.command("terraform", "show", "-no-color", "-json", "./tfplan");
        processBuilder3.directory(new File("./tmp"));

        processBuilderList.add(processBuilder1);
        processBuilderList.add(processBuilder2);
        processBuilderList.add(processBuilder3);

        // 串行调用
        var planJsonProcessor = new TestProcessor();
        for (int i = 0; i < processBuilderList.size(); i++) {
            ProcessBuilder builder = processBuilderList.get(i);
            var process = builder.start();
            System.out.println("\n=========== index: " + i + "============\n");
            try (var reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {

                String line;

                while ((line = reader.readLine()) != null) {
                    if (StringUtils.isNotBlank(line)) {
//                        System.out.println(line);
                        planJsonProcessor.parse(line);
                    }
                }
            }

            process.waitFor();

//            System.out.println("index : " + i + " error -> " + planJsonProcessor.getErrorBuilder().toString());

        }
        if (planJsonProcessor.isHasErr()) {
            System.out.println(planJsonProcessor.getErrMsg());
        }

        if (!planJsonProcessor.isHasErr() && planJsonProcessor.isCompleted()) {
            System.out.println(planJsonProcessor.getPlanJson());
            System.out.println(planJsonProcessor.getChangeSummary());
        }
    }

    @Test
    void Test05() throws Exception {

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
}
