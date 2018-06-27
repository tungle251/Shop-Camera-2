package com.spring.controller;

import com.spring.domain.Users;
import com.spring.repo.impl.UserRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class UserController {
    @Autowired
    private UserRepoImpl userRepo;

    @RequestMapping("/danh-sach-user")
    public String danhSachUser(Model model) {
        model.addAttribute("listUser", userRepo.getListUser());
        return "admin/danhsachuser";
    }

    @RequestMapping("/getuser/{id}")
    public ResponseEntity<?> getUser(Model model, @PathVariable int id) {
        Users users = userRepo.getUserById(id);
        return ResponseEntity.ok(users);
    }
}
