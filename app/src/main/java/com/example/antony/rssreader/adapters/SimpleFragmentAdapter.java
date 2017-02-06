package com.example.antony.rssreader.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.antony.rssreader.models.MenuItem;
import com.example.antony.rssreader.screens.content.ContentFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antony on 2/6/2017.
 */

public class SimpleFragmentAdapter extends FragmentStatePagerAdapter {
    private List<MenuItem> items = new ArrayList<>();

    public SimpleFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addUrl(String url, String name) {
        items.add(new MenuItem(url, name));
        notifyDataSetChanged();
    }

    public void addUrl(MenuItem item) {
        items.add(item);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        String serviceUrl = items.get(position).getServiceUrl();
        return ContentFragment.getInstance(serviceUrl);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return items.get(position).getServiceName();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void updateData(List<MenuItem> data) {
        if (data != null) {
            for (MenuItem menuItem : data) {
                addUrl(menuItem);
            }
            notifyDataSetChanged();
        }
    }

    public void removeItem(MenuItem item) {
        items.remove(item);
        notifyDataSetChanged();
    }
}
