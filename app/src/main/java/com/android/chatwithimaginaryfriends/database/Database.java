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
            + " TEXT, " + SystemConstant.COLUMN_HEART_DESCRIPTION + " TEXT, "
            + SystemConstant.COLUMN_FINAL_REPLY + " TEXT );";

    private static final String CREATE_TABLE_INTERACTION = "CREATE TABLE "
            + SystemConstant.TABLE_INTERACTION + "(" + SystemConstant.COLUMN_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ SystemConstant.COLUMN_HEART_ID + " INTEGER, "
            + SystemConstant.COLUMN_TRIGGER_WORD +" TEXT, "+ SystemConstant.COLUMN_REPLY_PATTERN +" TEXT);";

    private static final String CREATE_TABLE_CHARACTER = "CREATE TABLE "
            + SystemConstant.TABLE_CHARACTER + "(" + SystemConstant.COLUMN_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ SystemConstant.COLUMN_CHAR_NAME + " TEXT, "
            + SystemConstant.COLUMN_CHAR_AVATAR +" BLOB, "
            + SystemConstant.COLUMN_CHAR_HEART +" INTEGER, "+ SystemConstant.COLUMN_CHAR_BOT+" INTEGER, "
            + SystemConstant.COLUMN_CHAR_SHORT_DESCRIPTION+" TEXT, "
            + SystemConstant.COLUMN_CHAR_GENDER +" TEXT, "
            + SystemConstant.COLUMN_CHAR_BIRTHDAY+" TEXT, "
            + SystemConstant.COLUMN_CHAR_HEIGHT+" TEXT, "
            + SystemConstant.COLUMN_CHAR_WEIGHT+" TEXT, "
            + SystemConstant.COLUMN_CHAR_ZODIAC+" TEXT, "
            + SystemConstant.COLUMN_CHAR_ADDRESS+" TEXT);";

    private static final String CREATE_TABLE_CHAT = "CREATE TABLE "
            + SystemConstant.TABLE_CHAT + "(" + SystemConstant.COLUMN_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + SystemConstant.COLUMN_CHAT_CHARACTER_ID + " INTEGER, "
            + SystemConstant.COLUMN_CHAT_MESSAGE +" TEXT, "
            + SystemConstant.COLUMN_CHAT_TIME +" TEXT, "
            + SystemConstant.COLUMN_CHAT_IS_SEND +" TEXT);";

    private static final String CREATE_TABLE_BOT = "CREATE TABLE "
            + SystemConstant.TABLE_BOT + "(" + SystemConstant.COLUMN_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + SystemConstant.COLUMN_BOT_NAME + " TEXT, "
            + SystemConstant.COLUMN_BOT_DESCRIPTION +" TEXT, "
            + SystemConstant.COLUMN_BOT_AVATAR +" BLOB, "
            + SystemConstant.COLUMN_BOT_URL +" TEXT);";

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
        sqLiteDatabase.execSQL(CREATE_TABLE_CHAT);
        sqLiteDatabase.execSQL(CREATE_TABLE_BOT);
        sqLiteDatabase.execSQL(INSERT_BOT_1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SystemConstant.TABLE_INTERACTION);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SystemConstant.TABLE_HEART);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SystemConstant.TABLE_CHARACTER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SystemConstant.TABLE_CHAT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SystemConstant.TABLE_BOT);
        onCreate(sqLiteDatabase);
    }

    private static final String INSERT_BOT_1 = "INSERT INTO bots values(1,'Brain Bot','This is a brain bot','','http://api.brainshop.ai/get?bid=165426&key=8S10YTsIlEFSMJKk&uid=[uid]&msg=')";
}
