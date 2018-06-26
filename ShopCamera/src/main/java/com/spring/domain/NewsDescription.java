package com.spring.domain;

import javax.persistence.*;

@Entity
@Table(name = "news_decription")
public class NewsDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "content")
    private String content;

    public NewsDescription() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "NewsDescription{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
