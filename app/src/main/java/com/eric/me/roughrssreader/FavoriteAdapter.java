package com.eric.me.roughrssreader;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
        ImageView star;

        private ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.favoriteTitle);
            star = itemView.findViewById(R.id.favoratedStar);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(singleItemLayoutID, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final Article currentArticle = favoriteArticles.get(i);
        viewHolder.title.setText(currentArticle.getTitle());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(mContext, Uri.parse(currentArticle.getLink()));
            }
        });

        final FavoriteHelper favoriteHelper = new FavoriteHelper(mContext);
        viewHolder.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //viewHolder.star.setImageResource(R.drawable.ic_star_border_black_24dp);
                favoriteHelper.removeFromFavorite(currentArticle);
                //不理解
                favoriteArticles.remove(viewHolder.getAdapterPosition());
                notifyDataSetChanged();
                showProcess("Removed from favorite list.");
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

    //duplicate code
    private void showProcess(final String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        ProgressBar progressBar = new ProgressBar(mContext);
        builder.setView(progressBar);
        final AlertDialog alertDialog = builder.create();
        //透明背景
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog.dismiss();
                Toast.makeText(mContext, text, Toast.LENGTH_LONG).show();
            }
        }, 500);
        alertDialog.show();
    }
}
