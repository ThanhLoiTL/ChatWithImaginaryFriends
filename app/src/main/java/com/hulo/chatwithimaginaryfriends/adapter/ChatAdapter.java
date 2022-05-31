package com.hulo.chatwithimaginaryfriends.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hulo.chatwithimaginaryfriends.R;
import com.hulo.chatwithimaginaryfriends.dao.ICharacterDAO;
import com.hulo.chatwithimaginaryfriends.dao.IChatDAO;
import com.hulo.chatwithimaginaryfriends.dao.impl.CharacterDAO;
import com.hulo.chatwithimaginaryfriends.dao.impl.ChatDAO;
import com.hulo.chatwithimaginaryfriends.model.CharacterModel;
import com.hulo.chatwithimaginaryfriends.model.ChatModel;
import com.hulo.chatwithimaginaryfriends.util.ImageUtil;

import java.util.List;

public class ChatAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    public List<ChatModel> listChat;

    private ICharacterDAO characterDAO;
    private IChatDAO chatDAO;

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
        ImageButton btnChatDelete;
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
            viewHolder.btnChatDelete = view.findViewById(R.id.chat_delete);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ChatModel chatModel = listChat.get(i);
        CharacterModel character = characterDAO.findOne(chatModel.getCharterId());
        viewHolder.txtCharacterName.setText(character.getName());
        viewHolder.txtMessage.setText(chatModel.getMessage());
        viewHolder.imgAvatar.setImageBitmap(ImageUtil.byteToBitmap(character.getAvatar()));

        viewHolder.btnChatDelete.setOnClickListener(v -> {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle("Notification!");
            alertDialog.setIcon(null);
            alertDialog.setMessage("Do you want to delete chat with " + character.getName() + " ?");
            alertDialog.setPositiveButton("Yes", (dialogInterface, i1) -> {
                chatDAO = new ChatDAO();
                chatDAO.deleteChatByCharacter(character.getId());
                listChat = chatDAO.findByChat();
                this.notifyDataSetChanged();
            });
            alertDialog.setNegativeButton("No", (dialogInterface, i2) -> {

            });
            alertDialog.show();
        });
        return view;
    }
}
