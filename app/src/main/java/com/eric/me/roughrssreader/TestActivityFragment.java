package com.eric.me.roughrssreader;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Copied from TestActivity.java
 */
public class TestActivityFragment extends Fragment {

    private View mView;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_test, container, false);
        mContext = mView.getContext();
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mView.findViewById(R.id.buttonAndroidAuthority).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityWithParams("https://www.androidauthority.com/feed");
            }
        });
        mView.findViewById(R.id.buttonIThome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityWithParams("https://www.ithome.com/rss/");
            }
        });
        mView.findViewById(R.id.buttonNYTimes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityWithParams("http://rss.nytimes.com/services/xml/rss/nyt/US.xml");
            }
        });
        mView.findViewById(R.id.buttonCNN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityWithParams("http://rss.cnn.com/rss/cnn_us.rss");
            }
        });
        mView.findViewById(R.id.buttonSspai).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityWithParams("https://sspai.com/feed");
            }
        });
        mView.findViewById(R.id.buttonZhihu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityWithParams("http://www.zhihu.com/rss");
            }
        });
        mView.findViewById(R.id.buttonCustomFeed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Custom URL:");
                final EditText editText = new EditText(mContext);
                //TODO set padding
                builder.setView(editText);
                builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String URL = editText.getText().toString();
                        if (!URL.startsWith("http://") && !URL.startsWith("https://")) {
                            URL = "http://" + URL;
                        }
                        if (ReadingActivity.testSingleFeed(URL)) {
                            startActivityWithParams(URL);
                        } else {
                            Toast.makeText(mContext, "Please input a leagal URL.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                builder.setCancelable(true);
                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
        });
        mView.findViewById(R.id.buttonRefresh).setVisibility(View.INVISIBLE);
    }

    private void startActivityWithParams(String... URL) {
        Intent intent = new Intent(mContext, ReadingActivity.class);
        intent.putExtra("URL", URL);
        startActivity(intent);
    }
}
