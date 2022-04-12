package com.android.chatwithimaginaryfriends.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;

import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.adapter.BotAPIAdapter;
import com.android.chatwithimaginaryfriends.handle.HttpDataHandler;
import com.android.chatwithimaginaryfriends.model.ChatModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    ListView listMessage;
    FloatingActionButton btnSend;
    EditText editMessage;
    List<ChatModel> listChat = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_chat);

        listMessage  = findViewById(R.id.list_message);
        btnSend = findViewById(R.id.btn_send);
        editMessage = findViewById(R.id.edit_message);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editMessage.getText().toString();
                ChatModel chatModel = new ChatModel(text, true);
                listChat.add(chatModel);
                new ChatActivity.BotAPI().execute(listChat);
                editMessage.setText("");
            }
        });
    }
    private class BotAPI extends AsyncTask<List<ChatModel>, Void, String> {
        String stream = null;
        List<ChatModel> models;
        String text = editMessage.getText().toString();
        @Override
        protected String doInBackground(List<ChatModel>... lists) {
            String url = String.format("http://api.brainshop.ai/get?bid=165426&key=8S10YTsIlEFSMJKk&uid=[uid]&msg="+text);
            models = lists[0];
            HttpDataHandler httpDataHandler = new HttpDataHandler();
            stream = httpDataHandler.getHttpData(url);
            return stream;
        }

        @Override
        protected void onPostExecute(String s) {
            Gson gson = new Gson();
            com.android.chatwithimaginaryfriends.model.BotAPI res = gson.fromJson(s, com.android.chatwithimaginaryfriends.model.BotAPI.class);

            ChatModel chatModel = new ChatModel(res.getCnt(),false);
            models.add(chatModel);
            BotAPIAdapter adapter = new BotAPIAdapter(getApplicationContext(), models);
            listMessage.setAdapter(adapter);
        }
    }
}