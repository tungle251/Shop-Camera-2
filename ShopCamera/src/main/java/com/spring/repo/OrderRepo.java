package com.spring.repo;

import java.util.List;

import org.springframework.web.context.request.WebRequest;
import com.spring.domain.Order;
import com.spring.domain.OrderDetail;

public interface OrderRepo {
	// public int getListOrder();
	public Order getOrder(int id);

	public List<Order> getListOrder();

	public int insertOrder(Order order);

	public int insertOrder(OrderDetail order);

	void updateOrder(Order order);

	void deleteOrder(int id);
}
