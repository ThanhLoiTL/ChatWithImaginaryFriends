package com.android.chatwithimaginaryfriends.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageButton;

import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.fragment.FinalReplyFragment;
import com.android.chatwithimaginaryfriends.model.HeartModel;

public class EditFinalReplyActivity extends AppCompatActivity {
    private ImageButton btnSaveHeart;
    FragmentManager fragmentManager = getFragmentManager();
    private FinalReplyFragment finalReplyFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
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