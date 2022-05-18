package com.android.chatwithimaginaryfriends.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.adapter.BotAPIAdapter;
import com.android.chatwithimaginaryfriends.constant.SystemConstant;
import com.android.chatwithimaginaryfriends.dao.IBotDAO;
import com.android.chatwithimaginaryfriends.dao.ICharacterDAO;
import com.android.chatwithimaginaryfriends.dao.IChatDAO;
import com.android.chatwithimaginaryfriends.dao.IHeartDAO;
import com.android.chatwithimaginaryfriends.dao.IInteractionDAO;
import com.android.chatwithimaginaryfriends.dao.impl.BotDAO;
import com.android.chatwithimaginaryfriends.dao.impl.CharacterDAO;
import com.android.chatwithimaginaryfriends.dao.impl.ChatDAO;
import com.android.chatwithimaginaryfriends.dao.impl.HeartDAO;
import com.android.chatwithimaginaryfriends.dao.impl.InteractionDAO;
import com.android.chatwithimaginaryfriends.handle.HttpDataHandler;
import com.android.chatwithimaginaryfriends.model.BotModel;
import com.android.chatwithimaginaryfriends.model.CharacterModel;
import com.android.chatwithimaginaryfriends.model.ChatModel;
import com.android.chatwithimaginaryfriends.model.HeartModel;
import com.android.chatwithimaginaryfriends.model.InteractionModel;
import com.android.chatwithimaginaryfriends.util.ConvertUtil;
import com.android.chatwithimaginaryfriends.util.ImageUtil;
import com.android.chatwithimaginaryfriends.util.ResponseChat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ChatActivity extends AppCompatActivity {

    private static ListView listMessage;
    private FloatingActionButton btnSend;
    private EditText editMessage;
    private ImageView avatarCharacterChat;
    private ImageButton btnBackChat;
    private TextView nameOfCharacter;
    public static List<ChatModel> listChat;

    private static IChatDAO chatDAO;
    private ICharacterDAO characterDAO;
    private IHeartDAO heartDAO;
    private IBotDAO botDAO;
    private IInteractionDAO interactionDAO;
    private static BotAPIAdapter adapter;
    private static CharacterModel character;

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
        setContentView(R.layout.activity_chat);

        init();

        Intent intent = getIntent();
        character = (CharacterModel) intent.getSerializableExtra("Character");
        listChat = chatDAO.findByCharacter(character.getId());
        setView(character);
        adapter = new BotAPIAdapter(ChatActivity.this, listChat);
        listMessage.setAdapter(adapter);
        btnSend.setOnClickListener(view -> {
            String text = editMessage.getText().toString();
            if(!text.isEmpty()){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                String time = simpleDateFormat.format(new Date());

                ChatModel chatModel = new ChatModel(character.getId(), text, time, true);
                chatDAO.addChat(chatModel);
                listChat.add(chatModel);
                if(character.getHeart() != 0) {
                    new HeartAPI().execute(listChat);
                }else if(character.getBot() != 0) {
                    new BotAPI().execute(listChat);
                }
                editMessage.setText("");
            }
        });

        btnBackChat.setOnClickListener(view -> {
            finish();
        });
    }
    private class HeartAPI extends AsyncTask<List<ChatModel>, Void, String> {
        String reply = "";
        String text = editMessage.getText().toString().trim();
        @Override
        protected String doInBackground(List<ChatModel>... lists) {
            HeartModel heart = heartDAO.findOne(character.getHeart());
            List<InteractionModel> interactionList = interactionDAO.findByHeart(heart.getId());
            reply = ResponseChat.response(interactionList, text);
            if(reply.equals("")){
                List<String> listFinalReply = ConvertUtil.stringToArray(heart.getFinalReply());
                if(listFinalReply.size() > 0) {
                    reply = listFinalReply.get(new Random().nextInt(listFinalReply.size()));
                }
                if(reply.equals("")){
                    reply = "No set reply";
                }
            }

            return reply;
        }

        @Override
        protected void onPostExecute(String s) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            String time = simpleDateFormat.format(new Date());
            long id = listChat.get(0).getCharterId();
            ChatModel chatModel = new ChatModel(id, s, time, false);
            listChat.add(chatModel);
            chatDAO.addChat(chatModel);
            setAdapterChat();
        }
    }

    private class BotAPI extends AsyncTask<List<ChatModel>, Void, String> {
        String reply = "No reply";
        String text = editMessage.getText().toString().trim();
        @Override
        protected String doInBackground(List<ChatModel>... lists) {
            BotModel bot = botDAO.findOne(character.getBot());
            String url = String.format(bot.getUrl()+text);
            HttpDataHandler httpDataHandler = new HttpDataHandler();
            reply = httpDataHandler.getHttpData(url);
            return reply;
        }

        @Override
        protected void onPostExecute(String s) {
            String response = "No reply";
            JSONObject jsonResponse = null;
            try {
                jsonResponse = new JSONObject(s);
                response = jsonResponse.getString("cnt");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            String time = simpleDateFormat.format(new Date());
            long id = listChat.get(0).getCharterId();
            ChatModel chatModel = new ChatModel(id, response, time, false);
            listChat.add(chatModel);
            chatDAO.addChat(chatModel);
            setAdapterChat();
        }
    }

    public static void setAdapterChat(){
        adapter.listChat = listChat = chatDAO.findByCharacter(character.getId());
        listMessage.setAdapter(adapter);
    }

    private void setView(CharacterModel character) {
        nameOfCharacter.setText(character.getName());
        avatarCharacterChat.setImageBitmap(ImageUtil.byteToBitmap(character.getAvatar()));
    }

    private void init() {
        listMessage  = findViewById(R.id.list_message);
        btnSend = findViewById(R.id.btn_send);
        editMessage = findViewById(R.id.edit_message);
        avatarCharacterChat = findViewById(R.id.avatar_character_chat);
        btnBackChat = findViewById(R.id.btn_back_chat);
        nameOfCharacter = findViewById(R.id.name_of_character);
        chatDAO = new ChatDAO();
        characterDAO = new CharacterDAO();
        heartDAO = new HeartDAO();
        interactionDAO = new InteractionDAO();
        botDAO = new BotDAO();
        listChat = new ArrayList<>();
    }
}