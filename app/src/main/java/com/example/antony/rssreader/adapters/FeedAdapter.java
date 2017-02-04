package com.example.antony.rssreader.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.antony.rssreader.R;
import com.example.antony.rssreader.models.RssFeedItem;
import com.example.antony.rssreader.utilities.MyHtmlImageGetter;
import com.example.antony.rssreader.viewholders.FeedViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antony on 2/3/2017.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder> {
    private List<RssFeedItem> items = new ArrayList<>();
    MyHtmlImageGetter htmlImageGetter = new MyHtmlImageGetter();
    public void updateData(List<RssFeedItem> newData) {
        if (newData != null) {
            items.clear();
            items.addAll(newData);
        }
        notifyDataSetChanged();
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.feed_item, parent, false);
        return new FeedViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position) {
        RssFeedItem rssFeedItem = items.get(position);
        Spanned spanned = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            spanned = Html.fromHtml(rssFeedItem.getDescription(), Html.FROM_HTML_MODE_COMPACT, htmlImageGetter, null);
        } else {
            spanned = Html.fromHtml(rssFeedItem.getDescription(), htmlImageGetter, null);
        }
        holder.textView.setText(spanned);
        holder.feedTitle.setText(rssFeedItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
