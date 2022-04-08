package com.android.chatwithimaginaryfriends.fragment;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.adapter.HeartAdapter;
import com.android.chatwithimaginaryfriends.model.Heart;
import java.util.ArrayList;

public class HeartFragment extends ListFragment {
    ArrayList<Heart> listHeart;
    HeartAdapter heartAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        listHeart = new ArrayList<>();
        AddArrayHeart();
        heartAdapter = new HeartAdapter(getActivity(), R.layout.row_heart, listHeart);
        setListAdapter(heartAdapter);
        View view = inflater.inflate(R.layout.fragment_heart, container, false);
        return view;
    }

    private void AddArrayHeart() {
        listHeart.add(new Heart("Bot A", "Con bot A"));
        listHeart.add(new Heart("Bot B", "Con bot B"));
        listHeart.add(new Heart("Bot C", "Con bot C"));
        listHeart.add(new Heart("Bot D", "Con bot D"));
        listHeart.add(new Heart("Bot E", "Con bot E"));
    }
}
