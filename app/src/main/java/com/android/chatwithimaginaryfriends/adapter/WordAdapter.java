package com.android.chatwithimaginaryfriends.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.chatwithimaginaryfriends.R;
import java.util.List;


public class WordAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    public List<String> listWord;

    public WordAdapter(Context context, int layout, List<String> listWord) {
        this.context = context;
        this.layout = layout;
        this.listWord = listWord;
    }

    @Override
    public int getCount() {
        return listWord.size();
    }

    @Override
    public Object getItem(int i) {
        return listWord.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    private class ViewHolder {
        TextView triggerWord;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            viewHolder.triggerWord = view.findViewById(R.id.word);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        String word = listWord.get(i);
        viewHolder.triggerWord.setText(word);
        return view;
    }
}
