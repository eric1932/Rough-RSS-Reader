package com.eric.me.roughrssreadernew;

import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class WebViewActivity extends AppCompatActivity {

    private static String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_container);

        Intent intent = getIntent();
        String content = intent.getStringExtra("tmp"); //tmp is content
        URL = intent.getStringExtra("URL");
        WebView webView = findViewById(R.id.webview);
        //webView.getSettings().setJavaScriptEnabled(true);
        webView.setHorizontalScrollBarEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        //webView.loadDataWithBaseURL(null, "<style>img{display: inline; height: auto; max-width: 100%;} " +
        //        "</style>\n" + "<style>iframe{ height: auto; width: auto;}" + "</style>\n" + content, null, "utf-8", null);
        webView.loadDataWithBaseURL(null, "<style>img{display: inline; height: auto; max-width: 100%;}</style>\n" +
                "<style>iframe{height: auto; width: auto;}</style>\n" + content
                + "<br />\n<br />\n<br />\n<br />\n", "html/text", "utf-8", null);
    }

    public void onClickFAB(View v) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(URL));
    }
}
