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
public class IsolationLevelTest {

    @Autowired
    private ReadService readService;

    @Autowired
    private UpdateService updateService;

    @Autowired
    private ProductReadService productReadService;

    @Autowired
    private ProductInsertService productInsertService;

    @Test
    void repeatable_read_across_transactions() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(2);

        executor.submit(() -> {
            try {
                readService.readTwice(); // TX 1
            } finally {
                latch.countDown();
            }
        });

        Thread.sleep(1000); // đảm bảo read lần 1 xong

        executor.submit(() -> {
            try {
                updateService.decreaseStock(); // TX 2
            } finally {
                latch.countDown();
            }
        });

        latch.await();
    }

    @Test
    void phantom_read_should_happen() throws Exception {

        ExecutorService executor = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(2);

        executor.submit(() -> {
            try {
                productReadService.readCountTwice();
            } finally {
                latch.countDown();
            }
        });

        Thread.sleep(1000);

        executor.submit(() -> {
            try {
                productInsertService.insertProduct();
            } finally {
                latch.countDown();
            }
        });

        latch.await();
    }
}
