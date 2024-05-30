package com.trouble_shooting.jpa_context.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "Product_HISTORY")
public class ProductHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", updatable = false, nullable = false)
	private Long id;

	@Column(name = "CREATE_DATETIME")
	private Date createDateTime;

	@JoinColumn(name = "product")
	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;

	@JoinColumn(name = "user")
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	@Column(name = "quantity", length = 10)
	private Integer quantity;

	@Column(name = "total_price", length = 20)
	private long totalPrice;

	@PrePersist
	protected void onCreate() {
		createDateTime = new Date();
	}

	@Builder
	public ProductHistory(Date createDateTime, Product product,
		User user, Integer quantity,
		long totalPrice) {
		this.createDateTime = createDateTime;
		this.product = product;
		this.user = user;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
	}
}
