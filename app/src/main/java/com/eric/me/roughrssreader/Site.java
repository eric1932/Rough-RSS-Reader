package com.eric.me.roughrssreader;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Site {

    @SerializedName("site_name")
    private String siteName;
    @SerializedName("url")
    private String url;

    public Site(String siteName, String webAddress) {
        this.siteName = siteName;
        this.url = webAddress;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static void main(String[] args) {
        //读取Demo
        Gson gson = new Gson();
        String json = "[{\"site_name\":\"IT Home\",\"url\":\"http://www.ithome.com/rss\"},{\"site_name\":\"Android Authority\",\"url\":\"https://www.androidauthority.com/feed\"}]";
        Site[] siteList = gson.fromJson(json, Site[].class);
        System.out.println(siteList.length);

        //写入Demo
        Gson gson2 = new Gson();
        String str = gson2.toJson(siteList);
        System.out.println(str);
    }
}
