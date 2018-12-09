package com.eric.me.roughrssreader;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class SiteHelper {

    private ArrayList<Site> siteList;

    SiteHelper() {
        siteList = new ArrayList<>();
    }

    SiteHelper(String json) {
        siteList = new ArrayList<>(Arrays.asList((new Gson()).fromJson(json, Site[].class)));
    }

    SiteHelper(ArrayList<Site> siteList) {
        this.siteList = siteList;
    }

    public void add(String siteName, String url) {
        siteList.add(new Site(siteName, url));
    }

    public Site[] getArray() {
        Site[] toReturn = new Site[siteList.size()];
        for (int i = 0; i < siteList.size(); i++) {
            toReturn[i] = siteList.get(i);
        }
        return toReturn;
    }

    ArrayList<Site> getArrayList() {
        return siteList;
    }

    public String toJson() {
        return (new Gson()).toJson(siteList);
    }

    static Site[] getDefaultSiteArray() {
        Gson gson = new Gson();
        String json = "[{\"site_name\":\"IT Home\",\"url\":\"http://www.ithome.com/rss\"},{\"site_name\":\"Android Authority\",\"url\":\"https://www.androidauthority.com/feed\"}]";
        return gson.fromJson(json, Site[].class);
    }

    //TODO to be refined
    static ArrayList<Site> getDefaultSiteArrayList() {
        Site[] tmp = getDefaultSiteArray();
        ArrayList<Site> toReturn = new ArrayList<>();
        for (Site x : tmp) {
            toReturn.add(x);
        }
        return toReturn;
    }

    static String getDefaultJson() {
        return "[{\"site_name\":\"IT Home\",\"url\":\"http://www.ithome.com/rss\"},{\"site_name\":\"Android Authority\",\"url\":\"https://www.androidauthority.com/feed\"}]";
    }
}
