package com.NamVu.concurrency_lab.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@Slf4j
public class ContentionTest {

    @Autowired
    private ContentionService contentionService;

    @Test
    void lock_contention_should_slow_system() throws Exception {
        int threadCount = 20;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        long start = System.currentTimeMillis();

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    contentionService.updateStock(1L);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        long duration = System.currentTimeMillis() - start;
        log.info("Total time = {} ms", duration);
    }
}
