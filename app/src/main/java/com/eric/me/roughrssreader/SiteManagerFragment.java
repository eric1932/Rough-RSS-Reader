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

import java.util.ArrayList;

public class SiteManagerFragment extends Fragment {

    private View view;
    private ArrayList<Site> siteArrayList;
    private SiteAdapter siteAdapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_site_list, container, false);
        siteArrayList = ((MainActivity) getActivity()).getSiteListAsArrayList();
        recyclerView = view.findViewById(R.id.fragmentedRecyclerSiteList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        siteAdapter = new SiteAdapter(siteArrayList, R.layout.site_list_view, getActivity());
        recyclerView.setAdapter(siteAdapter);
        return view;
    }
}
