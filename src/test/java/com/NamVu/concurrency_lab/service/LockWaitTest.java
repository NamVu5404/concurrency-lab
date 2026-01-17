package com.NamVu.concurrency_lab.service;

import com.NamVu.concurrency_lab.domain.Product;
import com.NamVu.concurrency_lab.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@Slf4j
public class LockWaitTest {

    @Autowired
    private LockHolderService lockHolderService;

    @Autowired
    private LockWaitService lockWaitService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void lock_wait_should_happen() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> lockHolderService.holdLock(1L));
        Thread.sleep(1000); // đảm bảo TX1 lock trước
        executor.submit(() -> lockWaitService.tryUpdate(1L));

        Thread.sleep(15000);

        Product product = productRepository.findById(1L).orElseThrow();
        log.info("Stock after updated = {}", product.getStock());
    }
}
