package com.baizhi.entity;

import java.io.Serializable;

public class Poetry implements Serializable {
    private Integer id;
    private String content;
    private String title;
    private Poet poets;

    public Poetry() {
        super();
    }

    @Override
    public String toString() {
        return "Poetry{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                ", poets=" + poets +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Poet getPoets() {
        return poets;
    }

    public void setPoets(Poet poets) {
        this.poets = poets;
    }

    public Poetry(Integer id, String content, String title, Poet poets) {
        this.id = id;
        this.content = content;
        this.title = title;
        this.poets = poets;
    }
}
