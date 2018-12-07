package com.eric.me.roughrssreader;

import com.prof.rssparser.Article;

public class ArticleComparable implements Comparable<ArticleComparable> {

    private Article article;
    private long timeStamp;

    public ArticleComparable(Article a) {
        article = a;
        timeStamp = a.getPubDate().getTime();
    }

    public int compareTo(ArticleComparable a) {
        // * -1 is because I need reversed order
        return Long.compare(this.timeStamp, a.getTimeStamp()) * -1;
    }

    public Article getArticle() {
        return article;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
