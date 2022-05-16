package com.android.chatwithimaginaryfriends.fragment;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.adapter.BotAdapter;
import com.android.chatwithimaginaryfriends.dao.IBotDAO;
import com.android.chatwithimaginaryfriends.dao.impl.BotDAO;
import com.android.chatwithimaginaryfriends.model.BotModel;

import java.util.List;

public class BotFragment extends ListFragment {
    List<BotModel> listBotModel;
    private BotAdapter botAdapter;

    private IBotDAO botDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        botDAO = new BotDAO();
        listBotModel = botDAO.getAll();
        botAdapter = new BotAdapter(getActivity(), R.layout.row_bot, listBotModel);
        setListAdapter(botAdapter);
        View view = inflater.inflate(R.layout.fragment_bot, container, false);
        return view;
    }
}
