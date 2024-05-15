package com.trouble_shooting.jpa_context.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "ORDER_HISTORY")
public class OrderHistory extends ParentEntity {

	@JoinColumn(name = "order")
	@ManyToOne(fetch = FetchType.LAZY)
	private Order order;

	@JoinColumn(name = "user")
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	@Column(name = "quantity", length = 10)
	private Integer quantity;

	@Column(name = "total_price", length = 20)
	private long totalPrice;

	@Builder
	public OrderHistory(Order order, User user,
		Integer quantity, long totalPrice) {
		this.order = order;
		this.user = user;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
	}
}
