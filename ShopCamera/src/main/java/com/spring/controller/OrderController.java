package com.spring.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import com.spring.domain.Order;
import com.spring.domain.Users;
import com.spring.repo.OrderJpaRepo;
import com.spring.repo.impl.CategoryRepoImpl;
import com.spring.repo.impl.OrderRepoImpl;
import com.spring.repo.impl.ProcessRepoImpl;
import com.spring.repo.impl.ProductRepoImpl;
import com.spring.repo.impl.UserRepoImpl;
import com.spring.service.impl.CategoryServiceImpl;
import com.spring.service.impl.ProductServiceImpl;

@Controller
@RequestMapping(value = "/admin")
public class OrderController {

	@Autowired
	private ProductServiceImpl productServiceImpl;
	@Autowired
	private CategoryServiceImpl categoryServiceImpl;
	@Autowired
	private CategoryRepoImpl categoryRepoImpl;
	@Autowired
	private OrderRepoImpl orderRepoImpl;
	@Autowired
	private UserRepoImpl userRepoImpl;
	@Autowired
	private OrderJpaRepo orderJpaRepo;
	@Autowired
	private ProductRepoImpl productRepoImpl;

	@Autowired
	private ProcessRepoImpl processingRepoImpl;

	@RequestMapping(value = "/danhsachdonhang")
	public String danhsachdonhang(Model model) {
		List<Order> listOrder = orderRepoImpl.getListOrder();
		model.addAttribute("listOrder", listOrder);
		List<Users> listUsers = new ArrayList<>();
		for (int i = 0; i < listOrder.size(); i++) {
			listUsers.add(userRepoImpl.getUserById(listOrder.get(i).getIdUser()));
		}
		model.addAttribute("listUsers", listUsers);
		return "admin/danhsachdonhang";
	}

	@RequestMapping(value = "/chitietdonhang/{id}")
	public String chitietdonhang(Model model, @PathVariable(value = "id") int id) {
		List<Object[]> list = orderRepoImpl.getOrderDetailByIdOrder(id);
		model.addAttribute("order", orderRepoImpl.getOrder(id));
		model.addAttribute("productRepoImpl", productRepoImpl);
		model.addAttribute("list", list);

		Users users = userRepoImpl.getUserById(orderRepoImpl.getOrder(id).getIdUser());

		model.addAttribute("user", users);
		model.addAttribute("process", processingRepoImpl.findById(orderRepoImpl.getOrder(id).getIdProcess()));
		return "admin/chitietdonhang";
	}

	@RequestMapping(value = "/suadonhang/{id}")
	public String suadonhang(Model model, @PathVariable(value = "id") int id) {
		List<Object[]> list = orderRepoImpl.getOrderDetailByIdOrder(id);
		model.addAttribute("order", orderRepoImpl.getOrder(id));
		model.addAttribute("productRepoImpl", productRepoImpl);
		model.addAttribute("list", list);

		Users users = userRepoImpl.getUserById(orderRepoImpl.getOrder(id).getIdUser());

		model.addAttribute("user", users);
		model.addAttribute("process", processingRepoImpl.findById(orderRepoImpl.getOrder(id).getIdProcess()));
		model.addAttribute("listProcess", processingRepoImpl.findAll());
		return "admin/suadonhang";
	}

	@RequestMapping(value = "/suadonhang2/{id}", method = RequestMethod.POST)
	public String sua(WebRequest request, @PathVariable(value = "id") int id) {
		Order order = orderRepoImpl.getOrder(id);

		// System.err.println(request.getParameter("active"));
		// System.err.println(request.getParameter("process"));
		int idActive = Integer.parseInt(request.getParameter("active"));
		int idProcess = Integer.parseInt(request.getParameter("process"));

		System.err.println(idActive);
		System.err.println(idProcess);
		order.setActive(idActive);
		order.setIdProcess(idProcess);
		orderRepoImpl.updateOrder(order);
		return "redirect:/admin/danhsachdonhang";
	}

	@RequestMapping(value = "/deleteOrder/{id}", method = RequestMethod.POST)
	public String xoadonhang(@PathVariable(value = "id") int id) {
		Order order = orderRepoImpl.getOrder(id);
		orderRepoImpl.deleteOrder(id);
		return "redirect:/admin/danhsachdonhang";
	}

}
