package com.NamVu.concurrency_lab.service;

import com.NamVu.concurrency_lab.domain.Product;
import com.NamVu.concurrency_lab.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LockWaitService {

    private final ProductRepository productRepository;

    @Transactional
    public void tryUpdate(Long productId) {
        Product product = productRepository.findByIdForUpdate(productId)
                .orElseThrow();
        log.info("TX2: Trying update...");

        product.setStock(product.getStock() - 1);
        log.info("TX2: Updated");
    }
}
