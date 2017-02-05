package com.example.antony.rssreader.screens.webscreen;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.antony.rssreader.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebContentFragment extends Fragment implements WebScreenContract.View {
    private WebScreenContract.Presenter mPresenter;
    private WebView mWebView;
    public WebContentFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web_content, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mWebView = (WebView) view.findViewById(R.id.webView);
        mPresenter = new WebScreenPresenterImpl(this);
    }
}
