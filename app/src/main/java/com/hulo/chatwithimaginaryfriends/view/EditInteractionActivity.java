package com.hulo.chatwithimaginaryfriends.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Window;
import android.widget.ImageButton;

import com.hulo.chatwithimaginaryfriends.R;
import com.hulo.chatwithimaginaryfriends.constant.SystemConstant;
import com.hulo.chatwithimaginaryfriends.fragment.ReplyFragment;
import com.hulo.chatwithimaginaryfriends.fragment.WordFragment;
import com.hulo.chatwithimaginaryfriends.model.InteractionModel;

import java.util.Objects;

public class EditInteractionActivity extends AppCompatActivity {
    private FragmentManager fragmentManager = getFragmentManager();
    private ImageButton btnSaveInteraction;
    private WordFragment wordFragment;
    private ReplyFragment replyFragment;
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int appTheme = app_preferences.getInt("theme", 0);
        if (appTheme == 0){
            setTheme(SystemConstant.DEFAULT_THEME);
        }else{
            setTheme(appTheme);
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();

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
            overridePendingTransition(R.anim.anim_enter_from_left, R.anim.anim_exit_out_right);
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