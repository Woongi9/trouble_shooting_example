package com.trouble_shooting.jpa_context.service;

import java.util.ArrayList;
import java.util.Date;
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
	public void purchaseOrder(List<PurchaseProductDTO> purchaseProductDTOList) {
		Date nowDate = new Date();
		List<ProductPurchase> productPurchaseList = new ArrayList<>();
		List<ProductHistory> productHistoryList = new ArrayList<>();
		for (PurchaseProductDTO purchaseProductDTO : purchaseProductDTOList) {
			Product product = productRepository.findById(purchaseProductDTO.getOrderId()).orElseThrow(() ->
				new IllegalArgumentException("해당 아이디의 Order가 없습니다. orderId=" + purchaseProductDTO.getOrderId()));
			User user = userRepository.findById(purchaseProductDTO.getUserId()).orElseThrow(() ->
				new IllegalArgumentException("해당 아이디의 User가 없습니다. userId=" + purchaseProductDTO.getUserId()));

			ProductPurchase productPurchase = ProductPurchase.builder()
				.createDateTime(nowDate)
				.product(product)
				.user(user)
				.quantity(purchaseProductDTO.getQuantity())
				.totalPrice(purchaseProductDTO.getTotalPrice())
				.build();
			productPurchaseList.add(productPurchase);

			ProductHistory productHistory = ProductHistory.builder()
				.createDateTime(nowDate)
				.product(product)
				.user(user)
				.quantity(purchaseProductDTO.getQuantity())
				.totalPrice(purchaseProductDTO.getTotalPrice())
				.build();
			productHistoryList.add(productHistory);
		}

		productPurchaseRepository.saveAll(productPurchaseList);
		productHistoryRepository.saveAll(productHistoryList);
	}

	@Getter
	@NoArgsConstructor
	public static class PurchaseProductDTO {
		private long userId;
		private long orderId;
		private int quantity;
		private long totalPrice;

		@Builder
		public PurchaseProductDTO(long userId, long orderId,
			int quantity, long totalPrice) {
			this.userId = userId;
			this.orderId = orderId;
			this.quantity = quantity;
			this.totalPrice = totalPrice;
		}
	}
}
