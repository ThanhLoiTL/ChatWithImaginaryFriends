package com.android.chatwithimaginaryfriends.view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.fragment.InteractionFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InteractionActivity extends AppCompatActivity {
    FragmentManager fragmentManager = getFragmentManager();
    TextView finalReply;
    ImageButton btnAddInteraction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_interaction);
        mapping();

        addFragment(new InteractionFragment());

        finalReply.setOnClickListener(view -> {
            Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
        });
        btnAddInteraction.setOnClickListener(view -> {

        });
    }

    private void mapping() {
        finalReply = findViewById(R.id.final_reply);
        btnAddInteraction = findViewById(R.id.btn_add_interaction);
    }
    private void addFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutInteraction, fragment);
        fragmentTransaction.commit();
    }
}