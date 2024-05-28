package com.trouble_shooting.jpa_context.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "PRODUCT")
public class Product extends ParentEntity{

	@Column(name = "name")
	private String name;

	@Column(name = "price")
	private int price;

	@Builder
	public Product(String name, int price) {
		this.name = name;
		this.price = price;
	}
}
