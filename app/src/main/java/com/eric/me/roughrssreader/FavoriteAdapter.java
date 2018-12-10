package com.eric.me.roughrssreader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prof.rssparser.Article;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private ArrayList<Article> favoriteArticles;
    private int singleItemLayoutID;
    private Context mContext;

    FavoriteAdapter(ArrayList<Article> a, int id, Context c) {
        favoriteArticles = a;
        singleItemLayoutID = id;
        mContext = c;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        private ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.favoriteTitle);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(singleItemLayoutID, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Article currentArticle = favoriteArticles.get(i);
        viewHolder.title.setText(currentArticle.getTitle());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO alertdialog
            }
        });
    }

    @Override
    public int getItemCount() {
        if (favoriteArticles == null) {
            return -1;
        } else {
            return favoriteArticles.size();
        }
    }
}
