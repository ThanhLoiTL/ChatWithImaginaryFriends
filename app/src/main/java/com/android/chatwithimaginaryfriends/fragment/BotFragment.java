package com.android.chatwithimaginaryfriends.fragment;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.adapter.BotAPIAdapter;
import com.android.chatwithimaginaryfriends.adapter.BotAdapter;
import com.android.chatwithimaginaryfriends.adapter.CharacterAdapter;
import com.android.chatwithimaginaryfriends.model.BotModel;
import com.android.chatwithimaginaryfriends.model.CharacterModel;

import java.util.ArrayList;

public class BotFragment extends ListFragment {
    ArrayList<BotModel> listBotModel;
    BotAdapter botAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        listBotModel = new ArrayList<>();
        AddArrayCharacter();
        botAdapter = new BotAdapter(getActivity(), R.layout.row_bot, listBotModel);
        setListAdapter(botAdapter);
        View view = inflater.inflate(R.layout.fragment_bot, container, false);
        return view;
    }

    private void AddArrayCharacter() {
        listBotModel.add(new BotModel("Brain Bot", "Brain Bot"));
        listBotModel.add(new BotModel("Love Bot", "Love Bot"));
    }
}
