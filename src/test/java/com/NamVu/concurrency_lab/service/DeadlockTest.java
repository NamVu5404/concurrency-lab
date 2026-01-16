package com.NamVu.concurrency_lab.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class DeadlockTest {

    @Autowired
    private DeadlockService deadlockService;

    @Test
    void deadlock_should_happen() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> deadlockService.txA());
        executor.submit(() -> deadlockService.txB());

        Thread.sleep(5000);
    }
}
