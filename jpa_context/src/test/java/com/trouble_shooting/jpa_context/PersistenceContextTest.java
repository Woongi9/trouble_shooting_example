package com.trouble_shooting.jpa_context;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.trouble_shooting.jpa_context.entity.Product;

import jakarta.persistence.EntityManager;

@SpringBootTest
public class PersistenceContextTest {
	@Autowired
	EntityManager entityManager;

	@Test
	@Transactional
	@DisplayName("PrePersist 어노테이션 1차 캐시 진입 or 플러시에서 동작 위치 확인")
	void prePersist() {
		Product product = Product.builder()
			.name("주문")
			.price(1000)
			.build();
		entityManager.persist(product);

		Product persistedProduct = entityManager.find(Product.class, product.getId());
		System.out.println("order.getCreateDateTime() = " + persistedProduct.getCreateDateTime());

		entityManager.flush();
		Product flushedProduct = entityManager.find(Product.class, product.getId());
		System.out.println("order.getCreateDateTime() = " + flushedProduct.getCreateDateTime());
	}
}
