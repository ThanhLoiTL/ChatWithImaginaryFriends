package com.android.chatwithimaginaryfriends.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.chatwithimaginaryfriends.constant.SystemConstant;

public class Database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ChatWithImaginaryFriends";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_HEART = "CREATE TABLE "
            + SystemConstant.TABLE_HEART + " (" + SystemConstant.COLUMN_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + SystemConstant.COLUMN_HEART_NAME
            + " TEXT, " + SystemConstant.COLUMN_HEART_DESCRIPTION + " TEXT );";

    private static final String CREATE_TABLE_INTERACTION = "CREATE TABLE "
            + SystemConstant.TABLE_INTERACTION + "(" + SystemConstant.COLUMN_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ SystemConstant.COLUMN_HEART_ID + " INTEGER, "
            + SystemConstant.COLUMN_FINAL_REPLY +" TEXT, "
            + SystemConstant.COLUMN_TRIGGER_WORD +" TEXT, "+ SystemConstant.COLUMN_REPLY_PATTERN +" TEXT);";

    private static final String CREATE_TABLE_CHARACTER = "CREATE TABLE "
            + SystemConstant.TABLE_CHARACTER + "(" + SystemConstant.COLUMN_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ SystemConstant.COLUMN_CHAR_NAME + " TEXT, "
            + SystemConstant.COLUMN_CHAR_AVATAR +" BLOB, "
            + SystemConstant.COLUMN_CHAR_HEART +" INTEGER, "+ SystemConstant.COLUMN_CHAR_BOT+" INTEGER, "
            + SystemConstant.COLUMN_CHAR_SHORT_DESCRIPTION+" TEXT, "
            + SystemConstant.COLUMN_CHAR_GENDER +" TEXT, "
            + SystemConstant.COLUMN_CHAR_BIRTHDAY+" TEXT, "
            + SystemConstant.COLUMN_CHAR_HEIGHT+" REAL, "
            + SystemConstant.COLUMN_CHAR_WEIGHT+" REAL, "
            + SystemConstant.COLUMN_CHAR_ZODIAC+" TEXT, "
            + SystemConstant.COLUMN_CHAR_ADDRESS+" TEXT);";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void QueryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public Cursor GetData(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_HEART);
        sqLiteDatabase.execSQL(CREATE_TABLE_INTERACTION);
        sqLiteDatabase.execSQL(CREATE_TABLE_CHARACTER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SystemConstant.TABLE_INTERACTION);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SystemConstant.TABLE_HEART);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SystemConstant.TABLE_CHARACTER);
        onCreate(sqLiteDatabase);
    }
}
