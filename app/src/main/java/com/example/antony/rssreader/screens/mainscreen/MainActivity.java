package com.example.antony.rssreader.screens.mainscreen;

import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.antony.rssreader.R;
import com.example.antony.rssreader.adapters.FeedAdapter;
import com.example.antony.rssreader.adapters.MenuAdapter;
import com.example.antony.rssreader.models.RssFeed;
import com.example.antony.rssreader.models.RssFeedItem;
import com.example.antony.rssreader.networking.DownloadCallBack;
import com.example.antony.rssreader.networking.NetworkFragment;
import com.example.antony.rssreader.utilities.Constants;

import java.util.List;

public class MainActivity extends AppCompatActivity implements DownloadCallBack<RssFeed>, MainScreenContract.View {
    private DrawerLayout mDrawerLayout;
    private RecyclerView mMenuRw;
    private RecyclerView mMainContentRw;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private NetworkFragment mNetworkFragment;
    private FeedAdapter feedAdapter;
    private MenuAdapter mMenuAdapter;
    private MainScreenContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mNetworkFragment = NetworkFragment.getInstance(getSupportFragmentManager());
        mPresenter = new MainScreenPresenterImpl(this);
        setSupportActionBar(mToolbar);
        initMainContent();
        initSideBarMenu();
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open_drawer_descr, R.string.app_name);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void initMainContent() {
        mMainContentRw = (RecyclerView) findViewById(R.id.contentRw);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        mMainContentRw.setLayoutManager(layout);
        feedAdapter = new FeedAdapter();
        mMainContentRw.setAdapter(feedAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layout.getOrientation());
        mMainContentRw.addItemDecoration(dividerItemDecoration);
    }

    private void initSideBarMenu() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mMenuRw = (RecyclerView) findViewById(R.id.menuRw);
        mMenuRw.setLayoutManager(new LinearLayoutManager(this));
        mMenuAdapter = new MenuAdapter();
        mMenuRw.setAdapter(mMenuAdapter);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
        mPresenter.fetchData(Constants.KOTAKU_RSS_FEED_LINK);
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
        mPresenter.OnDeliveredResult(result);
    }

    @Override
    public void cancelDownload() {
        if (mNetworkFragment != null) {
            mNetworkFragment.cancelDownload();
        }
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(mMainContentRw, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showData(List<RssFeedItem> resultList) {
        feedAdapter.updateData(resultList);
    }

    @Override
    public void makeNetworkCall(String link) {
        mNetworkFragment.startDownload(link);
    }
}
