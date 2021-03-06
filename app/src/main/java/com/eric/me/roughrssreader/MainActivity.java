package com.eric.me.roughrssreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.prof.rssparser.Article;
import com.prof.rssparser.Parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<Article> articleList;
    private int loaded = 0, feedNumber;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //my code below

        getData();

        IOHelper ioHelper = new IOHelper(getApplication());
        if (!ioHelper.isExist("favorite.txt")) {
            ioHelper.writeFile("", "favorite.txt", true);
        }

        //auto select "News"
        navigationView.getMenu().getItem(0).setChecked(true);
        onNavigationItemSelected(navigationView.getMenu().getItem(0));

        //hide unnecessary things
        navigationView.getMenu().findItem(R.id.nav_tools).setVisible(false);
        navigationView.getMenu().findItem(R.id.communicate).setVisible(false);
        findViewById(R.id.buttonEnterTest).setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //get floatingActionButton
        fab = findViewById(R.id.fab);
        fab.hide();
        //Prepare to inflate fragment
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_news) {
            /* example
            fragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, new FirstFragment())
                    .commit(); */
            fragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, new ReadingFragment())
                    .commit();
        } else if (id == R.id.nav_sites) {
            fab.show();
            fragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, new SiteManagerFragment())
                    .commit();
        } else if (id == R.id.nav_favorites) {
            fragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, new FavoriteFragment())
                    .commit();
        } else if (id == R.id.nav_tools) {
            fragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, new TestActivityFragment())
                    .commit();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClickEnterTestActivity(View v) {
        startActivity(new Intent(getApplication(), TestActivity.class));
    }

    public void onClickTest(View v) {
        if (articleList != null) {
            Toast.makeText(getApplicationContext(), "" + articleList.get(0).getPubDate().getTime(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "null", Toast.LENGTH_LONG).show();
        }
    }

    private void addFeed(String url) {
        if (articleList == null) {
            articleList = new ArrayList<>();
        }
        Parser parser = new Parser();
        parser.execute(url);
        parser.onFinish(new Parser.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(ArrayList<Article> arrayList) {
                articleList.addAll(arrayList);
                loaded++;
                //sortArticle();
            }

            @Override
            public void onError() {
                //do nothing
            }
        });
    }

    private void sortArticle() {
        articleList = sortArticle(articleList);
    }

    public static ArrayList<Article> sortArticle(ArrayList<Article> articleList) {
        //make a new comparable list
        ArrayList<ArticleComparable> articleComparable = new ArrayList<>();
        for (int i = 0; i < articleList.size(); i++) {
            articleComparable.add(new ArticleComparable(articleList.get(i)));
        }
        Collections.sort(articleComparable);
        ArrayList<Article> toReplace = new ArrayList<>();
        for (ArticleComparable ac : articleComparable) {
            toReplace.add(ac.getArticle());
        }
        return toReplace;
    }

    void clearFeed() {
        articleList = new ArrayList<>();
    }

    private Site[] getFeedOrDefault() {
        IOHelper ioHelper = new IOHelper(getApplication());
        String json;
        String fileName = "feedlist.txt";
        if (ioHelper.isExist(fileName)) {
            try {
                json = ioHelper.readFile(fileName);
            } catch (IOException e) {
                e.printStackTrace();
                //failed
                return SiteHelper.getDefaultSiteArray();
            }
            if (json.equals("[]\n")) {
                ioHelper.writeFile(SiteHelper.getDefaultJson(), fileName, true);
                return SiteHelper.getDefaultSiteArray();
            }
            return (new SiteHelper(json)).getArray();
        } else {
            ioHelper.writeFile(SiteHelper.getDefaultJson(), fileName, true);
            return SiteHelper.getDefaultSiteArray();
        }
    }

    void getData() {
        Site[] siteList = getFeedOrDefault();
        feedNumber = siteList.length;
        for (Site x : siteList) {
            addFeed(x.getUrl());
        }
    }

    /*Getters*/

    public ArrayList<Article> getArticleList() {
        return articleList;
    }

    //TODO to be refined
    public ArrayList<Site> getSiteListAsArrayList() {
        IOHelper ioHelper = new IOHelper(getApplication());
        String json;
        Site[] siteList;
        String fileName = "feedlist.txt";
        if (ioHelper.isExist(fileName)) {
            try {
                json = ioHelper.readFile(fileName);
            } catch (IOException e) {
                e.printStackTrace();
                //failed
                return new ArrayList<>();
            }
            return (new SiteHelper(json)).getArrayList();
        } else {
            ioHelper.writeFile(SiteHelper.getDefaultJson(), fileName, true);
            return SiteHelper.getDefaultSiteArrayList();
        }
    }

    public int getLoaded() {
        return loaded;
    }

    public int getFeedNumber() {
        return feedNumber;
    }

    FloatingActionButton getFAB() {
        return fab;
    }

    /*private static long date2ms(String input) {
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        try {
            Date date = format.parse(input);
            return date.getTime();
        } catch (ParseException e) {
            return 0;
        }
    }*/
}
