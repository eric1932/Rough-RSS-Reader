package com.eric.me.roughrssreader;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class SiteManagerFragment extends Fragment {

    private View view;
    private ArrayList<Site> siteArrayList;
    private SiteAdapter siteAdapter;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_site_list, container, false);
        siteArrayList = ((MainActivity) getActivity()).getSiteListAsArrayList();
        recyclerView = view.findViewById(R.id.fragmentedRecyclerSiteList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        siteAdapter = new SiteAdapter(siteArrayList, R.layout.site_list_view, getActivity());
        recyclerView.setAdapter(siteAdapter);

        fab = ((MainActivity) getActivity()).getFAB();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Add a new source");
                final EditText editTextTitle = new EditText(getActivity());
                final EditText editTextURL = new EditText(getActivity());
                LinearLayout linearLayout = new LinearLayout(getActivity());
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.addView(editTextTitle);
                linearLayout.addView(editTextURL);
                builder.setView(linearLayout);
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO
                        String title = editTextTitle.getText().toString();
                        String url = editTextURL.getText().toString();
                        if (!ReadingActivity.testSingleFeed(url)) {
                            Snackbar.make(view, "Please input legal rss feed url", Snackbar.LENGTH_LONG).show();
                        } else {
                            addSite(title, url);
                            Snackbar.make(view, "Success", Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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

        return view;
    }

    void addSite(String title, String url) {
        Site site = new Site(title, url);
        siteArrayList.add(site);
        SiteHelper siteHelper = new SiteHelper(siteArrayList);
        String newJson = siteHelper.toJson();
        IOHelper ioHelper = new IOHelper(getActivity());
        ioHelper.writeFile(newJson, "feedlist.txt", true);
        siteAdapter.notifyDataSetChanged();
    }
}
