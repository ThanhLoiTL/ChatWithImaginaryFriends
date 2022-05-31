package com.hulo.chatwithimaginaryfriends.fragment;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import com.hulo.chatwithimaginaryfriends.R;
import com.hulo.chatwithimaginaryfriends.adapter.BotAdapter;
import com.hulo.chatwithimaginaryfriends.dao.IBotDAO;
import com.hulo.chatwithimaginaryfriends.dao.impl.BotDAO;
import com.hulo.chatwithimaginaryfriends.model.BotModel;
import com.hulo.chatwithimaginaryfriends.view.BotActivity;

import java.util.List;

public class BotFragment extends ListFragment {
    private static final int CODE_BOT = 1890;
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
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        try{
            Intent intent = new Intent(v.getContext(), BotActivity.class);
            BotModel bot = listBotModel.get(position);
            intent.putExtra("BotModel", bot);
            startActivityForResult(intent, CODE_BOT);
            getActivity().overridePendingTransition(R.anim.anim_enter_from_right, R.anim.anim_exit_out_left);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
