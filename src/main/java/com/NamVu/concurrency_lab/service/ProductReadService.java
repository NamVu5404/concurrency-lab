package com.NamVu.concurrency_lab.service;

import com.NamVu.concurrency_lab.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductReadService {

    private final ProductRepository productRepository;

    @Transactional
    public void readCountTwice() {
        long first = productRepository.count();
        log.info("First count = {}", first);

        sleep(3000);

        long second = productRepository.count();
        log.info("Second count = {}", second);
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {}
    }
}
