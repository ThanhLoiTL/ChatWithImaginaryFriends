package com.android.chatwithimaginaryfriends.fragment;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.adapter.CharacterAdapter;
import com.android.chatwithimaginaryfriends.adapter.HeartAdapter;
import com.android.chatwithimaginaryfriends.model.Character;
import com.android.chatwithimaginaryfriends.model.Heart;

import java.util.ArrayList;

public class CharacterFragment extends ListFragment {
    ArrayList<Character> listCharacter;
    CharacterAdapter characterAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        listCharacter = new ArrayList<>();
        AddArrayCharacter();
        characterAdapter = new CharacterAdapter(getActivity(), R.layout.row_character, listCharacter);
        setListAdapter(characterAdapter);
        View view = inflater.inflate(R.layout.fragment_character, container, false);
        return view;
    }

    private void AddArrayCharacter() {
        listCharacter.add(new Character("Character A", "Heart A", null));
        listCharacter.add(new Character("Character B", "Heart B", null));
        listCharacter.add(new Character("Character C", "Heart C", null));
        listCharacter.add(new Character("Character D", "Heart D", null));
        listCharacter.add(new Character("Character E", "Heart E", null));
    }
}
