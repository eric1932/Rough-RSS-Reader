package com.eric.me.roughrssreader;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.prof.rssparser.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MySuperCoolAdapter extends RecyclerView.Adapter<MySuperCoolAdapter.ViewHolder> {

    private ArrayList<Article> articleS = new ArrayList<>();
    private int rowNums;
    //不知道下面这个什么意思
    //TODO why never used
    private Context myDontKnowContext;

    public MySuperCoolAdapter(ArrayList<Article> articleList, int n, Context context) {
        articleS = articleList;
        rowNums = n;
        myDontKnowContext = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView date;
        TextView intro;
        ImageView headPicture;
        Button button;

        //原来是public
        private ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewTitle);
            date = itemView.findViewById(R.id.textViewDate);
            intro = itemView.findViewById(R.id.textViewIntro);
            headPicture = itemView.findViewById(R.id.imageHeadPicture);
            button = itemView.findViewById(R.id.buttonBrowser);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(rowNums, viewGroup, false);
        return new ViewHolder(v);
    }

    //最重要的部分
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int index) {
        Article currentArticle = articleS.get(index);
        String titleStr = currentArticle.getTitle();
        String dateStr = currentArticle.getPubDate().toString();
        String introStr = currentArticle.getDescription();
        String imageURL = currentArticle.getImage();
        final String originalURL = currentArticle.getLink();

        viewHolder.title.setText(titleStr);
        viewHolder.date.setText(dateStr);
        viewHolder.intro.setText(introStr);
        // to show image
        Picasso.get().load(imageURL).into(viewHolder.headPicture);
        // Button Listener
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(originalURL);
                myDontKnowContext.startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (articleS == null) {
            return -1;
        } else {
            return articleS.size();
        }
    }
}
//TODO 图片太小了
