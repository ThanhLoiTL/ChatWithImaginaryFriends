package com.android.chatwithimaginaryfriends.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;

import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.constant.SystemConstant;
import com.android.chatwithimaginaryfriends.view.AddEditCharacterActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Database extends SQLiteOpenHelper {
    private static Context context;
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
        this.context = context;
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
        insertData(sqLiteDatabase);
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
    private void insertData(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(INSERT_BOT_1, new Object[]{addImage(R.drawable.brandshop)});
        sqLiteDatabase.execSQL(INSERT_HEART_1);
        sqLiteDatabase.execSQL(INSERT_INTER_1);
        sqLiteDatabase.execSQL(INSERT_CHARACTER_1,new Object[]{addImage(R.drawable.anime_nu_sieu_de_thuong_1)});
        sqLiteDatabase.execSQL(INSERT_CHARACTER_2,new Object[]{addImage(R.drawable.image_brainbot)});
    }

    private static final String INSERT_BOT_1 = "INSERT INTO bots values(1,'Brain Bot','This is a brain bot',?,'http://api.brainshop.ai/get?bid=165426&key=8S10YTsIlEFSMJKk&uid=[uid]&msg=')";
    private static final String INSERT_HEART_1 = "INSERT INTO hearts values(null,'Mora','A friendly coyote. She is supposed to be on of my Original characters, she lives in Harmonia with her friend. Her friends are her family. The members of her family Belinda, may,...','I don''t know that,I do not understand,;))')";
    private static final String INSERT_INTER_1 = "INSERT INTO interactions values(null,1,'Hey','Yeah! You can talk to me anytime'), " +
            "(null,1,'I love you','Aw'), (null,1,'Hi,hello,hey','Hellooo'),(null,1,'good','Great!'),(null,1,'feel upset,angry,sad','What happened?')," +
            "(null,1,'go for a hike?,go for a walk?','HELL YES'),(null,1,'Awesome,Great,Nice,Amazing,Hooray',':)'),(null,1,'hurt','How?'),(null,1,'hurt','How?')," +
            "(null,1,'understands','How can you be sure?'),(null,1,'FUCK,vent,shitty day,Oh shit','Whoa whoa whoa,Slow down,Take a deep breath')," +
            "(null,1,'How are you,How ya doin','I am okay I guess'),(null,1,'I cant stop thinking of my ex','Dont let toxic people into your life'),(null,1,'So happy','Meeee tooooooo')," +
            "(null,1,'I like you','As a friend?,Like you'),(null,1,':(,>:(',':(,So sad'),(null,1,'lonely,want a girlfriend,want a boyfriend','You remind me of my love'),(null,1,'sorry','Its alright')," +
            "(null,1,'concerned','About you'),(null,1,'are you ok','Yes'),(null,1,'favorite color','Sky blue'),(null,1,'cheer','Thank you'),(null,1,'thank you,thanks','No problem')," +
            "(null,1,'glad','Me too'),(null,1,'give you food,some food','Give me a burger!'),(null,1,'i hate you','Then why are you taking to me?'),(null,1,'tired','Maybe you should take nap')," +
            "(null,1,'hehe,haha,hoho,hihi',';),hehe,haha,hoho,hihi'),(null,1,'nothing,no','Okey'),(null,1,'hungry','I will give you a burger'),(null,1,'morning','Good moring'),(null,1,'afternoon','Good afternoon')," +
            "(null,1,'meaning of life','Good question'),(null,1,'What are you doing','Taking to you'),(null,1,'I dont know','We cant know everything'),(null,1,'Why are you crying','Its been stressful day.')," +
            "(null,1,'sleep','(yawn)...'),(null,1,'sleep','(yawn)...'),(null,1,'goodnight','Goodnight :)'),(null,1,'love music','Music is healing'),(null,1,'agree','Yess'),(null,1,'perfect day','You sound happy and cheery')," +
            "(null,1,'sick','Sick? Oh no'),(null,1,'lonely','I understand'),(null,1,'eating','Mmmm'),(null,1,'relax','I will relax with you'),(null,1,'talking','Okey,Sure')";

    private byte[] addImage(int image) {
        @SuppressLint("UseCompatLoadingForDrawables") Bitmap bm = ((BitmapDrawable) context.getResources().getDrawable(image)).getBitmap();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, os);
        return os.toByteArray();
    }

    private static final String INSERT_CHARACTER_1 = "INSERT INTO characters values(null,'Mora',?,1,0,'Chat with me everywhere','Female','2001/03/04','1m55','45kg','Pisces','Japan')";
    private static final String INSERT_CHARACTER_2 = "INSERT INTO characters values(null,'Brain AI',?,0,1,'Brain Shop by Acobot','Male','1999/03/15','1m65','65kg','Pisces','Amarican')";
}
