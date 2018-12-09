package com.eric.me.roughrssreader;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class SiteAdapter extends RecyclerView.Adapter<SiteAdapter.ViewHolder> {

    private ArrayList<Site> siteArrayList;
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
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        Site currentSite = siteArrayList.get(i);
        String siteName = currentSite.getSiteName();
        viewHolder.tvSiteName.setText(siteName);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = viewHolder.getAdapterPosition();
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Remove Sources?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeItemAndSave(position);
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(true);
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (siteArrayList == null) {
            return -1;
        } else {
            return siteArrayList.size();
        }
    }

    private void removeItemAndSave(final int index) {
        siteArrayList.remove(index);
        SiteHelper siteHelper = new SiteHelper(siteArrayList);
        String newJson = siteHelper.toJson();
        IOHelper ioHelper = new IOHelper(mContext);
        ioHelper.writeFile(newJson, "feedlist.txt", true);
    }
}
