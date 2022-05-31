package com.hulo.chatwithimaginaryfriends.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hulo.chatwithimaginaryfriends.R;

import java.util.List;

public class FinalReplyAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    public List<String> listFinalReply;

    public FinalReplyAdapter(Context context, int layout, List<String> listFinalReply) {
        this.context = context;
        this.layout = layout;
        this.listFinalReply = listFinalReply;
    }

    @Override
    public int getCount() {
        return listFinalReply.size();
    }

    @Override
    public Object getItem(int i) {
        return listFinalReply.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    private class ViewHolder {
        TextView finalReply;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            viewHolder.finalReply = view.findViewById(R.id.word);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        String word = listFinalReply.get(i);
        viewHolder.finalReply.setText(word);
        return view;
    }
}
