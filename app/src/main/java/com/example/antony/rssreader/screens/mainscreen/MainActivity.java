package com.example.antony.rssreader.screens.mainscreen;

import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.antony.rssreader.R;
import com.example.antony.rssreader.adapters.FeedAdapter;
import com.example.antony.rssreader.models.RssFeed;
import com.example.antony.rssreader.networking.DownloadCallBack;
import com.example.antony.rssreader.networking.NetworkFragment;
import com.example.antony.rssreader.utilities.Constants;

public class MainActivity extends AppCompatActivity implements DownloadCallBack<RssFeed>{
    private DrawerLayout mDrawerLayout;
    private RecyclerView mMenuRw;
    private RecyclerView mMainContentRw;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private NetworkFragment mNetworkFragment;
    private Button mBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mBtn  = (Button) findViewById(R.id.btn);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNetworkFragment.startDownload();
            }
        });
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mMenuRw = (RecyclerView) findViewById(R.id.menuRw);
        mMenuRw.setLayoutManager(new LinearLayoutManager(this));
        mMenuRw.setAdapter(new FeedAdapter());
        mMainContentRw = (RecyclerView) findViewById(R.id.contentRw);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open_drawer_descr, R.string.app_name);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mNetworkFragment = NetworkFragment.getInstance(Constants.KOTAKU_RSS_FEED_LINK, getSupportFragmentManager());
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public NetworkInfo getNetworkInfo() {
        ConnectivityManager systemService = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = systemService.getActiveNetworkInfo();
        return activeNetworkInfo;
    }

    @Override
    public void deliverResult(RssFeed result) {
        String rawResponse = result.getRawResponse();
    }

    @Override
    public void cancelDownload() {
        if (mNetworkFragment !=null) {
            mNetworkFragment.cancelDownload();
        }
    }
}
