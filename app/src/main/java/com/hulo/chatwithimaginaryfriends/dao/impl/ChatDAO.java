package com.hulo.chatwithimaginaryfriends.dao.impl;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hulo.chatwithimaginaryfriends.constant.SystemConstant;
import com.hulo.chatwithimaginaryfriends.dao.IChatDAO;
import com.hulo.chatwithimaginaryfriends.database.Database;
import com.hulo.chatwithimaginaryfriends.model.ChatModel;
import com.hulo.chatwithimaginaryfriends.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class ChatDAO implements IChatDAO {
    Database database = MainActivity.database;
    @Override
    public long addChat(ChatModel chat) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SystemConstant.COLUMN_CHAT_CHARACTER_ID, chat.getCharterId());
        values.put(SystemConstant.COLUMN_CHAT_MESSAGE, chat.getMessage());
        values.put(SystemConstant.COLUMN_CHAT_TIME, chat.getTime());
        values.put(SystemConstant.COLUMN_CHAT_IS_SEND, chat.getIsSend().toString());

        long id = db.insert(SystemConstant.TABLE_CHAT, null, values);
        return id;
    }

    @Override
    public List<ChatModel> getAll() {
        return null;
    }

    @Override
    public void deleteChat(long id) {
        SQLiteDatabase db = database.getWritableDatabase();
        db.delete(SystemConstant.TABLE_CHAT, SystemConstant.COLUMN_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    @Override
    public void deleteChatByCharacter(long characterId) {
        SQLiteDatabase db = database.getWritableDatabase();
        db.delete(SystemConstant.TABLE_CHAT, SystemConstant.COLUMN_CHAT_CHARACTER_ID + " = ?",
                new String[] { String.valueOf(characterId) });
    }

    @Override
    public int updateChat(ChatModel chat) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SystemConstant.COLUMN_CHAT_CHARACTER_ID, chat.getCharterId());
        values.put(SystemConstant.COLUMN_CHAT_MESSAGE, chat.getMessage());
        values.put(SystemConstant.COLUMN_CHAT_TIME, chat.getTime());
        values.put(SystemConstant.COLUMN_CHAT_IS_SEND, chat.getIsSend().toString());
        return db.update(SystemConstant.TABLE_CHAT, values, SystemConstant.COLUMN_ID + " = ?",
                new String[] { String.valueOf(chat.getId()) });
    }

    @Override
    public ChatModel findOne(long id) {
        return null;
    }

    @SuppressLint("Range")
    @Override
    public List<ChatModel> findByCharacter(long characterId) {
        List<ChatModel> listChat = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + SystemConstant.TABLE_CHAT + " WHERE "
                + SystemConstant.COLUMN_CHAT_CHARACTER_ID + " = " + characterId;

        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                ChatModel chat = new ChatModel();
                chat.setId(cursor.getLong(cursor.getColumnIndex(SystemConstant.COLUMN_ID)));
                chat.setCharterId(cursor.getLong(cursor.getColumnIndex(SystemConstant.COLUMN_CHAT_CHARACTER_ID)));
                chat.setMessage(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_CHAT_MESSAGE)));
                chat.setTime(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_CHAT_TIME)));
                chat.setIsSend(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_CHAT_IS_SEND))));
                listChat.add(chat);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return listChat;
    }

    @SuppressLint("Range")
    @Override
    public List<ChatModel> findByChat() {
        List<ChatModel> listChat = new ArrayList<>();
//        String selectQuery = "SELECT  * FROM " + SystemConstant.TABLE_CHAT + " as m1" + " WHERE m1."
//                + SystemConstant.COLUMN_ID + " = (SELECT MAX(+""+))";
        String selectQuery = "SELECT * FROM chats AS c1 WHERE c1.id = (SELECT MAX(id) FROM chats as c2 where c1.character_id = c2.character_id) ORDER BY c1.id DESC";
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                ChatModel chat = new ChatModel();
                chat.setId(cursor.getLong(cursor.getColumnIndex(SystemConstant.COLUMN_ID)));
                chat.setCharterId(cursor.getLong(cursor.getColumnIndex(SystemConstant.COLUMN_CHAT_CHARACTER_ID)));
                chat.setMessage(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_CHAT_MESSAGE)));
                chat.setTime(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_CHAT_TIME)));
                chat.setIsSend(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_CHAT_IS_SEND))));
                listChat.add(chat);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return listChat;
    }
}
