package com.example.antony.rssreader.adapters;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.antony.rssreader.R;
import com.example.antony.rssreader.models.RssFeedItem;
import com.example.antony.rssreader.utilities.CommonUtils;
import com.example.antony.rssreader.utilities.RssFeedItemDiffCallback;
import com.example.antony.rssreader.utilities.RssItemUtil;
import com.example.antony.rssreader.viewholders.FeedViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antony on 2/3/2017.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder> {
    private List<RssFeedItem> items = new ArrayList<>();
    private static final String TAG = "FeedAdapter";

    public void updateData(List<RssFeedItem> newData) {
        if (newData != null) {
            RssFeedItemDiffCallback rssFeedItemDiffCallback = new RssFeedItemDiffCallback(items, newData);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(rssFeedItemDiffCallback);
            items.clear();
            items.addAll(newData);
            diffResult.dispatchUpdatesTo(this);
        }
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.feed_item, parent, false);
        return new FeedViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final FeedViewHolder holder, int position) {
        RssFeedItem rssFeedItem = items.get(position);
        holder.image.setImageBitmap(null);
        String imgUrl = rssFeedItem.getImgUrl();
        if (RssItemUtil.isImgUtl(rssFeedItem.getImgUrl())) {
            Log.d(TAG, imgUrl);
        }
        holder.textView.setText(CommonUtils.fromHtml(rssFeedItem.getDescription()));
        holder.feedTitle.setText(rssFeedItem.getTitle());
    }

    @Override
    public void onViewRecycled(FeedViewHolder holder) {
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
