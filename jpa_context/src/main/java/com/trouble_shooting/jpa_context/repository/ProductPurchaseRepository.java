package com.trouble_shooting.jpa_context.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trouble_shooting.jpa_context.entity.ProductPurchase;

public interface ProductPurchaseRepository extends JpaRepository<ProductPurchase, Long> {
}
