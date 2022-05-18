package com.android.chatwithimaginaryfriends.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Window;

import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.constant.SystemConstant;
import com.android.chatwithimaginaryfriends.database.Database;
import com.android.chatwithimaginaryfriends.fragment.BotFragment;
import com.android.chatwithimaginaryfriends.fragment.CharacterFragment;
import com.android.chatwithimaginaryfriends.fragment.ChatFragment;
import com.android.chatwithimaginaryfriends.fragment.HeartFragment;
import com.android.chatwithimaginaryfriends.fragment.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView navigationMenu;
    public static Database database;
    private final FragmentManager fragmentManager = getFragmentManager();

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
        setContentView(R.layout.activity_main);
        mapping();
        database = new Database(this);
        //default nav chat
        addFragment(new ChatFragment());

        navigationMenu.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_heart:
                    addFragment(new HeartFragment());
                    break;
                case R.id.action_character:
                    addFragment(new CharacterFragment());
                    break;
                case R.id.action_bot:
                    addFragment(new BotFragment());
                    break;
                case R.id.action_chat:
                    addFragment(new ChatFragment());
                    break;
                case R.id.action_setting:
                    addFragment(new SettingFragment());
                    break;
            }
            return true;
        });
    }

    private void mapping() {
        navigationMenu = findViewById(R.id.bottom_navigation);
    }

    private void addFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}