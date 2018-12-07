package com.eric.me.roughrssreader;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prof.rssparser.Article;

import java.util.ArrayList;

public class ReadingFragment extends Fragment {

    private View view;
    private ArrayList<Article> articleList;
    private int loaded;
    private MySuperCoolAdapter adapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reading, container, false);
//        do {
            getData();
//        } while (loaded < 6);
        initRecyclerview();
        return view;
    }

    private void getData() {
        articleList = ((MainActivity) getActivity()).getArticleList();
        loaded = ((MainActivity) getActivity()).getLoaded();
    }

    private void initRecyclerview() {
        recyclerView = view.findViewById(R.id.fragmentedRecyclerView);
        adapter = new MySuperCoolAdapter(articleList, R.layout.card_view, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
