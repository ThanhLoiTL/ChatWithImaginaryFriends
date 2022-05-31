package com.hulo.chatwithimaginaryfriends.dao.impl;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hulo.chatwithimaginaryfriends.constant.SystemConstant;
import com.hulo.chatwithimaginaryfriends.dao.IBotDAO;
import com.hulo.chatwithimaginaryfriends.database.Database;
import com.hulo.chatwithimaginaryfriends.model.BotModel;
import com.hulo.chatwithimaginaryfriends.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class BotDAO implements IBotDAO {
    Database database = MainActivity.database;

    @SuppressLint("Range")
    @Override
    public List<BotModel> getAll() {
        List<BotModel> listBot = new ArrayList<>();
        String selectQuery = "SELECT  * FROM "+SystemConstant.TABLE_BOT;

        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                BotModel bot = new BotModel();
                bot.setId(cursor.getLong((cursor.getColumnIndex(SystemConstant.COLUMN_ID))));
                bot.setName(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_BOT_NAME)));
                bot.setDescription(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_BOT_DESCRIPTION)));
                bot.setAvatar(cursor.getBlob(cursor.getColumnIndex(SystemConstant.COLUMN_BOT_AVATAR)));
                bot.setUrl(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_BOT_URL)));
                listBot.add(bot);
            } while (cursor.moveToNext());
        }
        return listBot;
    }

    @SuppressLint("Range")
    @Override
    public BotModel findOne(long id) {
        SQLiteDatabase db = database.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + SystemConstant.TABLE_BOT + " WHERE "
                + SystemConstant.COLUMN_ID + " = " + id;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
            cursor.moveToFirst();
        BotModel bot = new BotModel();
        bot.setId(cursor.getLong((cursor.getColumnIndex(SystemConstant.COLUMN_ID))));
        bot.setName(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_BOT_NAME)));
        bot.setDescription(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_BOT_DESCRIPTION)));
        bot.setAvatar(cursor.getBlob(cursor.getColumnIndex(SystemConstant.COLUMN_BOT_AVATAR)));
        bot.setUrl(cursor.getString(cursor.getColumnIndex(SystemConstant.COLUMN_BOT_URL)));
        return bot;
    }
}
