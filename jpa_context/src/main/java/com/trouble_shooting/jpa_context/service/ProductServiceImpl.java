package com.trouble_shooting.jpa_context.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trouble_shooting.jpa_context.entity.Product;
import com.trouble_shooting.jpa_context.entity.ProductHistory;
import com.trouble_shooting.jpa_context.entity.ProductPurchase;
import com.trouble_shooting.jpa_context.entity.User;
import com.trouble_shooting.jpa_context.repository.ProductHistoryRepository;
import com.trouble_shooting.jpa_context.repository.ProductPurchaseRepository;
import com.trouble_shooting.jpa_context.repository.ProductRepository;
import com.trouble_shooting.jpa_context.repository.UserRepository;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final ProductHistoryRepository productHistoryRepository;
	private final ProductPurchaseRepository productPurchaseRepository;

	@Override
	@Transactional
	public void purchaseOrder(List<PurchaseOrderDTO> purchaseOrderDTOList) {
		for (PurchaseOrderDTO purchaseOrderDTO : purchaseOrderDTOList) {
			Product product = productRepository.findById(purchaseOrderDTO.getOrderId()).orElseThrow(() ->
				new IllegalArgumentException("해당 아이디의 Order가 없습니다. orderId=" + purchaseOrderDTO.getOrderId()));
			User user = userRepository.findById(purchaseOrderDTO.getUserId()).orElseThrow(() ->
				new IllegalArgumentException("해당 아이디의 User가 없습니다. userId=" + purchaseOrderDTO.getUserId()));

			ProductPurchase productPurchase = ProductPurchase.builder()
				.product(product)
				.user(user)
				.quantity(purchaseOrderDTO.getQuantity())
				.totalPrice(purchaseOrderDTO.getTotalPrice())
				.build();
			productPurchaseRepository.save(productPurchase);

			ProductHistory productHistory = ProductHistory.builder()
				.product(product)
				.user(user)
				.quantity(purchaseOrderDTO.getQuantity())
				.totalPrice(purchaseOrderDTO.getTotalPrice())
				.build();
			productHistoryRepository.save(productHistory);
		}
	}

	@Getter
	@NoArgsConstructor
	public static class PurchaseOrderDTO {
		private long userId;
		private long orderId;
		private int quantity;
		private long totalPrice;

		@Builder
		public PurchaseOrderDTO(long userId, long orderId,
			int quantity, long totalPrice) {
			this.userId = userId;
			this.orderId = orderId;
			this.quantity = quantity;
			this.totalPrice = totalPrice;
		}
	}
}
