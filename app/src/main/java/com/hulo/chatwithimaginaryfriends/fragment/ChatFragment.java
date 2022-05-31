package com.hulo.chatwithimaginaryfriends.fragment;

import android.app.Dialog;
import android.app.ListFragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.Nullable;

import com.hulo.chatwithimaginaryfriends.R;
import com.hulo.chatwithimaginaryfriends.adapter.CharacterListAdapter;
import com.hulo.chatwithimaginaryfriends.adapter.ChatAdapter;
import com.hulo.chatwithimaginaryfriends.dao.ICharacterDAO;
import com.hulo.chatwithimaginaryfriends.dao.IChatDAO;
import com.hulo.chatwithimaginaryfriends.dao.impl.CharacterDAO;
import com.hulo.chatwithimaginaryfriends.dao.impl.ChatDAO;
import com.hulo.chatwithimaginaryfriends.model.CharacterModel;
import com.hulo.chatwithimaginaryfriends.model.ChatModel;
import com.hulo.chatwithimaginaryfriends.view.ChatActivity;

import java.util.List;

public class ChatFragment extends ListFragment {
    private final int CODE_CHAT = 1103;
    private List<ChatModel> listChat;
    private List<CharacterModel> listCharacter;
    private ChatAdapter chatAdapter;

    private ImageButton btnAddChat;
    private ICharacterDAO characterDAO;
    private IChatDAO chatDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        chatDAO = new ChatDAO();
        listChat = chatDAO.findByChat();
        characterDAO = new CharacterDAO();
        listCharacter = characterDAO.getAll();
        chatAdapter = new ChatAdapter(getActivity(), R.layout.row_chat, listChat);
        setListAdapter(chatAdapter);

        btnAddChat = view.findViewById(R.id.btn_add_chat);
        addChat(listCharacter);

        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        CharacterModel character = characterDAO.findOne(listChat.get(position).getCharterId());
        intent.putExtra("Character", character);
        startActivityForResult(intent, CODE_CHAT);
        getActivity().overridePendingTransition(R.anim.anim_enter_from_right, R.anim.anim_exit_out_left);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CODE_CHAT){
            listChat = chatAdapter.listChat = chatDAO.findByChat();
            chatAdapter.notifyDataSetChanged();
        }
    }

    private void addChat(List<CharacterModel> listCharacter) {
        btnAddChat.setOnClickListener(v -> {
            final Dialog dialog = new Dialog(v.getContext());
            dialog.setContentView(R.layout.dialog_list_character);
            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            ListView listView = dialog.findViewById(R.id.lv_character);
            TextView btnCancel = dialog.findViewById(R.id.btn_cancel_select_character);
            ArrayAdapter arrayAdapter = new CharacterListAdapter(v.getContext(), R.layout.row_select_character, listCharacter);
            listView.setAdapter(arrayAdapter);
            listView.setOnItemClickListener((adapterView, view, which, l) -> {
                Intent intent = new Intent(v.getContext(), ChatActivity.class);
                CharacterModel character = listCharacter.get(which);
                intent.putExtra("Character", character);
                startActivityForResult(intent,CODE_CHAT);
                dialog.dismiss();
            });

            btnCancel.setOnClickListener(view -> {
                dialog.dismiss();
            });

            dialog.show();
        });
    }
}
