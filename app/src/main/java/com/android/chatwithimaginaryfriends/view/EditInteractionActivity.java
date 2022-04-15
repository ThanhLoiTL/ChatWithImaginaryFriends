package com.android.chatwithimaginaryfriends.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.fragment.ReplyFragment;
import com.android.chatwithimaginaryfriends.fragment.WordFragment;
import com.android.chatwithimaginaryfriends.model.InteractionModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditInteractionActivity extends AppCompatActivity {
    FragmentManager fragmentManager = getFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_edit_interaction);

        Intent intent = getIntent();
        InteractionModel interactionModel = (InteractionModel) intent.getSerializableExtra("InteractionModel");
        List<String> words = stringToArray(interactionModel.getTriggerWord());
        addFragment(new WordFragment(words), R.id.frame_layout_trigger_word);
        addFragment(new ReplyFragment(stringToArray(interactionModel.getReplyWord())), R.id.frame_layout_reply_pattern);
    }

    private void addFragment(Fragment fragment, int id) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(id, fragment);
        fragmentTransaction.commit();
    }
    private List<String> stringToArray(String triggerWords) {
        List<String> arrString = Arrays.asList(triggerWords.split(","));
        return arrString;
    }
}