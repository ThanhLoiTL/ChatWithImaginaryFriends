package com.android.chatwithimaginaryfriends.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.fragment.ReplyFragment;
import com.android.chatwithimaginaryfriends.fragment.WordFragment;
import com.android.chatwithimaginaryfriends.model.HeartModel;
import com.android.chatwithimaginaryfriends.model.InteractionModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditInteractionActivity extends AppCompatActivity {
    FragmentManager fragmentManager = getFragmentManager();
    ImageButton btnSaveInteraction;
    WordFragment wordFragment;
    ReplyFragment replyFragment;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_edit_interaction);
        init();

        Intent intent = getIntent();
        InteractionModel interactionModel = (InteractionModel) intent.getSerializableExtra("InteractionModel");

        bundle.putSerializable("InteractionModel", interactionModel);
        wordFragment.setArguments(bundle);
        replyFragment.setArguments(bundle);

        addFragment(wordFragment, R.id.frame_layout_trigger_word);
        addFragment(replyFragment, R.id.frame_layout_reply_pattern);

        btnSaveInteraction.setOnClickListener(view -> {
            finish();
        });
    }
    private void init() {
        btnSaveInteraction = findViewById(R.id.btn_save_interaction);
        wordFragment = new WordFragment();
        replyFragment = new ReplyFragment();
        bundle = new Bundle();
    }

    private void addFragment(Fragment fragment, int id) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(id, fragment);
        fragmentTransaction.commit();
    }

}