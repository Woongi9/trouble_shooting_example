package com.trouble_shooting.jpa_context.service;

import com.trouble_shooting.jpa_context.entity.Order;

public interface OrderService {

	void purchaseOrder(OrderServiceImpl.PurchaseOrderDTO purchaseOrderDTO);
}
