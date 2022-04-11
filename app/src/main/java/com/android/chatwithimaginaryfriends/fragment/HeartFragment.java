package com.android.chatwithimaginaryfriends.fragment;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.adapter.HeartAdapter;
import com.android.chatwithimaginaryfriends.model.HeartModel;
import java.util.ArrayList;

public class HeartFragment extends ListFragment {
    ArrayList<HeartModel> listHeartModel;
    HeartAdapter heartAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        listHeartModel = new ArrayList<>();
        AddArrayHeart();
        heartAdapter = new HeartAdapter(getActivity(), R.layout.row_heart, listHeartModel);
        setListAdapter(heartAdapter);
        View view = inflater.inflate(R.layout.fragment_heart, container, false);
        return view;
    }

    private void AddArrayHeart() {
        listHeartModel.add(new HeartModel("Bot A", "Con bot A"));
        listHeartModel.add(new HeartModel("Bot B", "Con bot B"));
        listHeartModel.add(new HeartModel("Bot C", "Con bot C"));
        listHeartModel.add(new HeartModel("Bot D", "Con bot D"));
        listHeartModel.add(new HeartModel("Bot E", "Con bot E"));
    }
}
