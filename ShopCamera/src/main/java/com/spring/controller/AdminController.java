package com.spring.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.domain.Order;
import com.spring.domain.OrderDetail;
import com.spring.domain.Product;
import com.spring.domain.Users;
import com.spring.repo.impl.CategoryRepoImpl;
import com.spring.repo.impl.OrderRepoImpl;
import com.spring.repo.impl.ProcessRepoImpl;
import com.spring.repo.impl.ProductRepoImpl;
import com.spring.repo.impl.UserRepoImpl;
import com.spring.service.impl.CategoryServiceImpl;
import com.spring.service.impl.ProductServiceImpl;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

	private static String PATH = "C:\\Users\\TungLe\\Desktop\\New folder\\";
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
	private ProductRepoImpl productRepoImpl;

	@Autowired
	private ProcessRepoImpl processingRepoImpl;

	@RequestMapping(value = "/login", method = { RequestMethod.GET })
	public String loginView(@RequestParam(name = "error", required = false) String error, Model model) {
		if (error != null) {
			String message = "username or password is not correct";
			model.addAttribute("loginError", message);

		}
		return "admin/login";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model, Principal principal) {
		model.addAttribute("name", principal.getName());
		return "admin/index";
	}

	@RequestMapping(value = "/danhsachsanpham", method = RequestMethod.GET)
	public String getListProduct(HttpServletRequest request) {
		request.getSession().setAttribute("productlist", null);
		return "redirect:/admin/danhsachsanpham/page/1";
	}

	@RequestMapping(value = "/danhsachsanpham/page/{pageNumber}", method = RequestMethod.GET)
	public String getListProduct2(HttpServletRequest request, @PathVariable int pageNumber, Model model) {
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("productlist");
		int pagesize = 10;
		List<Product> list = productServiceImpl.getAll();
		if (pages == null) {
			pages = new PagedListHolder<>(list);
			pages.setPageSize(pagesize);
		} else {
			final int goToPage = pageNumber - 1;
			if (goToPage <= pages.getPageCount() && goToPage >= 0) {
				pages.setPage(goToPage);
			}
		}
		request.getSession().setAttribute("productlist", pages);
		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - list.size());
		int end = Math.min(begin + 20, pages.getPageCount());
		int totalPageCount = pages.getPageCount();
		String baseUrl = "/admin/danhsachsanpham/page/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("list", pages);
		return "admin/danhsachsanpham";
	}

	@RequestMapping("/themsanpham")
	public String addProduct(Model model) {
		model.addAttribute("product", new Product());
		model.addAttribute("listCate", categoryRepoImpl.getCategoryByIdToot(0));
		model.addAttribute("categoryRepoImpl", categoryRepoImpl);
		model.addAttribute("selected", 3);
		return "admin/themsanpham";
	}

	@RequestMapping(value = "/saveproduct", method = RequestMethod.POST)
	public String save(@ModelAttribute("product") @Valid Product product, BindingResult result,
			RedirectAttributes redirect, @RequestParam MultipartFile file) throws IOException {
		if (result.hasErrors()) {
			return "/admin/themsanpham";
		}
		if (!file.isEmpty()) {

			String fileName = file.getOriginalFilename();
			InputStream is = file.getInputStream();

			Files.copy(is, Paths.get(PATH + fileName), StandardCopyOption.REPLACE_EXISTING);
		}
		productServiceImpl.add(product);
		redirect.addFlashAttribute("success", "Saved contact successfully!");
		return "redirect:/admin/danhsachsanpham";
	}

	@RequestMapping("/themsanpham2")
	public String addProduct2(Model model) {
		model.addAttribute("product", new Product());
		model.addAttribute("listCate", categoryRepoImpl.getCategoryByIdToot(0));
		model.addAttribute("categoryRepoImpl", categoryRepoImpl);
		model.addAttribute("selected", 3);
		return "admin/themsanpham2";
	}

	@RequestMapping(value = "/saveproduct2", method = RequestMethod.POST)
	public String save(@RequestParam MultipartFile file) throws IOException {
		if (!file.isEmpty()) {

			String fileName = file.getOriginalFilename();
			InputStream is = file.getInputStream();

			Files.copy(is, Paths.get(PATH + fileName), StandardCopyOption.REPLACE_EXISTING);
		}
		return "redirect:/admin/themsanpham2";
	}


}
