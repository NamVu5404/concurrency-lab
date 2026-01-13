package com.NamVu.concurrency_lab.service;

import com.NamVu.concurrency_lab.domain.Product;
import com.NamVu.concurrency_lab.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReadService {

    private final ProductRepository productRepository;
    private final EntityManager em;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void readTwice() {
        Product p1 = productRepository.findAll().getFirst();
        log.info("First read stock = {}", p1.getStock());

        sleep(3000); // chờ update chen vào

        em.clear(); // ép Hibernate bỏ cache

        Product p2 = productRepository.findAll().getFirst();
        log.info("Second read stock = {}", p2.getStock());
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {}
    }
}
