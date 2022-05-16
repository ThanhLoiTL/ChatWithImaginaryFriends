package com.android.chatwithimaginaryfriends.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.dao.IHeartDAO;
import com.android.chatwithimaginaryfriends.dao.impl.HeartDAO;
import com.android.chatwithimaginaryfriends.model.HeartModel;


public class EditHeartActivity extends AppCompatActivity {
    private static int CODE_INTERACTION = 1119;
    EditText heartName, heartDescription;
    Button createEditInteraction;
    ImageButton saveHeart;
    HeartModel heartModel;
    IHeartDAO heartDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_edit_heart);
        mapping();
        heartDAO = new HeartDAO();
        Intent intent = getIntent();
        heartModel = (HeartModel) intent.getSerializableExtra("HeartModel");
        //heartModel = heartDAO.findOne(heartModel.getId());
        heartName.setText(heartModel.getHeartName());
        heartDescription.setText(heartModel.getDescription());

        saveHeart.setOnClickListener(view -> {
            heartModel.setHeartName(heartName.getText().toString().trim());
            heartModel.setDescription(heartDescription.getText().toString().trim());
            int isSuccess = heartDAO.updateHeart(heartModel);
            if(isSuccess == 1){
                Toast.makeText(this, "Update success", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
            }
            finish();
        });

        createEditInteraction.setOnClickListener(view -> {
            Intent intentInteraction = new Intent(EditHeartActivity.this, InteractionActivity.class);
            intentInteraction.putExtra("HeartModel", heartModel);
            startActivityForResult(intentInteraction, CODE_INTERACTION);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CODE_INTERACTION) {
            heartModel = heartDAO.findOne(heartModel.getId());
        }
    }

    private void mapping() {
        heartName = findViewById(R.id.heart_name);
        heartDescription = findViewById(R.id.heart_description);
        createEditInteraction = findViewById(R.id.create_edit_interaction);
        saveHeart = findViewById(R.id.btn_save_heart);
    }

}