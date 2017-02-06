package com.example.antony.rssreader.adapters;

import android.content.Context;
import android.support.v4.util.LruCache;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.antony.rssreader.R;
import com.example.antony.rssreader.WebController;
import com.example.antony.rssreader.models.RssFeedItem;
import com.example.antony.rssreader.networking.ImageAsyncTask;
import com.example.antony.rssreader.utilities.CommonUtils;
import com.example.antony.rssreader.utilities.RssFeedItemDiffCallback;
import com.example.antony.rssreader.utilities.RssItemUtil;
import com.example.antony.rssreader.viewholders.FeedViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Antony on 2/3/2017.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder> {
    private List<RssFeedItem> items = new ArrayList<>();
    private Map<ImageView, ImageAsyncTask> cacheMap = new HashMap<>();
    private static final String TAG = "FeedAdapter";
    private WebController clickListener;
    public FeedAdapter(WebController clickListener) {
        this.clickListener = clickListener;
    }
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
            ImageAsyncTask imageAsyncTask = new ImageAsyncTask(holder.image);
            cacheMap.put(holder.image, imageAsyncTask);
            imageAsyncTask.execute(imgUrl);
        }
        holder.textView.setText(CommonUtils.fromHtml(rssFeedItem.getDescription(), clickListener));
        holder.textView.setMovementMethod(LinkMovementMethod.getInstance());
        holder.feedTitle.setText(rssFeedItem.getTitle());
    }

    @Override
    public long getItemId(int position) {
        RssFeedItem rssFeedItem = items.get(position);
        return rssFeedItem.getDescription().hashCode();
    }

    @Override
    public void onViewRecycled(FeedViewHolder holder) {
        ImageAsyncTask imageAsyncTask = cacheMap.get(holder.image);
        if (imageAsyncTask != null && !imageAsyncTask.isCancelled()) {
            imageAsyncTask.cancel(true);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
