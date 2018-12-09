package com.eric.me.roughrssreader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.prof.rssparser.Article;
import com.prof.rssparser.Parser;

import java.util.ArrayList;

public class ReadingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MySuperCoolAdapter adapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);

        progressBar = findViewById(R.id.progressBar);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Intent intent = getIntent();
        String[] URL = intent.getStringArrayExtra("URL");
        getFeed(URL);
        //getFeed(new String[] {"https://www.ithome.com/rss/"});
    }

    public void getFeed(String[] URL) {
        Parser parser = new Parser();
        parser.execute(URL);
        parser.onFinish(new Parser.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(ArrayList<Article> arrayList) {
                adapter = new MySuperCoolAdapter(arrayList, R.layout.card_view, ReadingActivity.this);
                progressBar.setVisibility(View.INVISIBLE);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onError() {
                Toast.makeText(getApplicationContext(), "SOME ERROR OCCURRED", Toast.LENGTH_LONG).show();
                Log.e("RSSERROR", "RSSERROR");
            }
        });
    }

    public static boolean testSingleFeed(String URL) {
        if (!URL.contains(".") || URL.contains(" ") || URL.contains("\\")) {
            return false;
        } else if (!URL.startsWith("http://") && !URL.startsWith("https://")) {
            URL = "http://" + URL;
        }
        //TODO 判断是否为RSS网页
        return true;
    }
}
