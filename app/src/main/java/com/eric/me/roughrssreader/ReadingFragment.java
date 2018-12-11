package com.eric.me.roughrssreader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.prof.rssparser.Article;

import java.util.ArrayList;

public class ReadingFragment extends Fragment {

    private View view;
    private ArrayList<Article> articleList;
    private int loaded, feedNumber;
    private MySuperCoolAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_reading, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        feedNumber = ((MainActivity) getActivity()).getFeedNumber();

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //TODO 也许可精简
                ((MainActivity) getActivity()).clearFeed();
                ((MainActivity) getActivity()).getData();
                initRecyclerView();
                (new LoadOnFinish()).execute();
                adapter.notifyDataSetChanged();
                new Handler().postDelayed(new Runnable() {//模拟耗时操作
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);//取消刷新
                    }
                },2000);
                //TODO 改进
            }
        });

        initRecyclerView();
        // wait until load finished
        (new LoadOnFinish()).execute();

        //set title bar
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("News");

        return view;
    }

    class LoadOnFinish extends AsyncTask<Void, Void, Void> {

        LoadOnFinish() {

        }

        @Override
        protected Void doInBackground(Void... voids) {
            do {
                getData();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException i) {
                    //do nothing
                }
            } while (loaded < feedNumber);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            articleList = MainActivity.sortArticle(articleList);
            updateRecyclerView();
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    //TODO 这里有一个bug
    private void getData() {
        articleList = ((MainActivity) getActivity()).articleList;
        loaded = ((MainActivity) getActivity()).getLoaded();
    }

    private void initRecyclerView() {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateRecyclerView();
    }

    private void updateRecyclerView() {
        adapter = new MySuperCoolAdapter(articleList, R.layout.card_view, getActivity());
        recyclerView.setAdapter(adapter);
    }
}
