package com.example.antony.rssreader.screens.content;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.antony.rssreader.R;
import com.example.antony.rssreader.UrlClickListener;
import com.example.antony.rssreader.adapters.FeedAdapter;

public class ContentFragment extends Fragment implements ContentFragmentContract.View {
    private FeedAdapter feedAdapter;
    private RecyclerView mMainContentRw;
    private SwipeRefreshLayout swipeRefreshLayout;
    private UrlClickListener mUrlClickListener;
    private ContentFragmentContract.Presenter mPresenter;
    public ContentFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof UrlClickListener)) {
            throw new IllegalStateException("Must implement url click listener");
        }
        mUrlClickListener = (UrlClickListener) context;
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
        mPresenter = new ContentFragmentPresenterImpl(this);
        mMainContentRw = (RecyclerView) view.findViewById(R.id.contentRw);
        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        mMainContentRw.setLayoutManager(layout);
        feedAdapter = new FeedAdapter(mUrlClickListener);
        feedAdapter.updateData(mPresenter.queryDatabase());
        mMainContentRw.setAdapter(feedAdapter);
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getContext(), layout.getOrientation());
        mMainContentRw.addItemDecoration(dividerItemDecoration);
    }

}
