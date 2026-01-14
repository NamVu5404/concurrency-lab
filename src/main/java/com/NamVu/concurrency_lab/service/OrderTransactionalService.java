package com.NamVu.concurrency_lab.service;

import com.NamVu.concurrency_lab.domain.Product;
import com.NamVu.concurrency_lab.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderTransactionalService {

    private final ProductRepository productRepository;

    @Transactional
    public void buy(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow();

        if (product.getStock() <= 0) {
            throw new RuntimeException("Out of stock");
        }

        product.decreaseStock();
        // Hibernate sẽ:
        // - flush khi commit
        // - check version
    }
}
