package com.hulo.chatwithimaginaryfriends.dao.impl;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hulo.chatwithimaginaryfriends.constant.SystemConstant;
import com.hulo.chatwithimaginaryfriends.dao.ICharacterDAO;
import com.hulo.chatwithimaginaryfriends.dao.IHeartDAO;
import com.hulo.chatwithimaginaryfriends.database.Database;
import com.hulo.chatwithimaginaryfriends.model.HeartModel;
import com.hulo.chatwithimaginaryfriends.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class HeartDAO implements IHeartDAO {
    private final Database database = MainActivity.database;
    private ICharacterDAO characterDAO;

    @Override
    public long addHeart(HeartModel heart) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SystemConstant.COLUMN_HEART_NAME, heart.getHeartName());
        values.put(SystemConstant.COLUMN_HEART_DESCRIPTION, heart.getDescription());
        values.put(SystemConstant.COLUMN_FINAL_REPLY, heart.getFinalReply());

        long id = db.insert(SystemConstant.TABLE_HEART, null, values);
        return id;
    }

    @SuppressLint("Range")
    @Override
    public List<HeartModel> getAll() {
        List<HeartModel> listHeart = new ArrayList<>();
        String selectQuery = "SELECT  * FROM hearts";

        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HeartModel heart = new HeartModel();
                heart.setId(cursor.getLong((cursor.getColumnIndex(SystemConstant.COLUMN_ID))));
                heart.setHeartName(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_HEART_NAME)));
                heart.setDescription(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_HEART_DESCRIPTION)));
                heart.setFinalReply(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_FINAL_REPLY)));
                listHeart.add(heart);
            } while (cursor.moveToNext());
        }
        return listHeart;
    }

    @Override
    public void deleteHeart(long heartId) {
        SQLiteDatabase db = database.getWritableDatabase();
        characterDAO = new CharacterDAO();
        for(int i=0;i<characterDAO.findCharacterByHeart(heartId).size();i++){
            characterDAO.deleteCharacterByHeart(heartId);
        }
        db.delete(SystemConstant.TABLE_INTERACTION, SystemConstant.COLUMN_HEART_ID + " = ?",
                new String[] { String.valueOf(heartId) });
        db.delete(SystemConstant.TABLE_HEART, SystemConstant.COLUMN_ID + " = ?",
                new String[] { String.valueOf(heartId) });
    }

    @Override
    public int updateHeart(HeartModel heart) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SystemConstant.COLUMN_HEART_NAME, heart.getHeartName());
        values.put(SystemConstant.COLUMN_HEART_DESCRIPTION, heart.getDescription());
        values.put(SystemConstant.COLUMN_FINAL_REPLY, heart.getFinalReply());
        return db.update(SystemConstant.TABLE_HEART, values, SystemConstant.COLUMN_ID + " = ?",
                new String[] { String.valueOf(heart.getId()) });
    }

    @SuppressLint("Range")
    @Override
    public HeartModel findOne(long id) {
        SQLiteDatabase db = database.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + SystemConstant.TABLE_HEART + " WHERE "
                + SystemConstant.COLUMN_ID + " = " + id;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
            cursor.moveToFirst();
        HeartModel heart = new HeartModel();
        heart.setId(cursor.getLong(cursor.getColumnIndex(SystemConstant.COLUMN_ID)));
        heart.setHeartName(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_HEART_NAME)));
        heart.setDescription(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_HEART_DESCRIPTION)));
        heart.setFinalReply(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_FINAL_REPLY)));
        return heart;
    }
}
