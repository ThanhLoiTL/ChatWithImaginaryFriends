package com.hulo.chatwithimaginaryfriends.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hulo.chatwithimaginaryfriends.R;
import com.hulo.chatwithimaginaryfriends.constant.SystemConstant;
import com.hulo.chatwithimaginaryfriends.dao.IHeartDAO;
import com.hulo.chatwithimaginaryfriends.dao.impl.HeartDAO;
import com.hulo.chatwithimaginaryfriends.model.HeartModel;

import java.util.Objects;


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
        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int appTheme = app_preferences.getInt("theme", 0);
        if (appTheme == 0){
            setTheme(SystemConstant.DEFAULT_THEME);
        }else{
            setTheme(appTheme);
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(R.layout.activity_edit_heart);
        mapping();
        heartDAO = new HeartDAO();
        Intent intent = getIntent();
        heartModel = (HeartModel) intent.getSerializableExtra("HeartModel");
        heartModel = heartDAO.findOne(heartModel.getId());
        heartName.setText(heartModel.getHeartName());
        heartDescription.setText(heartModel.getDescription());

        saveHeart.setOnClickListener(view -> {
            if(heartName.getText().toString().trim().equals("") || heartDescription.getText().toString().trim().equals("")) {
                Toast.makeText(this, "Please Fill All Properties", Toast.LENGTH_LONG).show();
            }else {
                heartModel.setHeartName(heartName.getText().toString().trim());
                heartModel.setDescription(heartDescription.getText().toString().trim());
                int isSuccess = heartDAO.updateHeart(heartModel);
                if (isSuccess == 1) {
                    //Toast.makeText(this, "Update success", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
                }
                finish();
                overridePendingTransition(R.anim.anim_enter_from_left, R.anim.anim_exit_out_right);
            }
        });

        createEditInteraction.setOnClickListener(view -> {
            Intent intentInteraction = new Intent(EditHeartActivity.this, InteractionActivity.class);
            intentInteraction.putExtra("HeartModel", heartModel);
            startActivityForResult(intentInteraction, CODE_INTERACTION);
            overridePendingTransition(R.anim.anim_enter_from_right, R.anim.anim_exit_out_left);
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