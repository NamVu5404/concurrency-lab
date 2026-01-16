package com.NamVu.concurrency_lab.service;

import com.NamVu.concurrency_lab.domain.Product;
import com.NamVu.concurrency_lab.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeadlockService {

    private final ProductRepository productRepository;

    @Transactional
    public void txA() {
        Product p1 = productRepository.lockById(1L);
        sleep(2000);
        Product p2 = productRepository.lockById(2L);
        log.info("TX A done");
    }

    @Transactional
    public void txB() {
        Product p2 = productRepository.lockById(2L);
        sleep(2000);
        Product p1 = productRepository.lockById(1L);
        log.info("TX B done");
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {}
    }
}
