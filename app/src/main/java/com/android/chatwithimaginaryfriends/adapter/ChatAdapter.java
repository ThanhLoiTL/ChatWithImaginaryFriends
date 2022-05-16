package com.android.chatwithimaginaryfriends.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.dao.ICharacterDAO;
import com.android.chatwithimaginaryfriends.dao.IChatDAO;
import com.android.chatwithimaginaryfriends.dao.impl.CharacterDAO;
import com.android.chatwithimaginaryfriends.model.CharacterModel;
import com.android.chatwithimaginaryfriends.model.ChatModel;
import com.android.chatwithimaginaryfriends.util.ImageUtil;

import java.util.List;

public class ChatAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    public List<ChatModel> listChat;

    private ICharacterDAO characterDAO;

    public ChatAdapter(Context context, int layout, List<ChatModel> listChat) {
        this.context = context;
        this.layout = layout;
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
        TextView txtCharacterName, txtMessage;
        ImageView imgAvatar;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        characterDAO = new CharacterDAO();
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            viewHolder.txtCharacterName = view.findViewById(R.id.txtCharacterName);
            viewHolder.txtMessage = view.findViewById(R.id.latest_message);
            viewHolder.imgAvatar = view.findViewById(R.id.imgAvatar);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ChatModel chatModel = listChat.get(i);
        CharacterModel character = characterDAO.findOne(chatModel.getCharterId());
        viewHolder.txtCharacterName.setText(character.getName());
        viewHolder.txtMessage.setText(chatModel.getMessage());
        viewHolder.imgAvatar.setImageBitmap(ImageUtil.byteToBitmap(character.getAvatar()));

        return view;
    }
}
