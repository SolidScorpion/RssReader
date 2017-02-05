package com.example.antony.rssreader.networking;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Pripachkin on 04.02.2017.
 */

public class ImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView imageView;

    public ImageAsyncTask(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        if (params != null && params.length > 0) {
            String link = params[0];
            try {
                URL url = new URL(link);
                InputStream inputStream = url.openStream();
                return BitmapFactory.decodeStream(inputStream);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null) {
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, imageView.getWidth(), imageView.getHeight());
            imageView.setImageBitmap(bitmap);
        }
    }
}
