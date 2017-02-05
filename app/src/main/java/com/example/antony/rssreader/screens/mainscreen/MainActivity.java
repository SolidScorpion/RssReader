package com.example.antony.rssreader.screens.mainscreen;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.antony.rssreader.R;
import com.example.antony.rssreader.WebController;
import com.example.antony.rssreader.adapters.MenuAdapter;
import com.example.antony.rssreader.networking.DownloadCallBack;
import com.example.antony.rssreader.networking.NetworkFragment;
import com.example.antony.rssreader.screens.content.ContentFragment;
import com.example.antony.rssreader.screens.webscreen.WebContentFragment;
import com.example.antony.rssreader.screens.webscreen.WebInteractionFragment;
import com.example.antony.rssreader.utilities.Constants;



public class MainActivity extends AppCompatActivity implements MainScreenContract.View, WebController {
    private DrawerLayout mDrawerLayout;
    private RecyclerView mMenuRw;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private NetworkFragment mNetworkFragment;
    private MenuAdapter mMenuAdapter;
    private MainScreenContract.Presenter mPresenter;
    private static final String CONTENT_TAG = "contentTag";
    private static final String WEB_TAG = "webContent";

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
        initSideBarMenu();
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open_drawer_descr, R.string.app_name);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
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
        showContentFragment(Constants.KOTAKU_RSS_FEED_LINK);
    }

    private void showContentFragment(String feedLink) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        if (supportFragmentManager.findFragmentByTag(CONTENT_TAG) == null) {
            FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
            ContentFragment instance = ContentFragment.getInstance(feedLink);
            fragmentTransaction.add(R.id.fragmentContainer, instance, CONTENT_TAG);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void cancelDownload() {
        if (mNetworkFragment != null) {
            mNetworkFragment.cancelDownload();
        }
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(findViewById(R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onLinkClicked(String url) {
        FragmentManager manager = getSupportFragmentManager();
        WebContentFragment instance = WebContentFragment.getInstance(url);
        manager.beginTransaction().replace(R.id.fragmentContainer, instance, WEB_TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void sendNetworkRequest(String url, DownloadCallBack callBack) {
        mNetworkFragment.startDownload(url, callBack);
    }

    @Override
    public void onBackPressed() {
        if (isWebFragmentAvailable()) {
            WebInteractionFragment webInteractionFragment = getWebInteractionFragment();
            if (webInteractionFragment.canGoBack()) {
                webInteractionFragment.goBack();
            }
        } else {
            super.onBackPressed();
        }
    }

    private boolean isWebFragmentAvailable() {
        FragmentManager fm = getSupportFragmentManager();
        return fm.findFragmentByTag(WEB_TAG) != null;
    }

    private WebInteractionFragment getWebInteractionFragment() {
        WebInteractionFragment fragmentByTag = (WebInteractionFragment) getSupportFragmentManager().findFragmentByTag(WEB_TAG);
        return fragmentByTag;
    }
}