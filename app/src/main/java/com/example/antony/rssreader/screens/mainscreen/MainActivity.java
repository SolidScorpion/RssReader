package com.example.antony.rssreader.screens.mainscreen;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;

import com.example.antony.rssreader.R;
import com.example.antony.rssreader.WebController;
import com.example.antony.rssreader.adapters.MenuAdapter;
import com.example.antony.rssreader.adapters.SimpleFragmentAdapter;
import com.example.antony.rssreader.database.RssDatabaseImpl;
import com.example.antony.rssreader.models.MenuItem;
import com.example.antony.rssreader.networking.DownloadCallBack;
import com.example.antony.rssreader.networking.NetworkFragment;
import com.example.antony.rssreader.screens.AddMenuItemDialog;
import com.example.antony.rssreader.screens.webscreen.WebContentFragment;
import com.example.antony.rssreader.screens.webscreen.WebInteractionFragment;
import com.example.antony.rssreader.utilities.Constants;

import java.util.List;


public class MainActivity extends AppCompatActivity implements MainScreenContract.View, WebController, MenuAdapter.MenuItemClickListener, AddMenuItemDialog.AddMenuListener {
    private DrawerLayout mDrawerLayout;
    private RecyclerView mMenuRw;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private NetworkFragment mNetworkFragment;
    private MenuAdapter mMenuAdapter;
    private MainScreenContract.Presenter mPresenter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private SimpleFragmentAdapter pagerAdapter;
    private static final String DIALOG_TAG = "addMenuDialog";
    private static final String WEB_TAG = "webContent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        pagerAdapter = new SimpleFragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);
        mNetworkFragment = NetworkFragment.getInstance(getSupportFragmentManager());
        mPresenter = new MainScreenPresenterImpl(this, new RssDatabaseImpl(this));
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
        mMenuAdapter = new MenuAdapter(this);
        mMenuRw.setAdapter(mMenuAdapter);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
        mPresenter.getMenuItems();
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
    public void showMenuItems(List<MenuItem> data) {
        pagerAdapter.updateData(data);
        mMenuAdapter.updateData(data);
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
            } else {
                super.onBackPressed();
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

    @Override
    public void addMenuItem() {
        AddMenuItemDialog addMenuItemDialog = new AddMenuItemDialog();
        addMenuItemDialog.show(getSupportFragmentManager(),DIALOG_TAG);
    }

    @Override
    public void onMenuItemClick(MenuItem item) {
        TabLayout.Tab menuItemTab = getMenuItemTab(item);
        if (menuItemTab != null) {
            menuItemTab.select();
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private TabLayout.Tab getMenuItemTab(MenuItem item) {
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tabAt = mTabLayout.getTabAt(i);
            if (tabAt.getText().equals(item.getServiceName())) {
                return tabAt;
            }
        }
        return null;
    }

    @Override
    public boolean removeItem(MenuItem item) {
        if (item.getServiceUrl().equals(Constants.KOTAKU_RSS_FEED_LINK)) {
            return false;
        }
        mPresenter.removeItem(item);
        pagerAdapter.removeItem(item);
        TabLayout.Tab menuItemTab = getMenuItemTab(item);
        if (menuItemTab != null) {
            mTabLayout.removeTab(menuItemTab);
        }
        return true;
    }

    @Override
    public void onItemAdded(MenuItem item) {
        mPresenter.saveMenuItem(item);
        mTabLayout.addTab(mTabLayout.newTab());
        pagerAdapter.addUrl(item);
        mMenuAdapter.addMenuItem(item);

    }
}