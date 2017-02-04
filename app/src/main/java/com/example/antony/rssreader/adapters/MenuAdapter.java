package com.example.antony.rssreader.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.antony.rssreader.R;
import com.example.antony.rssreader.viewholders.MenuViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Antony on 2/3/2017.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuViewHolder> {
    private List<String> items = new ArrayList<>();
    private static final int FOOTER_TYPE = 123;
    private static final int USUAL_TYPE = 3432;

    public MenuAdapter() {
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
                        showAddDialog(view.getContext());
                    }
                });
                btn.setText("+");
                break;
            default:
                final String text = items.get(position);
                textView.setText(text);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        removeFeed(text);
                    }
                });
        }

    }

    private void removeFeed(String item) {
        int i = items.indexOf(item);
        items.remove(i);
        notifyItemRemoved(i);
    }

    private void showAddDialog(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("Add an item")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        long l = new Random().nextInt(100);
                        addRssFeedItem(String.valueOf(l));
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    public void addRssFeedItem(String item) {
        items.add(item);
        notifyItemInserted(items.size());
    }

    @Override
    public int getItemCount() {
        return items.size() + 1;
    }
}
