package com.trouble_shooting.jpa_context.service;

import java.util.List;

public interface ProductService {

	void purchaseOrder(List<ProductServiceImpl.PurchaseOrderDTO> purchaseOrderDTO);
}
