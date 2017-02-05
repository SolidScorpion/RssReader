package com.example.antony.rssreader.utilities;

import android.support.v7.util.DiffUtil;

import com.example.antony.rssreader.models.RssFeedItem;

import java.util.List;

/**
 * Created by Pripachkin on 05.02.2017.
 */

public class RssFeedItemDiffCallback extends DiffUtil.Callback {
    private List<RssFeedItem> oldList;
    private List<RssFeedItem> newList;

    public RssFeedItemDiffCallback(List<RssFeedItem> oldList, List<RssFeedItem> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getDate().equals(newList.get(newItemPosition).getDate());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        RssFeedItem rssFeedItem = oldList.get(oldItemPosition);
        RssFeedItem newItem = newList.get(newItemPosition);
        return rssFeedItem.getTitle() == newItem.getTitle() && rssFeedItem.getImgUrl() == newItem.getImgUrl();
    }
}
