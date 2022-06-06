package com.hulo.chatwithimaginaryfriends.dao.impl;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hulo.chatwithimaginaryfriends.constant.SystemConstant;
import com.hulo.chatwithimaginaryfriends.dao.ICharacterDAO;
import com.hulo.chatwithimaginaryfriends.dao.IChatDAO;
import com.hulo.chatwithimaginaryfriends.database.Database;
import com.hulo.chatwithimaginaryfriends.model.CharacterModel;
import com.hulo.chatwithimaginaryfriends.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class CharacterDAO implements ICharacterDAO {
    private final Database database = MainActivity.database;
    private IChatDAO chatDAO;

    @Override
    public long addCharacter(CharacterModel character) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SystemConstant.COLUMN_CHAR_NAME, character.getName());
        values.put(SystemConstant.COLUMN_CHAR_AVATAR, character.getAvatar());
        values.put(SystemConstant.COLUMN_CHAR_HEART, character.getHeart());
        values.put(SystemConstant.COLUMN_CHAR_BOT, character.getBot());
        values.put(SystemConstant.COLUMN_CHAR_SHORT_DESCRIPTION, character.getShortDescription());
        values.put(SystemConstant.COLUMN_CHAR_GENDER, character.getGender());
        values.put(SystemConstant.COLUMN_CHAR_BIRTHDAY, character.getBirthday());
        values.put(SystemConstant.COLUMN_CHAR_HEIGHT, character.getHeight());
        values.put(SystemConstant.COLUMN_CHAR_WEIGHT, character.getWeight());
        values.put(SystemConstant.COLUMN_CHAR_ADDRESS, character.getAddress());
        values.put(SystemConstant.COLUMN_CHAR_ZODIAC, character.getZodiac());

        long id = db.insert(SystemConstant.TABLE_CHARACTER, null, values);
        return id;
    }

    @Override
    public int updateCharacter(CharacterModel character) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SystemConstant.COLUMN_CHAR_NAME, character.getName());
        values.put(SystemConstant.COLUMN_CHAR_AVATAR, character.getAvatar());
        values.put(SystemConstant.COLUMN_CHAR_HEART, character.getHeart());
        values.put(SystemConstant.COLUMN_CHAR_BOT, character.getBot());
        values.put(SystemConstant.COLUMN_CHAR_SHORT_DESCRIPTION, character.getShortDescription());
        values.put(SystemConstant.COLUMN_CHAR_GENDER, character.getGender());
        values.put(SystemConstant.COLUMN_CHAR_BIRTHDAY, character.getBirthday());
        values.put(SystemConstant.COLUMN_CHAR_HEIGHT, character.getHeight());
        values.put(SystemConstant.COLUMN_CHAR_WEIGHT, character.getWeight());
        values.put(SystemConstant.COLUMN_CHAR_ADDRESS, character.getAddress());
        values.put(SystemConstant.COLUMN_CHAR_ZODIAC, character.getZodiac());
        return db.update(SystemConstant.TABLE_CHARACTER, values, SystemConstant.COLUMN_ID + " = ?",
                new String[] { String.valueOf(character.getId()) });
    }

    @Override
    public void deleteCharacter(long characterId) {
        SQLiteDatabase db = database.getWritableDatabase();
        db.delete(SystemConstant.TABLE_CHAT, SystemConstant.COLUMN_CHAT_CHARACTER_ID + " = ?",
                new String[] { String.valueOf(characterId) });
        db.delete(SystemConstant.TABLE_CHARACTER, SystemConstant.COLUMN_ID + " = ?",
                new String[] { String.valueOf(characterId) });
    }

    @Override
    public void deleteCharacterByHeart(long heartId) {
        SQLiteDatabase db = database.getWritableDatabase();
        chatDAO = new ChatDAO();
        for(CharacterModel character: findCharacterByHeart(heartId)){
            chatDAO.deleteChatByCharacter(character.getId());
        }
        db.delete(SystemConstant.TABLE_CHARACTER, SystemConstant.COLUMN_CHAR_HEART + " = ?",
                new String[] { String.valueOf(heartId) });
    }

    @SuppressLint("Range")
    @Override
    public List<CharacterModel> getAll() {
        List<CharacterModel> listCharacter = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+SystemConstant.TABLE_CHARACTER;

        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                CharacterModel character = new CharacterModel();
                character.setId(cursor.getLong(cursor.getColumnIndex(SystemConstant.COLUMN_ID)));
                character.setName(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_CHAR_NAME)));
                character.setAvatar(cursor.getBlob(cursor.getColumnIndex(SystemConstant.COLUMN_CHAR_AVATAR)));
                character.setHeart(cursor.getLong(cursor.getColumnIndex(SystemConstant.COLUMN_CHAR_HEART)));
                character.setBot(cursor.getLong(cursor.getColumnIndex(SystemConstant.COLUMN_CHAR_BOT)));
                character.setShortDescription(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_CHAR_SHORT_DESCRIPTION)));
                character.setGender(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_CHAR_GENDER)));
                character.setBirthday(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_CHAR_BIRTHDAY)));
                character.setHeight(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_CHAR_HEIGHT)));
                character.setWeight(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_CHAR_WEIGHT)));
                character.setZodiac(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_CHAR_ZODIAC)));
                character.setAddress(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_CHAR_ADDRESS)));

                listCharacter.add(character);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return listCharacter;
    }

    @SuppressLint("Range")
    @Override
    public List<CharacterModel> findCharacterByHeart(long heartId) {
        List<CharacterModel> listCharacter = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+SystemConstant.TABLE_CHARACTER +" WHERE "+SystemConstant.COLUMN_CHAR_HEART +" = "+heartId;

        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                CharacterModel character = new CharacterModel();
                character.setId(cursor.getLong(cursor.getColumnIndex(SystemConstant.COLUMN_ID)));
                character.setName(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_CHAR_NAME)));
                character.setAvatar(cursor.getBlob(cursor.getColumnIndex(SystemConstant.COLUMN_CHAR_AVATAR)));
                character.setHeart(cursor.getLong(cursor.getColumnIndex(SystemConstant.COLUMN_CHAR_HEART)));
                character.setBot(cursor.getLong(cursor.getColumnIndex(SystemConstant.COLUMN_CHAR_BOT)));
                character.setShortDescription(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_CHAR_SHORT_DESCRIPTION)));
                character.setGender(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_CHAR_GENDER)));
                character.setBirthday(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_CHAR_BIRTHDAY)));
                character.setHeight(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_CHAR_HEIGHT)));
                character.setWeight(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_CHAR_WEIGHT)));
                character.setZodiac(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_CHAR_ZODIAC)));
                character.setAddress(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_CHAR_ADDRESS)));

                listCharacter.add(character);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return listCharacter;
    }

    @SuppressLint("Range")
    @Override
    public CharacterModel findOne(long id) {
        SQLiteDatabase db = database.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + SystemConstant.TABLE_CHARACTER + " WHERE "
                + SystemConstant.COLUMN_ID + " = " + id;
        Cursor cursor = db.rawQuery(selectQuery, null);
        CharacterModel character = null;
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            character = new CharacterModel();
            character.setId(cursor.getLong(cursor.getColumnIndex(SystemConstant.COLUMN_ID)));
            character.setName(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_CHAR_NAME)));
            character.setAvatar(cursor.getBlob(cursor.getColumnIndex(SystemConstant.COLUMN_CHAR_AVATAR)));
            character.setBot(cursor.getLong(cursor.getColumnIndex(SystemConstant.COLUMN_CHAR_BOT)));
            character.setHeart(cursor.getLong(cursor.getColumnIndex(SystemConstant.COLUMN_CHAR_HEART)));
            character.setGender(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_CHAR_GENDER)));
            character.setBirthday(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_CHAR_BIRTHDAY)));
            character.setWeight(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_CHAR_WEIGHT)));
            character.setHeight(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_CHAR_HEIGHT)));
            character.setShortDescription(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_CHAR_SHORT_DESCRIPTION)));
            character.setAddress(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_CHAR_ADDRESS)));
            character.setZodiac(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_CHAR_ZODIAC)));
            cursor.close();
        }
        return character;
    }
}
