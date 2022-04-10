package com.android.chatwithimaginaryfriends.fragment;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.adapter.CharacterAdapter;
import com.android.chatwithimaginaryfriends.adapter.ChatAdapter;
import com.android.chatwithimaginaryfriends.model.Character;
import com.android.chatwithimaginaryfriends.model.ChatModel;
import com.android.chatwithimaginaryfriends.view.ChatActivity;

import java.util.ArrayList;

public class ChatFragment extends ListFragment {
    ArrayList<ChatModel> listChat;
    ChatAdapter chatAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        listChat = new ArrayList<>();
        AddArrayChat();
        chatAdapter = new ChatAdapter(getActivity(), R.layout.row_chat, listChat);
        setListAdapter(chatAdapter);

        return view;
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(v.getContext(), "hello", Toast.LENGTH_LONG).show();
    }
    private void AddArrayChat() {
        listChat.add(new ChatModel("Chat A", "hello", false));
        listChat.add(new ChatModel("Chat A", "hahahahaha", false));
    }
}
