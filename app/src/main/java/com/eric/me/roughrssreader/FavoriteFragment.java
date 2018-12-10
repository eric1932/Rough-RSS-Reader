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
import android.widget.TextView;

import com.prof.rssparser.Article;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {

    private View mView;
    private ArrayList<Article> articles;
    private RecyclerView recyclerView;
    private FavoriteAdapter favoriteAdapter;
    private TextView noFavorite;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.favorite_fragment, container, false);
        FavoriteHelper favoriteHelper = new FavoriteHelper(getActivity());
        noFavorite = mView.findViewById(R.id.textViewFavoriteIsEmpty);
        articles = favoriteHelper.getArticles();
        if (articles.size() == 0) {
            noFavorite.setVisibility(View.VISIBLE);
        } else {
            noFavorite.setVisibility(View.INVISIBLE);
        }
        recyclerView = mView.findViewById(R.id.favoriteRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        favoriteAdapter = new FavoriteAdapter(articles, R.layout.favorite_list_view, getActivity());
        recyclerView.setAdapter(favoriteAdapter);
        return mView;
    }
}
