package com.example.antony.rssreader.screens.webscreen;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.antony.rssreader.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebContentFragment extends Fragment implements WebScreenContract.View, WebInteractionFragment {
    private WebScreenContract.Presenter mPresenter;
    private WebView mWebView;
    private ProgressBar mProgressBar;
    private String url;
    private static final String KEY_URL = "URLLINK";
    public WebContentFragment() {
    }

    public static WebContentFragment getInstance(String url) {
        WebContentFragment webContentFragment = new WebContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_URL, url);
        webContentFragment.setArguments(bundle);
        return webContentFragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        url = arguments.getString(KEY_URL);
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
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;

            }
        });
        mWebView.loadUrl(url);
    }

    @Override
    public boolean canGoBack() {
        return mWebView.canGoBack();
    }

    @Override
    public void goBack() {
        mWebView.goBack();
    }
}
