package com.android.chatwithimaginaryfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

import java.util.ArrayList;

public class HeartActivity extends AppCompatActivity {
    ListView lvBot;
    ArrayList<Hearts> arrHearts;
    BotAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_heart);
        lvBot = (ListView) findViewById(R.id.listBot);
        arrHearts = new ArrayList<>();
        arrHearts.add(new Hearts("Bot Huy", "No description"));
        arrHearts.add(new Hearts("Bot Lợi", "No description"));
        arrHearts.add(new Hearts("Bot Lợi", "No description"));
        adapter = new BotAdapter(this,R.layout.bot_list_item,arrHearts);
        lvBot.setAdapter(adapter);
    }
}