package com.NamVu.concurrency_lab.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@Slf4j
public class IsolationLevelTest {

    @Autowired
    private ReadService readService;

    @Autowired
    private UpdateService updateService;

    @Test
    void non_repeatable_read_should_happen() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // TX 1
        executor.submit(() -> readService.readOnce("First"));

        Thread.sleep(1000);

        // TX 2
        executor.submit(() -> updateService.decreaseStock());

        Thread.sleep(1000);

        // TX 3
        executor.submit(() -> readService.readOnce("Second"));

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }
}
