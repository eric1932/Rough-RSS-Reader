package com.eric.me.roughrssreader;

import android.content.Context;

import com.google.gson.Gson;
import com.prof.rssparser.Article;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

//TODO 一次只能加一个星星
//TODO 无法删除
class FavoriteHelper {

    private Context mContext;
    private static String fileName = "favorite.txt";
    private ArrayList<Article> articles;
    private IOHelper ioHelper;

    FavoriteHelper(Context context) {
        mContext = context;
        //load articles
        ioHelper = new IOHelper(mContext);
        String json = null;
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

    boolean inFavorite(Article article) {
        String title = article.getTitle();
        for (Article x : articles) {
            if (x.getTitle().equals(title)) {
                return true;
            }
        }
        return false;
    }

    void addToFavorite(Article article) {
        articles.add(article);
        syncChanges();
    }

    void removeFromFavorite(Article article) {
        articles.remove(article);
    }

    private void syncChanges() {
        Gson gson = new Gson();
        String toJson = gson.toJson(articles);
        ioHelper.writeFile(toJson, fileName, true);
    }
}
