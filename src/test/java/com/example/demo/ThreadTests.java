package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class ThreadTests {

    @Test
    void contextLoads() {
    }

    @Test
    void test01() throws Exception {
        final ExecutorService executor = Executors.newWorkStealingPool();

        LinkedList<CompletableFuture<Void>> futureList = new LinkedList<>();

        futureList.add(CompletableFuture.runAsync(() -> {
            System.out.println("Future 1");
            executor.submit(() -> System.out.println("Task 1"));
            executor.submit(() -> System.out.println("Task 2"));
        }));
        futureList.add(CompletableFuture.runAsync(() -> {
            System.out.println("Future 2");
            executor.submit(() -> System.out.println("Task 3"));
            executor.submit(() -> System.out.println("Task 4"));
        }));
        futureList.add(CompletableFuture.runAsync(() -> {
            System.out.println("Future 3");
            executor.submit(() -> System.out.println("Task 5"));
            executor.submit(() -> System.out.println("Task 6"));
        }));

        CompletableFuture<Void> finalFuture = CompletableFuture.completedFuture(null);

        for (CompletableFuture<Void> future : futureList) {
            finalFuture = finalFuture.thenCompose((v) -> future);
        }

        // 等待所有任务完成
        try {
            finalFuture.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
