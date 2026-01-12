package com.NamVu.concurrency_lab.service;

import com.NamVu.concurrency_lab.domain.Product;
import com.NamVu.concurrency_lab.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;

    @Transactional
    public boolean buy(Long productId) {
        String thread = Thread.currentThread().getName();
        log.info("[{}] Start buy", thread);

        Product product = productRepository.findByIdForUpdate(productId).orElseThrow();

        log.info("[{}] Current stock = {}", thread, product.getStock());

        if (product.getStock() > 0) {
            product.decreaseStock();

            log.info("[{}] Stock after decrease = {}", thread, product.getStock());

            productRepository.save(product);
            log.info("[{}] Buy success", thread);
            return true;
        }

        log.warn("[{}] Buy failed - out of stock", thread);
        return false;
    }
}
