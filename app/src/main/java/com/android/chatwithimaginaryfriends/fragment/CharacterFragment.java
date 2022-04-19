package com.android.chatwithimaginaryfriends.fragment;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.adapter.CharacterAdapter;
import com.android.chatwithimaginaryfriends.model.CharacterModel;

import java.util.ArrayList;

public class CharacterFragment extends ListFragment {
    ArrayList<CharacterModel> listCharacterModel;
    CharacterAdapter characterAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        listCharacterModel = new ArrayList<>();
        AddArrayCharacter();
        characterAdapter = new CharacterAdapter(getActivity(), R.layout.row_character, listCharacterModel);
        setListAdapter(characterAdapter);
        View view = inflater.inflate(R.layout.fragment_character, container, false);
        return view;
    }

    private void AddArrayCharacter() {
        listCharacterModel.add(new CharacterModel("Em Gai Mua", "Heart A", null));
        listCharacterModel.add(new CharacterModel("Character B", "Heart B", null));
    }
}
