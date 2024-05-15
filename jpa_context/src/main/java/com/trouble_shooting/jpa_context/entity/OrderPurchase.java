package com.trouble_shooting.jpa_context.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "ORDER_PURCHASE")
public class OrderPurchase extends ParentEntity{

	@JoinColumn(name = "order")
	@ManyToOne(fetch = FetchType.LAZY)
	private Order order;

	@JoinColumn(name = "user")
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	@Column(name = "quantity", length = 10)
	private int quantity;

	@Column(name = "total_price")
	private long totalPrice;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private PurchaseStatus status;

	@Builder
	public OrderPurchase(Order order, User user,
		int quantity, long totalPrice) {
		this.order = order;
		this.user = user;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
	}

	public enum PurchaseStatus {
		PURCHASED,
		PREPARING,
		DELIVERING,
		COMPLETE,
		CANCELED
	}
}
