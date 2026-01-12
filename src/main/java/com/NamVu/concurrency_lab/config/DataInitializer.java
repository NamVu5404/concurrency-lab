package com.NamVu.concurrency_lab.config;

import com.NamVu.concurrency_lab.domain.Product;
import com.NamVu.concurrency_lab.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final ProductRepository productRepository;

    @PostConstruct
    public void init() {
        productRepository.save(new Product("Iphone", 9));
    }
}
