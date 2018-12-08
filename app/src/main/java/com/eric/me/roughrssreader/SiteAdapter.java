package com.eric.me.roughrssreader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class SiteAdapter extends RecyclerView.Adapter<SiteAdapter.ViewHolder> {

    private ArrayList<Site> siteArrayList = new ArrayList<>();
    private int cardViewID;
    private Context mContext;

    SiteAdapter(ArrayList<Site> sa, int cvID, Context c) {
        siteArrayList = sa;
        cardViewID = cvID;
        mContext = c;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvSiteName;

        private ViewHolder(View itemView) {
            super(itemView);
            tvSiteName = itemView.findViewById(R.id.textViewSiteName);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(cardViewID, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Site currentSite = siteArrayList.get(i);
        String siteName = currentSite.getSiteName();
        viewHolder.tvSiteName.setText(siteName);
    }

    @Override
    public int getItemCount() {
        if (siteArrayList == null) {
            return -1;
        } else {
            return siteArrayList.size();
        }
    }
}
