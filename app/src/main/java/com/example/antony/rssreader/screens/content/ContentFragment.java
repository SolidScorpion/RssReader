package com.example.antony.rssreader.screens.content;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.antony.rssreader.R;
import com.example.antony.rssreader.WebController;
import com.example.antony.rssreader.adapters.FeedAdapter;
import com.example.antony.rssreader.database.RssDatabaseImpl;
import com.example.antony.rssreader.database.RssFeedDatabase;
import com.example.antony.rssreader.models.RssFeed;
import com.example.antony.rssreader.models.RssFeedItem;
import com.example.antony.rssreader.networking.DownloadCallBack;

import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class ContentFragment extends Fragment implements ContentFragmentContract.View, DownloadCallBack<RssFeed> {
    private FeedAdapter feedAdapter;
    private RecyclerView mMainContentRw;
    private SwipeRefreshLayout swipeRefreshLayout;
    private WebController mWebController;
    private RssFeedDatabase rssFeedDatabase;
    private String url;
    private static final String URL_KEY = "URLKEY_CONTENT";
    private ContentFragmentContract.Presenter mPresenter;

    public ContentFragment() {
    }

    public static ContentFragment getInstance(String url) {
        Bundle bundle = new Bundle();
        bundle.putString(URL_KEY, url);
        ContentFragment contentFragment = new ContentFragment();
        contentFragment.setArguments(bundle);
        return contentFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getArguments().getString(URL_KEY);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof WebController)) {
            throw new IllegalStateException("Must implement url click listener");
        }
        mWebController = (WebController) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initMainContent(view);
    }

    private void initMainContent(View view) {
        rssFeedDatabase = new RssDatabaseImpl(getContext());
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.fetchData(url);
            }
        });
        mPresenter = new ContentFragmentPresenterImpl(this, rssFeedDatabase);
        mMainContentRw = (RecyclerView) view.findViewById(R.id.contentRw);
        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        mMainContentRw.setLayoutManager(layout);
        feedAdapter = new FeedAdapter(mWebController);
        mPresenter.queryDatabase(url);
        mMainContentRw.setAdapter(feedAdapter);
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getContext(), layout.getOrientation());
        mMainContentRw.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public NetworkInfo getNetworkInfo() {
        ConnectivityManager systemService = (ConnectivityManager) getContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = systemService.getActiveNetworkInfo();
        return activeNetworkInfo;
    }

    @Override
    public void deliverResult(RssFeed result) {
        mPresenter.OnDeliveredResult(result);
    }

    @Override
    public void cancelDownload() {
        mWebController.cancelDownload();
    }

    @Override
    public void showData(List<RssFeedItem> resultList) {
        swipeRefreshLayout.setRefreshing(false);
        feedAdapter.updateData(resultList);
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(mMainContentRw, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void makeNetworkCall(String link) {
        mWebController.sendNetworkRequest(link, this);
    }
}
