package com.trouble_shooting.jpa_context.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trouble_shooting.jpa_context.entity.Order;
import com.trouble_shooting.jpa_context.entity.OrderHistory;
import com.trouble_shooting.jpa_context.entity.OrderPurchase;
import com.trouble_shooting.jpa_context.entity.User;
import com.trouble_shooting.jpa_context.repository.OrderHistoryRepository;
import com.trouble_shooting.jpa_context.repository.OrderPurchaseRepository;
import com.trouble_shooting.jpa_context.repository.OrderRepository;
import com.trouble_shooting.jpa_context.repository.UserRepository;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final UserRepository userRepository;
	private final OrderRepository orderRepository;
	private final OrderHistoryRepository orderHistoryRepository;
	private final OrderPurchaseRepository orderPurchaseRepository;

	@Override
	@Transactional
	public void purchaseOrder(PurchaseOrderDTO purchaseOrderDTO) {
		Order order = orderRepository.findById(purchaseOrderDTO.getOrderId()).orElseThrow(() ->
			new IllegalArgumentException("해당 아이디의 Order가 없습니다. orderId=" + purchaseOrderDTO.getOrderId()));
		User user = userRepository.findById(purchaseOrderDTO.getUserId()).orElseThrow(() ->
			new IllegalArgumentException("해당 아이디의 User가 없습니다. userId=" + purchaseOrderDTO.getUserId()));

		OrderPurchase orderPurchase = OrderPurchase.builder()
			.order(order)
			.user(user)
			.quantity(purchaseOrderDTO.getQuantity())
			.totalPrice(purchaseOrderDTO.getTotalPrice())
			.build();
		orderPurchaseRepository.save(orderPurchase);

		OrderHistory orderHistory = OrderHistory.builder()
			.order(order)
			.user(user)
			.quantity(purchaseOrderDTO.getQuantity())
			.totalPrice(purchaseOrderDTO.getTotalPrice())
			.build();
		orderHistoryRepository.save(orderHistory);
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
