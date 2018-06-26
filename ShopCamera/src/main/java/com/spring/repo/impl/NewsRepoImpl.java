package com.spring.repo.impl;

import com.spring.domain.News;
import com.spring.domain.NewsDescription;
import com.spring.domain.Product;
import com.spring.repo.NewsRepo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class NewsRepoImpl implements NewsRepo {
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private ProductRepoImpl productRepo;

    @Override
    public List<News> getAllNews() {
        Session session = this.sessionFactory.openSession();
        List<News> list = session.createQuery("From News").getResultList();
        return list;
    }

    public News getNews(int id) {
        Session session = this.sessionFactory.openSession();
        News news = session.get(News.class, id);
        return news;
    }

    public List<News> randomNews() {
        List<News> list = new ArrayList<>();
        for (int i = 5; i < 10; i++) {
            list.add(getNews(i));
        }
        return list;
    }

    public List<Product> randomProducts() {
        List<Product> list = new ArrayList<>();
        for (int i = 18; i < 25; i++) {
            list.add(productRepo.getProduct(i));
        }
        return list;
    }

    public NewsDescription findNewsDescriptionById(int id) {
        Session session = this.sessionFactory.openSession();
        NewsDescription newsDescription = session.get(NewsDescription.class, id);
        return newsDescription;
    }
}
