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
public class ReadService {

    private final ProductRepository productRepository;

    @Transactional
    public void readOnce(String label) {
        Product p = productRepository.findAll().getFirst();
        log.info("{} read stock = {}", label, p.getStock());
    }
}
