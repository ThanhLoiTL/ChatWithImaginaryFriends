package com.android.chatwithimaginaryfriends.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.model.BotModel;
import com.android.chatwithimaginaryfriends.model.HeartModel;
import com.android.chatwithimaginaryfriends.util.ImageUtil;

import java.util.List;

public class BotAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<BotModel> listBotModel;

    public BotAdapter(Context context, int layout, List<BotModel> listBotModel) {
        this.context = context;
        this.layout = layout;
        this.listBotModel = listBotModel;
    }

    @Override
    public int getCount() {
        return listBotModel.size();
    }

    @Override
    public Object getItem(int i) {
        return listBotModel.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    private class ViewHolder {
        TextView txtBotName;
        ImageView imgBot;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            viewHolder.txtBotName = view.findViewById(R.id.txtBotName);
            viewHolder.imgBot = view.findViewById(R.id.imgBot);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        BotModel botModel = listBotModel.get(i);
        viewHolder.txtBotName.setText(botModel.getName());
        viewHolder.imgBot.setImageBitmap(ImageUtil.byteToBitmap(botModel.getAvatar()));
        return view;
    }
}
