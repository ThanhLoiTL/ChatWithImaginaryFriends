package com.android.chatwithimaginaryfriends.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.model.ChatModel;

import java.util.List;

public class BotAPIAdapter extends BaseAdapter {
    private Context context;
    private List<ChatModel> listChat;


    public BotAPIAdapter(Context context, List<ChatModel> listChat) {
        this.context = context;
        this.listChat = listChat;
    }

    @Override
    public int getCount() {
        return listChat.size();
    }

    @Override
    public Object getItem(int i) {
        return listChat.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder {
        TextView message;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(listChat.get(i).isSend()){
                view = inflater.inflate(R.layout.user_msg_rv_item, null);
            }else{
                view = inflater.inflate(R.layout.bot_msg_rv_item, null);
            }
            viewHolder.message = view.findViewById(R.id.message);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        ChatModel chatModel = listChat.get(i);
        viewHolder.message.setText(chatModel.getMessage());
        return view;
    }
}
