package com.NamVu.concurrency_lab.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderTransactionalService txService;

    public void buyWithRetry(Long productId) {
        int maxRetry = 3;

        for(int attempt = 1; attempt <= maxRetry; attempt++) {
            try {
                txService.buy(productId);
                return;
            } catch (ObjectOptimisticLockingFailureException e) {
                log.warn("Optimistic lock conflict, attempt {}", attempt);
            }
        }

        throw new RuntimeException("Buy failed after retries");
    }
}
