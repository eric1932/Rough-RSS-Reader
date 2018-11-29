package com.eric.me.roughrssreader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.prof.rssparser.Article;
import com.prof.rssparser.Parser;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RSSTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsstest);
    }

    public void onClickMain(View v) {
        //TODO 替换为自定义网址
        String urlString = "https://www.androidcentral.com/feed";
        retrieve(urlString);
    }

    public void onClickCurl(View v) {

    }

    public void retrieve(String urlString) {
        final TextView tv = findViewById(R.id.textViewRSS);
        tv.setText("");
        Parser parser = new Parser();
        parser.execute(urlString);
        parser.onFinish(new Parser.OnTaskCompleted() {

            @Override
            public void onTaskCompleted(ArrayList<Article> list) {
                //what to do when the parsing is done
                //the Array List contains all article's data. For example you can use it for your adapter.
                for (Article x : list) {
                    tv.setText(tv.getText() + "" + x + "\n");
                }
            }

            @Override
            public void onError() {
                //what to do in case of error
            }
        });
        return;
    }
}
