package com.example.antony.rssreader.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.antony.rssreader.R;

/**
 * Created by Antony on 2/3/2017.
 */

public class FeedViewHolder extends RecyclerView.ViewHolder {
    public TextView textView;
    public ImageView image;
    public TextView feedTitle;
    public FeedViewHolder(View itemView) {
        super(itemView);
        feedTitle = (TextView) itemView.findViewById(R.id.feedTitle);
        textView = (TextView) itemView.findViewById(R.id.tvFeedTv);
        image = (ImageView) itemView.findViewById(R.id.imgFeed);
    }
}
