package com.NamVu.concurrency_lab.service;

import com.NamVu.concurrency_lab.domain.Product;
import com.NamVu.concurrency_lab.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
@Slf4j
public class OrderServiceConcurrencyTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void oversell_should_happen() throws Exception {
        Long productId = productRepository.findAll().getFirst().getId();

        int threadCount = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger failCount = new AtomicInteger();

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    orderService.buyWithRetry(productId);
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    log.warn("Optimistic lock conflict");
                    failCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Product product = productRepository.findById(productId).orElseThrow();

        log.info("===== RESULT =====");
        log.info("Success orders = {}", successCount.get());
        log.info("Fail orders = {}", failCount.get());
        log.info("Final stock = {}", product.getStock());
    }
}
