package com.android.chatwithimaginaryfriends.view;

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

import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.constant.SystemConstant;
import com.android.chatwithimaginaryfriends.fragment.FinalReplyFragment;
import com.android.chatwithimaginaryfriends.model.HeartModel;

import java.util.Objects;

public class EditFinalReplyActivity extends AppCompatActivity {
    private ImageButton btnSaveHeart;
    FragmentManager fragmentManager = getFragmentManager();
    private FinalReplyFragment finalReplyFragment;
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

        setContentView(R.layout.activity_edit_final_reply);
        mapping();

        Intent intent = getIntent();
        HeartModel heart = (HeartModel) intent.getSerializableExtra("Heart");
        Bundle bundle = new Bundle();
        bundle.putSerializable("Heart", heart);
        finalReplyFragment.setArguments(bundle);
        addFragment(finalReplyFragment);

        btnSaveHeart.setOnClickListener(view -> {
            finish();
            overridePendingTransition(R.anim.anim_enter_from_left, R.anim.anim_exit_out_right);
        });
    }

    private void mapping() {
        btnSaveHeart = findViewById(R.id.btn_save_heart);
        finalReplyFragment = new FinalReplyFragment();
    }
    private void addFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_final_reply, fragment);
        fragmentTransaction.commit();
    }
}