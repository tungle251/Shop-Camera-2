package com.spring.repo;

import com.spring.domain.News;

import java.util.List;

public interface NewsRepo {
    public List<News> getAllNews();
}
