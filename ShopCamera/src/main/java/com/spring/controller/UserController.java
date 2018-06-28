package com.spring.controller;

import com.spring.domain.Users;
import com.spring.repo.impl.UserRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

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

    @RequestMapping("/danh-sach-user2/{testPath}")
    public String danhSachUser2(Model model, @PathVariable(value = "testPath") String testPath) {
        model.addAttribute("listUser", userRepo.getListUser());
        model.addAttribute("attribute", testPath);
        return "admin/danhsachuser";
    }

    @RequestMapping("/getuser/{id}")
    public ResponseEntity<?> getUser(Model model, @PathVariable int id) {
        Users users = userRepo.getUserById(id);
        return ResponseEntity.ok(users);
    }

    //    @RequestMapping(value = "/update-user", method = RequestMethod.POST)
//    public String getUser(Model model, WebRequest webRequest) {
//        int id = Integer.parseInt(webRequest.getParameter("id"));
//        int active = Integer.parseInt(webRequest.getParameter("active"));
//        Users users = userRepo.getUserById(id);
//        users.setActive(active);
//        System.err.println(id);
//        System.err.println(active);
//        userRepo.updateUser(users);
//        System.err.println("update ok");
//        return "redirect:/admin/danh-sach-user";
//    }


    @PostMapping("/update-user")
    public RedirectView handleTestRequest(Model model, WebRequest webRequest) {
        int id = Integer.parseInt(webRequest.getParameter("id"));
        int active = Integer.parseInt(webRequest.getParameter("active"));
        int idRole = Integer.parseInt(webRequest.getParameter("role"));

        Users users = userRepo.getUserById(id);
        users.setActive(active);
        users.setId_role(idRole);
        System.err.println(id);
        System.err.println(active);
        userRepo.updateUser(users);
        System.err.println("update ok");
        RedirectView rv = new RedirectView();
        rv.setContextRelative(true);
        rv.setUrl("/admin/danh-sach-user2/abc");
        System.err.println("okkkkkkkkkkkk");
        return rv;
    }


}
