package com.trouble_shooting.jpa_context.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "PRODUCT_PURCHASE")
public class ProductPurchase {

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
	private int quantity;

	@Column(name = "total_price")
	private long totalPrice;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private PurchaseStatus status;

	@PrePersist
	protected void onCreate() {
		createDateTime = (createDateTime == null) ? new Date() : createDateTime;
	}

	@Builder
	public ProductPurchase(Date createDateTime, Product product,
		User user, int quantity,
		long totalPrice) {
		this.createDateTime = createDateTime;
		this.product = product;
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
