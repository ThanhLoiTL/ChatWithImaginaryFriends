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
import com.android.chatwithimaginaryfriends.model.HeartModel;
import com.android.chatwithimaginaryfriends.view.EditHeartActivity;

import java.util.ArrayList;

public class HeartFragment extends ListFragment {
    private final int CODE_HEART = 123;
    public static ArrayList<HeartModel> listHeartModel;
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

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        HeartModel heart = listHeartModel.get(position);
        Intent intent = new Intent(getActivity(), EditHeartActivity.class);
        intent.putExtra("HeartModel", heart);
        startActivityForResult(intent, CODE_HEART);
    }

    private void AddArrayHeart() {
        listHeartModel.add(new HeartModel(0L, "Heart A", "Description heart A"));
        listHeartModel.add(new HeartModel(1L, "Heart B", "Description heart B"));
        listHeartModel.add(new HeartModel(2L, "Heart C", "Description heart C"));
        listHeartModel.add(new HeartModel(3L, "Heart D", "Description heart D"));
        listHeartModel.add(new HeartModel(4L, "Heart E", "Description heart E"));
    }
}
