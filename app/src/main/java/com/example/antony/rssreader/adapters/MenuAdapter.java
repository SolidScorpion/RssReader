package com.example.antony.rssreader.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.antony.rssreader.R;
import com.example.antony.rssreader.models.MenuItem;
import com.example.antony.rssreader.viewholders.MenuViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antony on 2/3/2017.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuViewHolder> {
    private List<MenuItem> items = new ArrayList<>();
    private static final int FOOTER_TYPE = 123;
    private static final int USUAL_TYPE = 3432;
    private static final int FOOTER_COUNT = 1;
    private MenuItemClickListener mListener;
    public interface MenuItemClickListener {
        void addMenuItem();
        void onMenuItemClick(MenuItem item);
        boolean removeItem(MenuItem item);
    }
    public MenuAdapter(MenuItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.menu_item, parent, false);
        return new MenuViewHolder(inflate);
    }

    @Override
    public int getItemViewType(int position) {
        if (items.size() == position) {
            return FOOTER_TYPE;
        }
        return USUAL_TYPE;
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, final int position) {
        Button btn = holder.btn;
        TextView textView = holder.textView;
        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case FOOTER_TYPE:
                textView.setText("Add Rss feed");
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.addMenuItem();
                    }
                });
                btn.setText("+");
                break;
            default:
                final MenuItem menuItem = items.get(position);
                textView.setText(menuItem.getServiceName());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onMenuItemClick(menuItem);
                    }
                });
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        removeFeed(menuItem);
                    }
                });
        }

    }

    private void removeFeed(MenuItem item) {
        if (mListener.removeItem(item)) {
            int i = items.indexOf(item);
            items.remove(i);
            notifyItemRemoved(i);
        }
    }

    public void updateData(List<MenuItem> items) {
        for (MenuItem item : items) {
            addMenuItem(item);
        }
    }

    public void addMenuItem(MenuItem item) {
        items.add(item);
        notifyItemInserted(items.size());
    }

    @Override
    public int getItemCount() {
        return items.size() + FOOTER_COUNT;
    }
}
