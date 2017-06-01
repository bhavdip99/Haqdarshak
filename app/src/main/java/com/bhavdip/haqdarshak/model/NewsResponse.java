package com.bhavdip.haqdarshak.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by bhavdip on 29/5/17.
 */

public class NewsResponse {
    @SerializedName("articles")
    private List<News> articles;

    public List<News> getArticles() {
        return articles;
    }

    public void setArticles(List<News> articles) {
        this.articles = articles;
    }
}
