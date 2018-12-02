package com.eric.me.roughrssreadernew;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //没有网络
        boolean state = !NetWorkUtils.isNetworkConnected(getApplication());
        if (state) {
            ConstraintLayout constraintLayout = findViewById(R.id.constraintLayoutTest);
            constraintLayout.setVisibility(View.INVISIBLE);
            LinearLayout linearLayout = findViewById(R.id.backLayout);

            TextView noNetwork = new TextView(TestActivity.this);
            noNetwork.setText("Network Not Available");
            linearLayout.addView(noNetwork);

            Button refresh = new Button(getApplication());
            refresh.setText("RETRY");
            refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    refresh();
                }
            });
            refresh.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.addView(refresh);
        }
    }

    public void onClickAndroidAuthority(View v) {
        startActivityWithParams("https://www.androidauthority.com/feed");
    }

    public void onClickIThome(View v) {
        startActivityWithParams("https://www.ithome.com/rss/");
    }

    public void onClickNYTimes(View v) {
        startActivityWithParams("http://rss.nytimes.com/services/xml/rss/nyt/US.xml");
    }

    public void onClickCNN(View v) {
        startActivityWithParams("http://rss.cnn.com/rss/cnn_us.rss");
    }

    public void onClickCNBeta(View v) {
        startActivityWithParams("http://rssdiy.com/u/2/cnbeta.xml");
    }

    public void onClickSspai(View v) {
        startActivityWithParams("https://sspai.com/feed");
    }

    public void onCLickZhihu(View v) {
        startActivityWithParams("http://www.zhihu.com/rss");
    }

    private void startActivityWithParams(String... URL) {
        Intent intent = new Intent(TestActivity.this, ReadingActivity.class);
        intent.putExtra("URL", URL);
        startActivity(intent);
    }

    public void onClickCustomURL(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Custom URL:");
        final EditText editText = new EditText(this);
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
                    Toast.makeText(TestActivity.this, "Please input a leagal URL.", Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    public void onClickReload(View v) {
        refresh();
    }

    private void refresh() {
        finish();
        startActivity(getIntent());
    }
}
