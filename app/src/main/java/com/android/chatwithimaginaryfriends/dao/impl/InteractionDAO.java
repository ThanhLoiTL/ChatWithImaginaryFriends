package com.android.chatwithimaginaryfriends.dao.impl;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.chatwithimaginaryfriends.constant.SystemConstant;
import com.android.chatwithimaginaryfriends.dao.IHeartDAO;
import com.android.chatwithimaginaryfriends.dao.IInteractionDAO;
import com.android.chatwithimaginaryfriends.database.Database;
import com.android.chatwithimaginaryfriends.model.HeartModel;
import com.android.chatwithimaginaryfriends.model.InteractionModel;
import com.android.chatwithimaginaryfriends.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class InteractionDAO implements IInteractionDAO {
    Database database = MainActivity.database;

    @Override
    public long addInteraction(InteractionModel interaction) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SystemConstant.COLUMN_HEART_ID, interaction.getIdHeart());
        values.put(SystemConstant.COLUMN_TRIGGER_WORD, interaction.getTriggerWord());

        long id = db.insert(SystemConstant.TABLE_INTERACTION, null, values);
        return id;
    }

    @SuppressLint("Range")
    @Override
    public List<InteractionModel> getAll() {
        List<InteractionModel> listInteraction = new ArrayList<>();
        String selectQuery = "SELECT  * FROM "+ SystemConstant.TABLE_INTERACTION;

        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                InteractionModel interaction = new InteractionModel();
                interaction.setId(cursor.getLong(cursor.getColumnIndex(SystemConstant.COLUMN_ID)));
                interaction.setIdHeart(cursor.getLong(cursor.getColumnIndex(SystemConstant.COLUMN_HEART_ID)));
                interaction.setTriggerWord(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_TRIGGER_WORD)));
                interaction.setReplyWord(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_REPLY_PATTERN)));

                listInteraction.add(interaction);
            } while (cursor.moveToNext());
        }
        return listInteraction;
    }

    @Override
    public void deleteInteraction(long id) {
        SQLiteDatabase db = database.getWritableDatabase();
        db.delete(SystemConstant.TABLE_INTERACTION, SystemConstant.COLUMN_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    @Override
    public int updateInteraction(InteractionModel interaction) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SystemConstant.COLUMN_HEART_ID, interaction.getIdHeart());
        values.put(SystemConstant.COLUMN_TRIGGER_WORD, interaction.getTriggerWord());
        values.put(SystemConstant.COLUMN_REPLY_PATTERN, interaction.getReplyWord());
        return db.update(SystemConstant.TABLE_INTERACTION, values, SystemConstant.COLUMN_ID + " = ?",
                new String[] { String.valueOf(interaction.getId()) });
    }

    @SuppressLint("Range")
    @Override
    public InteractionModel findOne(long id) {
        SQLiteDatabase db = database.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + SystemConstant.TABLE_INTERACTION + " WHERE "
                + SystemConstant.COLUMN_ID + " = " + id;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
            cursor.moveToFirst();
        InteractionModel interaction = new InteractionModel();
        interaction.setId(cursor.getLong(cursor.getColumnIndex(SystemConstant.COLUMN_ID)));
        interaction.setIdHeart(cursor.getLong(cursor.getColumnIndex(SystemConstant.COLUMN_HEART_ID)));
        interaction.setTriggerWord(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_TRIGGER_WORD)));
        interaction.setReplyWord(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_REPLY_PATTERN)));

        return interaction;
    }
}
