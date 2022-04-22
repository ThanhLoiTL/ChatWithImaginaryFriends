package com.android.chatwithimaginaryfriends.dao.impl;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.chatwithimaginaryfriends.constant.SystemConstant;
import com.android.chatwithimaginaryfriends.dao.IHeartDAO;
import com.android.chatwithimaginaryfriends.database.Database;
import com.android.chatwithimaginaryfriends.model.HeartModel;
import com.android.chatwithimaginaryfriends.model.InteractionModel;
import com.android.chatwithimaginaryfriends.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class HeartDAO implements IHeartDAO {
    Database database = MainActivity.database;

    @Override
    public long addHeart(HeartModel heart) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SystemConstant.COLUMN_HEART_NAME, heart.getHeartName());
        values.put(SystemConstant.COLUMN_HEART_DESCRIPTION, heart.getDescription());

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

                listHeart.add(heart);
            } while (cursor.moveToNext());
        }
        return listHeart;
    }

    @Override
    public void deleteHeart(long id) {
        SQLiteDatabase db = database.getWritableDatabase();
        db.delete(SystemConstant.TABLE_HEART, SystemConstant.COLUMN_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    @Override
    public int updateHeart(HeartModel heart) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SystemConstant.COLUMN_HEART_NAME, heart.getHeartName());
        values.put(SystemConstant.COLUMN_HEART_DESCRIPTION, heart.getDescription());
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
        return heart;
    }
}
