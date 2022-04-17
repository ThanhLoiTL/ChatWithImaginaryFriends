package com.android.chatwithimaginaryfriends.view;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.fragment.InteractionFragment;
import com.android.chatwithimaginaryfriends.model.HeartModel;

public class InteractionActivity extends AppCompatActivity {
    FragmentManager fragmentManager = getFragmentManager();
    TextView finalReply;
    ImageButton btnSaveHeart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_interaction);
        mapping();
        InteractionFragment interactionFragment = new InteractionFragment();

        Intent intent = getIntent();
        HeartModel heartModel = (HeartModel) intent.getSerializableExtra("HeartModel");
        Bundle bundle = new Bundle();
        bundle.putSerializable("HeartModel", heartModel);
        interactionFragment.setArguments(bundle);
        addFragment(interactionFragment);

        btnSaveHeart.setOnClickListener(view -> {
            finish();
        });

        finalReply.setOnClickListener(view -> {
            Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
        });

    }

    private void mapping() {
        finalReply = findViewById(R.id.final_reply);
        btnSaveHeart = findViewById(R.id.btn_save_heart);
    }
    private void addFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutInteraction, fragment);
        fragmentTransaction.commit();
    }
}