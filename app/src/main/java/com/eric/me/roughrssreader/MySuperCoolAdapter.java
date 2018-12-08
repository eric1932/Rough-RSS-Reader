package com.eric.me.roughrssreader;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.prof.rssparser.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MySuperCoolAdapter extends RecyclerView.Adapter<MySuperCoolAdapter.ViewHolder> {

    private ArrayList<Article> manyArticles = new ArrayList<>();
    private int cardViewID;
    private Context contextGivenByAncestor;

    MySuperCoolAdapter(ArrayList<Article> articleInput, int n, Context context) {
        manyArticles = articleInput;
        cardViewID = n;
        contextGivenByAncestor = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, intro, date;
        ImageView theImage;

        private ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewTitle);
            intro = itemView.findViewById(R.id.textViewIntro);
            date = itemView.findViewById(R.id.textViewDate);
            theImage = itemView.findViewById(R.id.headImage);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(cardViewID, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int index) {
        Article currentArticle = manyArticles.get(index);
        //author, content might be NULL!
        String title = currentArticle.getTitle(),
                intro = currentArticle.getDescription(),
                date,
                author = currentArticle.getAuthor(),
                content = currentArticle.getContent(),
                linkURL = currentArticle.getLink(),
                imageURL = currentArticle.getImage();
        final String tmp2 = linkURL;

        //title一定有
        viewHolder.title.setText(title);

        //处理content
        if (content == null) {
            content = intro;
        }
        final String tmp = content;

        //intro判断
        boolean introMightBeContent = false;
        if (intro.length() >= 400) {
            introMightBeContent = true;
            //Intro太长或者直接识别成正文就不显示了，大概400+
            Log.d("INTROERROR", "Introduction exceeds max length.");
            intro = "";
            viewHolder.intro.setHeight(0);
        } else {
            viewHolder.intro.setText(intro);
        }

        //date判断
        if (currentArticle.getPubDate() != null) {
            date = currentArticle.getPubDate().toString();
            viewHolder.date.setText(date);
        } else {
            date = "";
            viewHolder.date.setMaxHeight(0);
        }

        //image判断
        if (imageURL == null || !imageURL.contains(".png") && !imageURL.contains(".jpg") && !imageURL.contains(".jpeg") && !imageURL.contains(".gif")) {
            Log.d("IMAGEERROR", "Image URL is null OR does not include proper image URL: " + imageURL);
            imageURL = "";
            viewHolder.theImage.setImageResource(R.drawable.no_image_available);
        } else {
            //show image
            Picasso.get().load(imageURL).into(viewHolder.theImage);
        }


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contextGivenByAncestor, WebViewActivity.class);
                intent.putExtra("tmp", tmp);
                intent.putExtra("URL", tmp2);
                contextGivenByAncestor.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (manyArticles == null) {
            return -1;
        } else {
            return manyArticles.size();
        }
    }
}
