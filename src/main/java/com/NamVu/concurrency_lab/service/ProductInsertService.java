package com.NamVu.concurrency_lab.service;

import com.NamVu.concurrency_lab.domain.Product;
import com.NamVu.concurrency_lab.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductInsertService {

    private final ProductRepository productRepository;

    @Transactional
    public void insertProduct() {
        productRepository.save(new Product("Macbook", 5));
    }
}
