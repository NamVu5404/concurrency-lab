package com.NamVu.concurrency_lab.service;

import com.NamVu.concurrency_lab.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContentionService {

    private final ProductRepository productRepository;

    @Transactional
    public void updateStock(Long id) {
        productRepository.findByIdForUpdate(id);
        sleep(100); // giả lập xử lý
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
