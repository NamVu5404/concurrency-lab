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
public class LockHolderService {

    private final ProductRepository productRepository;

    @Transactional
    public void holdLock(Long productId) {
        Product product = productRepository.findByIdForUpdate(productId)
                .orElseThrow();

        log.info("TX1: Got lock, sleeping...");
        try {
            Thread.sleep(10000); // giữ lock 10s
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        product.setStock(product.getStock() - 1);
        log.info("TX1: Done");
    }
}
