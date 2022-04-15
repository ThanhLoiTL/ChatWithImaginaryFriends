package com.android.chatwithimaginaryfriends.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.fragment.HeartFragment;
import com.android.chatwithimaginaryfriends.model.HeartModel;

import java.util.ArrayList;

public class EditHeartActivity extends AppCompatActivity {
    private static int CODE_INTERACTION = 111;
    EditText heartName, heartDescription;
    Button createEditInteraction;
    ImageButton saveHeart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_edit_heart);
        mapping();

        Intent intent = getIntent();
        HeartModel heartModel = (HeartModel) intent.getSerializableExtra("HeartModel");
        heartName.setText(heartModel.getHeartName());
        heartDescription.setText(heartModel.getDescription());

        saveHeart.setOnClickListener(view -> {
            String name, description;
            name = heartName.getText().toString().trim();
            description = heartDescription.getText().toString().trim();
            ArrayList<HeartModel> listHeart = HeartFragment.listHeartModel;
            for (HeartModel heart: listHeart) {
                if(heart.getId() == heartModel.getId()){
                    heart.setHeartName(name);
                    heart.setDescription(description);
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        createEditInteraction.setOnClickListener(view -> {
            Intent intentInteraction = new Intent(this, InteractionActivity.class);
            startActivityForResult(intentInteraction, CODE_INTERACTION);
        });
    }
    private void mapping() {
        heartName = findViewById(R.id.heart_name);
        heartDescription = findViewById(R.id.heart_description);
        createEditInteraction = findViewById(R.id.create_edit_interaction);
        saveHeart = findViewById(R.id.btn_save_heart);
    }

}