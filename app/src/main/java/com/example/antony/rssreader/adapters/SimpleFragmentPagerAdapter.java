package com.example.antony.rssreader.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.antony.rssreader.models.MenuItem;
import com.example.antony.rssreader.screens.content.ContentFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antony on 2/6/2017.
 */

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<MenuItem> items = new ArrayList<>();

    public SimpleFragmentPagerAdapter(FragmentManager fm) {
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
}
