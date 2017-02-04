package com.example.antony.rssreader.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.antony.rssreader.R;

/**
 * Created by Antony on 2/3/2017.
 */

public class MenuViewHolder extends RecyclerView.ViewHolder{
    public TextView textView;
    public Button btn;
    public MenuViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.feedNameTv);
        btn = (Button) itemView.findViewById(R.id.feedBtn);
    }
}
