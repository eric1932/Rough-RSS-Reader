package com.eric.me.roughrssreader;

import android.content.Context;

import com.google.gson.Gson;
import com.prof.rssparser.Article;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

//TODO 一次只能添加/删除最后一个星星
class FavoriteHelper {

    private Context mContext;
    private static String fileName = "favorite.txt";
    private ArrayList<Article> articles;
    private IOHelper ioHelper;

    FavoriteHelper(Context context) {
        mContext = context;
        loadArticles();
    }

    boolean inFavorite(Article article) {
        return (indexOf(article) >= 0);
    }

    private int indexOf(Article article) {
        String title = article.getTitle();
        Date pubDate = article.getPubDate();
        for (int i = 0; i < articles.size(); i++) {
            if (articles.get(i).getTitle().equals(title) && articles.get(i).getPubDate().equals(article.getPubDate())) {
                return i;
            }
        }
        return -1;
    }

    void addToFavorite(Article article) {
        articles.add(article);
        syncChanges();
    }

    void removeFromFavorite(Article article) {
        int index = indexOf(article);
        articles.remove(index);
        syncChanges();
    }

    private void loadArticles() {
        ioHelper = new IOHelper(mContext);
        String json;
        try {
            json = ioHelper.readFile(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            //empty
            json = "[]";
        }
        Article[] tmp = (new Gson()).fromJson(json, Article[].class);
        if (tmp != null) {
            articles = new ArrayList<>(Arrays.asList(tmp));
        } else {
            articles = new ArrayList<>();
        }
    }

    private void syncChanges() {
        Gson gson = new Gson();
        String toJson = gson.toJson(articles);
        ioHelper.writeFile(toJson, fileName, true);
        loadArticles();
    }

    ArrayList<Article> getArticles() {
        return articles;
    }
}
