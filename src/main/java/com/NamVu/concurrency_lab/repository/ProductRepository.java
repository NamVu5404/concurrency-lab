package com.NamVu.concurrency_lab.repository;

import com.NamVu.concurrency_lab.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
}
