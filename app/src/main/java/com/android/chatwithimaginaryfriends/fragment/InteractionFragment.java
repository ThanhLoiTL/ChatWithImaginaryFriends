package com.android.chatwithimaginaryfriends.fragment;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.adapter.HeartAdapter;
import com.android.chatwithimaginaryfriends.adapter.InteractionAdapter;
import com.android.chatwithimaginaryfriends.model.HeartModel;
import com.android.chatwithimaginaryfriends.model.InteractionModel;
import com.android.chatwithimaginaryfriends.view.EditHeartActivity;
import com.android.chatwithimaginaryfriends.view.EditInteractionActivity;

import java.util.ArrayList;

public class InteractionFragment extends ListFragment {
    private static int CODE_INTERACTION = 222;
    public static ArrayList<InteractionModel> listInteractionModel;
    InteractionAdapter interactionAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        listInteractionModel = new ArrayList<>();
        AddArrayInteraction();
        interactionAdapter = new InteractionAdapter(getActivity(), R.layout.row_interaction, listInteractionModel);
        setListAdapter(interactionAdapter);
        View view = inflater.inflate(R.layout.fragment_interaction, container, false);
        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        InteractionModel interaction = listInteractionModel.get(position);
        Intent intent = new Intent(getActivity(), EditInteractionActivity.class);
        intent.putExtra("InteractionModel", interaction);
        startActivityForResult(intent, CODE_INTERACTION);
    }

    private void AddArrayInteraction() {
        listInteractionModel.add(new InteractionModel(0L, 0L, "Hello, Hi, Xin chao", "Hiii", "Beautiful girl"));
        listInteractionModel.add(new InteractionModel(1L, 1L, "Good morning", "Good", "Beautiful weather"));
    }
}
