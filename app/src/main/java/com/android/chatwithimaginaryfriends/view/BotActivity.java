package com.android.chatwithimaginaryfriends.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.constant.SystemConstant;
import com.android.chatwithimaginaryfriends.model.BotModel;
import com.android.chatwithimaginaryfriends.util.ImageUtil;

public class BotActivity extends AppCompatActivity {
    private BotModel botModel;
    private ImageView imgAvatarBot;
    private EditText botName, botDescription;
    private ImageButton btnBackBot;

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
        getSupportActionBar().hide();
        setContentView(R.layout.activity_bot);

        init();

        Intent intent = getIntent();
        botModel = (BotModel) intent.getSerializableExtra("BotModel");

        setView(botModel);

        btnBackBot.setOnClickListener(view -> {
            finish();
            overridePendingTransition(R.anim.anim_enter_from_left, R.anim.anim_exit_out_right);
        });
    }

    private void setView(BotModel bot) {
        if(bot != null){
            botName.setText(bot.getName());
            botDescription.setText(bot.getDescription());
            imgAvatarBot.setImageBitmap(ImageUtil.byteToBitmap(bot.getAvatar()));
        }
    }

    private void init() {
        imgAvatarBot = findViewById(R.id.img_avatar_bot);
        botName = findViewById(R.id.bot_name);
        botDescription = findViewById(R.id.bot_description);
        btnBackBot = findViewById(R.id.btn_back_bot);
    }
}