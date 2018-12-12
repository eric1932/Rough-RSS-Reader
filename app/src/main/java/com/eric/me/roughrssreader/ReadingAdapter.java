package com.eric.me.roughrssreader;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.prof.rssparser.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ReadingAdapter extends RecyclerView.Adapter<ReadingAdapter.ViewHolder> {

    private ArrayList<Article> manyArticles = new ArrayList<>();
    private int cardViewID;
    private Context contextGivenByAncestor;

    ReadingAdapter(ArrayList<Article> articleInput, int n, Context context) {
        manyArticles = articleInput;
        cardViewID = n;
        contextGivenByAncestor = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, intro, date;
        ImageView theImage, star;

        private ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.favoriteTitle);
            intro = itemView.findViewById(R.id.textViewIntro);
            date = itemView.findViewById(R.id.textViewDate);
            theImage = itemView.findViewById(R.id.headImage);
            star = itemView.findViewById(R.id.favoratedStar);
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
        final Article currentArticle = manyArticles.get(index);
        //author, content might be NULL!
        String title = currentArticle.getTitle(),
                intro = currentArticle.getDescription(),
                date,
                author = currentArticle.getAuthor(),
                content = currentArticle.getContent(),
                linkURL = currentArticle.getLink(),
                imageURL = currentArticle.getImage();
        final String tmp2 = linkURL;

        //title一
        viewHolder.title.setText(title);

        //process content
        if (content == null) {
            content = intro;
        }
        final String tmp = content;

        //intro
        boolean introMightBeContent = false;
        if (intro.length() >= 250) {
            introMightBeContent = true;
            //Intro too long
            Log.d("INTROERROR", "Introduction exceeds max length.");
            intro = "";
            viewHolder.intro.setHeight(0);
        } else {
            viewHolder.intro.setText(intro);
        }

        //date
        if (currentArticle.getPubDate() != null) {
            date = currentArticle.getPubDate().toString();
            viewHolder.date.setText(date);
        } else {
            date = "";
            viewHolder.date.setMaxHeight(0);
        }

        //image
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

        final FavoriteHelper favoriteHelper = new FavoriteHelper(contextGivenByAncestor);
        if (favoriteHelper.inFavorite(currentArticle)) {
            viewHolder.star.setImageResource(R.drawable.ic_star_yellow_24dp);
        } else {
            viewHolder.star.setImageResource(R.drawable.ic_star_border_black_24dp);
        }
        viewHolder.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favoriteHelper.inFavorite(currentArticle)) {
                    viewHolder.star.setImageResource(R.drawable.ic_star_border_black_24dp);
                    favoriteHelper.removeFromFavorite(currentArticle);
                    showProcess("Removed from favorite list.");
                } else {
                    viewHolder.star.setImageResource(R.drawable.ic_star_yellow_24dp);
                    favoriteHelper.addToFavorite(currentArticle);
                    showProcess("Added to favorite list.");
                }
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

    private void showProcess(final String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(contextGivenByAncestor);
        ProgressBar progressBar = new ProgressBar(contextGivenByAncestor);
        builder.setView(progressBar);
        final AlertDialog alertDialog = builder.create();
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog.dismiss();
                Toast.makeText(contextGivenByAncestor, text, Toast.LENGTH_LONG).show();
            }
        }, 500);
        alertDialog.show();
    }
}
