package com.example.antony.rssreader.networking;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.example.antony.rssreader.models.RssFeed;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Pripachkin on 03.02.2017.
 */

public class DownloadTask extends AsyncTask<String, Void, RssFeed> {
    private DownloadCallBack<RssFeed> mCallback;
    private static final String TAG = "DownloadTask";
    private String originalLink;
    public DownloadTask (DownloadCallBack<RssFeed> callBack) {
        setCallBack(callBack);
    }

    private void setCallBack(DownloadCallBack<RssFeed> callBack) {
        this.mCallback = callBack;
    }

    @Override
    protected void onPreExecute() {
        if (mCallback != null) {
            NetworkInfo networkInfo = mCallback.getNetworkInfo();
            if (networkInfo == null || !networkInfo.isConnected() ||
                    (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                            && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
                // If no connectivity, cancel task and update Callback with null data.
                cancel(true);
            }
        }
    }

    @Override
    protected RssFeed doInBackground(String... params) {
        RssFeed rssFeed = null;
        if (!isCancelled() && params != null && params.length > 0) {
            originalLink = params[0];
            try {
                URL url = new URL(originalLink);
                rssFeed = downloadUrl(url);
                return rssFeed;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(RssFeed rssFeed) {
        if (rssFeed != null && mCallback != null) {
            mCallback.deliverResult(rssFeed);
        }
    }

    private RssFeed downloadUrl(URL url) {
        InputStream steam = null;
        HttpURLConnection connection = null;
        String result = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("Got code: " + responseCode);
            }
            steam = connection.getInputStream();
            if (steam != null) {
                InputStreamReader reader = new InputStreamReader(steam);
                BufferedReader bufferedReader= new BufferedReader(reader);
                String response = readStream(steam);
                return new RssFeed(response, originalLink);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (steam != null) {
                try {
                    steam.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    private String readStream(InputStream steam){
        StringBuilder sb = new StringBuilder();
        InputStreamReader reader = new InputStreamReader(steam);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String tmp;
        try {
            while ((tmp = bufferedReader.readLine()) != null) {
                sb.append(tmp);
            }
        } catch (IOException e) {
            Log.d(TAG, "error reading stream");
        } finally {
            if (steam != null) {
                try {
                    steam.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, " error closing stream");
                }
            }
        }
        return sb.toString();
    }
}
