package com.android.chatwithimaginaryfriends;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class BotAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Hearts> heartsList;

    public BotAdapter(Context context, int layout, List<Hearts> heartsList) {
        this.context = context;
        this.layout = layout;
        this.heartsList = heartsList;
    }

    @Override
    public int getCount() {
        return heartsList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);
        TextView txtBotName = (TextView) view.findViewById(R.id.txtBotName);
        TextView txtBotDesc = (TextView) view.findViewById(R.id.txtBotDesc);
        Hearts hearts = heartsList.get(i);
        txtBotName.setText(hearts.getBotName());
        txtBotDesc.setText(hearts.getDescription());
        return view;
    }
}
