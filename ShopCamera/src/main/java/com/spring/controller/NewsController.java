package com.spring.controller;

import com.spring.domain.News;
import com.spring.domain.Product;
import com.spring.repo.impl.NewsRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/CameraGiamSat")
public class NewsController {
    @Autowired
    private NewsRepoImpl newsRepo;

    @RequestMapping("/tin-tuc")
    public String home(HttpServletRequest request) {
        request.getSession().setAttribute("newslist", null);
        return "redirect:/CameraGiamSat/tin-tuc/page/1";
    }

    @RequestMapping("/tin-tuc/page/{pageNumber}")
    public String home2(HttpServletRequest request, @PathVariable int pageNumber, Model model) {
        PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("newslist");
        int pagesize = 10;
        List<News> list = newsRepo.getAllNews();
        if (pages == null) {
            pages = new PagedListHolder<>(list);
            pages.setPageSize(pagesize);
        } else {
            final int goToPage = pageNumber - 1;
            if (goToPage <= pages.getPageCount() && goToPage >= 0) {
                pages.setPage(goToPage);
            }
        }
        request.getSession().setAttribute("newslist", pages);
        int current = pages.getPage() + 1;
        int begin = Math.max(1, current - list.size());
        int end = Math.min(begin + list.size(), pages.getPageCount());
        int totalPageCount = pages.getPageCount();
        String baseUrl = "/CameraGiamSat/tin-tuc/page/";

        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("baseUrl", baseUrl);
        model.addAttribute("list", pages);
        model.addAttribute("randomNews", newsRepo.randomNews());
        model.addAttribute("randomProducts", newsRepo.randomProducts());
        return "tin-tuc";
    }

}
