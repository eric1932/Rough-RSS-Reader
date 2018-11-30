package com.eric.me.roughrssreader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.prof.rssparser.Article;
import com.prof.rssparser.Parser;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RSSTestActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MySuperCoolAdapter mySuperCoolAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsstest);

        //get intent information
        Intent intentFrom = getIntent();
        String siteName = intentFrom.getStringExtra("SITE NAME");

        String urlString = "https://www.androidcentral.com/feed";
        //TODO 处理 siteName
        switch (siteName) {
            case "Android Central":
                urlString = "https://www.androidcentral.com/feed";
        }

        //Load RecyclerView
        recyclerView = findViewById(R.id.theREcyclerVIEW);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        retrieveFeed(urlString);
    }

    public void retrieveFeed(String urlString) {
        Parser parser = new Parser();
        parser.execute(urlString);
        parser.onFinish(new Parser.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(ArrayList<Article> arrayList) {
                mySuperCoolAdapter = new MySuperCoolAdapter(arrayList, R.layout.card, RSSTestActivity.this);
                recyclerView.setAdapter(mySuperCoolAdapter);
            }

            @Override
            public void onError() {
                Toast.makeText(getApplicationContext(), "SOME ERROR OCCURRED", Toast.LENGTH_LONG).show();
            }
        });
    }
}
