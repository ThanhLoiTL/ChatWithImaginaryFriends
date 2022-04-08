package com.android.chatwithimaginaryfriends.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.model.Heart;
import java.util.List;

public class HeartAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Heart> listHeart;

    public HeartAdapter(Context context, int layout, List<Heart> listHeart) {
        this.context = context;
        this.layout = layout;
        this.listHeart = listHeart;
    }

    @Override
    public int getCount() {
        return listHeart.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private class ViewHolder {
        TextView txtBotName, txtBotDesc;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            viewHolder.txtBotName = view.findViewById(R.id.txtBotName);
            viewHolder.txtBotDesc = view.findViewById(R.id.txtBotDesc);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Heart heart = listHeart.get(i);
        viewHolder.txtBotName.setText(heart.getBotName());
        viewHolder.txtBotDesc.setText(heart.getDescription());

        return view;
    }
}
