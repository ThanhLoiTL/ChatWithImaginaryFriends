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
    @SuppressLint("StaticFieldLeak")
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
        sqLiteDatabase.execSQL(INSERT_HEART_2);
        sqLiteDatabase.execSQL(INSERT_INTER_2);
        sqLiteDatabase.execSQL(INSERT_CHARACTER_1,new Object[]{addImage(R.drawable.anime_nu_sieu_de_thuong_1)});
        sqLiteDatabase.execSQL(INSERT_CHARACTER_2,new Object[]{addImage(R.drawable.image_brainbot)});
    }

    private static final String INSERT_BOT_1 = "INSERT INTO bots values(1,'Brain Bot','This is a brain bot',?,'http://api.brainshop.ai/get?bid=165426&key=8S10YTsIlEFSMJKk&uid=thanhloi&msg=')";
    private static final String INSERT_HEART_1 = "INSERT INTO hearts values(null,'Mora','A friendly coyote. She is supposed to be on of my Original characters, she lives in Harmonia with her friend. Her friends are her family. The members of her family Belinda, may,...','I don''t know that,I do not understand,;))')";
    private static final String INSERT_HEART_2 = "INSERT INTO hearts values(null,'Marvel','The films of the Marvel Cinematic Universe are a series of superhero works shown on the big screen, based on characters from the Marvel Comics.','I don''t know that,I don''t understand,;))')";
    private static final String INSERT_INTER_1 = "INSERT INTO interactions values(null,1,'Hey','Yeah! You can talk to me anytime'), " +
            "(null,1,'I love you','Aw'), (null,1,'Hi you,hello,hey','Hellooo'),(null,1,'good','Great!'),(null,1,'feel upset,angry,sad','What happened?')," +
            "(null,1,'go for a hike?,go for a walk?','HELL YES'),(null,1,'Awesome,Great,Nice,Amazing,Hooray',':)'),(null,1,'hurt','How?'),(null,1,'hurt','How?')," +
            "(null,1,'understands','How can you be sure?'),(null,1,'FUCK,vent,shitty day,Oh shit','Whoa whoa whoa,Slow down,Take a deep breath')," +
            "(null,1,'How are you,How ya doin','I''m okay I guess'),(null,1,'I can''t stop thinking of my ex','Dont let toxic people into your life'),(null,1,'So happy','Meeee tooooooo')," +
            "(null,1,'I like you','As a friend?,Like you'),(null,1,':(,>:(',':(,So sad'),(null,1,'lonely,want a girlfriend,want a boyfriend','You remind me of my love'),(null,1,'sorry','Its alright')," +
            "(null,1,'concerned','About you'),(null,1,'are you ok','Yes'),(null,1,'favorite color','Sky blue'),(null,1,'cheer','Thank you'),(null,1,'thank you,thanks','No problem')," +
            "(null,1,'glad','Me too'),(null,1,'give you food,some food','Give me a burger!'),(null,1,'i hate you','Then why are you taking to me?'),(null,1,'tired','Maybe you should take nap')," +
            "(null,1,'hehe,haha,hoho,hihi',';),hehe,haha,hoho,hihi'),(null,1,'nothing,no','Okey'),(null,1,'hungry','I will give you a burger'),(null,1,'morning','Good moring'),(null,1,'afternoon','Good afternoon')," +
            "(null,1,'meaning of life','Good question'),(null,1,'What are you doing','Taking to you'),(null,1,'I dont know','We cant know everything'),(null,1,'Why are you crying','Its been stressful day.')," +
            "(null,1,'sleep','(yawn)...'),(null,1,'goodnight','Goodnight :)'),(null,1,'love music','Music is healing'),(null,1,'agree','Yess'),(null,1,'perfect day','You sound happy and cheery')," +
            "(null,1,'sick','Sick? Oh no'),(null,1,'lonely','I understand'),(null,1,'eating','Mmmm'),(null,1,'relax','I will relax with you'),(null,1,'talking','Okey,Sure')";

    private static final String INSERT_INTER_2 = "INSERT INTO interactions values " +
            "(null,2,'Hi,How are you,Is anyone there?,Hell,Good day,Whats up,hello,hehe,hey,hey bro','Hello,Good to see you again,Hi there,how can I help?'), " +
            "(null,2,'See you later,Goodbye,I am Leaving,Have a Good day','Talk to you later, Goodbye'), " +
            "(null,2,'how old,how old are u,what is your age,how old are you,age?','I am 20 years old,20 years young,I dont know'), " +
            "(null,2,'what is your name,what should I call you,whats your name?','You can call me Arthur,I am Arthur'), " +
            "(null,2,'what do u do?,what job?,job?','I dont have a job,Talk to you,Hmmm'), " +
            "(null,2,'are you married?,married?','No,Nope I am single,I am divorced'), " +
            "(null,2,'Where are you from,you from?,from','I come from the UK,United Kingdom'), " +
            "(null,2,'What your hobby,hobby?,free time','I am talk to you,I miss you'), " +
            "(null,2,'movie,film,cinema,television,watching movies','Tell me your favorite movie'), " +
            "(null,2,'spider,spider man,peter parker,parker','Wow great movie,I also like that movie,Spider Man is very strong,MJ is so pretty,I very like his Iron Spider Suit'), " +
            "(null,2,'Iron Man,iron,tony stark','He is so cool in End Game,He is very rich,Iron Man is very strong,Iron man saved the world,I am Iron Man,I like Captain America more, I love you 3000')," +
            "(null,2,'Captain America,captain,Steve,Rogers','He is so cool in End Game,Captain America is very strong,Avengers assemble !!!,Peggy so pretty'), " +
            "(null,2,'Thor,Thor Odinson,Odinson,Mjolnir','Wow great movie,Thor very strong with Mjolnir,He cold,Do you know his new movie?,Thor is strong but i like Loki better,He killed Thanos,He is very strong in Thor Ragnarok'), " +
            "(null,2,'Black Widow,Natasha,Romanoff','Black Widow she so sexy,I very like her'), " +
            "(null,2,'Hulk,Robert Bruce Banner,Robert,Mr. Green,Big Green Guy,Green Snowman,Green','Hulk very strong, I like Hulk with Iron Man Armor, He big and strong'), " +
            "(null,2,'Hawkeye,Clinton Barton,Clinton,Barton','His weapon is a bow and arrow,He so cold'), " +
            "(null,2,'Sam Wilson,Falcon','Sam Thomas Wilson was born in the neighborhood of Harlem,He can fly, He strong,Black Panther has given Falcon a top notch outfit and outfit'), " +
            "(null,2,'Bucky,Bucky Barnes,Winter Soldier,White Wolf,Soldat','He so cold,He is a best friend of Steve Rogers,Bucky Barnes is a man with strong ideals,He was injected with super soldier serum'), " +
            "(null,2,'War Machine,James Rhodes,Rhodey','He so cold,War Machine Armor created by Tony Stark,Rhodey is a lieutenant in the United States Marine Corps'), " +
            "(null,2,'Wanda Maximoff,Wanda,Scarlet Witch,Little Witch,The Enhanced','Scarlet Witch aka Wanda Maximoff so strong,She so pretty,Scarlet Witch so strong in Doctor Strange 2,Wanda is endowed with the ability to control energy,Scarlet Witch use magic, and spirituality'), " +
            "(null,2,'Ant man,ant,Scott Lang','Scott Edward Harris Lang is a former criminal,Ant man using Pym, Ant man can manipulating the size of molecules,Ant man has the ability to turn large and small'), " +
            "(null,2,'Janet,Dyne,Wasp','She is the daughter of Dr Hank Pym,She and Scott Lang defeated Cross,She has cool fighting skills,She so pretty'), " +
            "(null,2,'Star Lord,Peter Jason Quill','He is half human half Celestial, He is an idiot in End Game,He appeared in Guardians of the Galaxy'), " +
            "(null,2,'Gamora','Gamora is the last of the Zen Whoberi,Gamora was raised and trained by Thanos,She joined the Guardians of the Galaxy, Hmmmm she so green'), " +
            "(null,2,'Nebula','Nebula is a Luphomoid assassin,She is the adopted daughter Thanos adopted sister of Gamora,She and Rocket are the remaining members of the Guardians of the Galaxy, Hmmmm she so blue'), " +
            "(null,2,'Drax the Destroyer,Drax,Arthur Douglas','He is a member of the group Infinity Watch,He has fly and shoot energy from both hands,Drax has not very high IQ'), " +
            "(null,2,'Groot','Groot so cute,I very like Groot,Groot can regenerate cells,Groot can lift Thor Stormbreaker'), " +
            "(null,2,'Rocket Racoon','Rocket is a member of the Guardians of the Galaxy,Rocket so cute,I like Rocket with his weapon,Rocket is a pilot in galaxy'), " +
            "(null,2,'Mantis','Mantis is a member of the Guardians of the Galaxy,She so cute,Mantis has two German-Vietnamese,Mantis was born in Hue, Vietnam, I love her,She so pretty'), " +
            "(null,2,'Doctor Stephen Strange,','Doctor Strange so cold, He very strong, He uses magic to fight,He is intelligent,I like his cape,I love you every universe,I regret his love story,You should watch the movie Doctor Strange in the Multiverse of Madness'), " +
            "(null,2,'Black Panther,Challa','Wakanda foreverrrr,He is the king of Wakanda,He so colddd, He so strong with Black Panther Armor,Challa aka Black Panther has a high intellectual mind'), " +
            "(null,2,'Carol Danvers,Captain Marvel','She so strong, She can solo Thanos, She so pretty,She is a NASA security officer,Captain Marvel can channel energy from his hands'), " +
            "(null,2,'Loki Laufeyson,','God of deception Lokiii,Loki Laufeyson is Laufey''s biological son, I very like Loki,Loki is Thor adopted brother,I regret that Loki was killed by Thanos,He so cold in movie Avengers 1'), " +
            "(null,2,'Vision','He so cold in movie Avengers 2,He possesses the powerful Mind Stone,I regret that Vision was killed by Thanos, I feel poor for Wanda about Vision death'), " +
            "(null,2,'Ancient One','The Ancient One was once the master of Stephen Vincent Strange'), " +
            "(null,2,'Thanos,Dark Lord,Mad Titan,Genocidal Maniac','He so strong in Infinity War and End Game,Thanos uses the infinity gauntlet, He big and strong, His nickname is purple potato,He is facing the Avengers team'), " +
            "(null,2,'Avengers team','I very like Avengers, I am a fan of Avengers, I like iron man the most in the Avenger group,Avengers team saved the world from Thanos'), " +
            "(null,2,'Age of Ultron','This is good movie,I have watched this movie 3 times already'), " +
            "(null,2,'Homecoming,spider','This is first movie spider man in Marvel Studio,Good spider man movie,Spider man in movie Homecoming so cold with spider suit'), " +
            "(null,2,'Ragnarok','Great movie i like this, You should watch Ragnarok before watching Infinity war,I really like Thor in the movie Ragnarok'), " +
            "(null,2,'Endgame,End Game','Great movie i love this, I am so sad about iron man''s death'), " +
            "(null,2,'Far from home','I really like Mysterio in this movie,I like love story peter and MJ,MJ so cute'), " +
            "(null,2,'Eternals','Sorry I have not seen this movie yet,Critics said it was very good,Eternals is quite strong'), " +
            "(null,2,'No Way Home','Great because there are 3 spider man,I feel sad for Aunt May because she is dead'), " +
            "(null,2,'Peggy Carter','She so beautiful,Peggy Carter is girlfriends of Captain America'), " +
            "(null,2,'Pepper Potts','She is wife of Tony Stark,She so beautiful and intelligent,I love her'), " +
            "(null,2,'Odin','He is the mighty king of Asgard'), " +
            "(null,2,'Nick Fury,Nicholas Joseph Fury,Unseen','He was a great leader for the Avengers and SHIELD'), " +
            "(null,2,'SHIELD','Is a military counter terrorism intelligence agency of the United States government whose mission is to maintain world security'), " +
            "(null,2,'Stan Lee','He has made a great contribution in turning Marvel Comics from a small company into a giant multimedia corporation, I am a fan of Stan Lee,I respect his dedication to Marvel'), " +
            "(null,2,'Wong','Wong is a descendant of Kan, Wong so cute, I very like Wong because his cute and strong,He is a Chinese monk who lived about a thousand years ago and is a magician'), " +
            "(null,2,'Hela','Hela Odinsdottir is an Asgardian god,Hela so mystery and strong,Hela is hysterical impulsive unstable and extremely ambitious, Hela so cold in movie Ragnarok'), " +
            "(null,2,'release,Iron Man','Release date May 16 2008'), " +
            "(null,2,'release,First Avenger','Release date July 29 2011'), " +
            "(null,2,'release,Incredible Hulk','Release date June 13 2008'), " +
            "(null,2,'release,Iron Man 2','Release date May 7 2010'), " +
            "(null,2,'release,Thor','Release date April 29 2011'), " +
            "(null,2,'release,The Avengers','Release date April 27 2012'), " +
            "(null,2,'release,Iron Man 3','Release date April 26 2013'), " +
            "(null,2,'release,The Dark World,Thor 2','Release date November 8  2013'), " +
            "(null,2,'release,Winter Soldier','Release date April 4 2014'), " +
            "(null,2,'release,Guardians of the Galaxy','Release date August 1  2014'), " +
            "(null,2,'release, Age of Ultron','Release date April 24 2015'), " +
            "(null,2,'release,Ant Man','Release date July 24 2015'), " +
            "(null,2,'release,Guardians of the Galaxy 2','Release date May 5 2017'), " +
            "(null,2,'release, Doctor Strange ','Release date May 4 2022'), " +
            "(null,2,'release,Civil War','Release date April 27 2016'), " +
            "(null,2,'release,Black Widow','Release date July 9 2021'), " +
            "(null,2,'release,Homecoming','Release date July 7 2017'), " +
            "(null,2,'release,Black Panther','Release date February 16 2018'), " +
            "(null,2,'release,Ragnarok','Release date November 3 2017'), " +
            "(null,2,'release,Infinity War','Release date April 27 2018'), " +
            "(null,2,'release,Ant Man and The Wasp','Release date July 6 2018'), " +
            "(null,2,'release,Captain Marvel','Release date March 7 2019'), " +
            "(null,2,'release,Endgame','Release date April 26 2019'), " +
            "(null,2,'release,Far From Home','Release date June 26 2019'), " +
            "(null,2,'release,WandaVision','Release date January 15 2021'), " +
            "(null,2,'release,Eternals','Release date November 5 2021'), " +
            "(null,2,'release,No Way Home','Release date December 17 2021')";

    private byte[] addImage(int image) {
        @SuppressLint("UseCompatLoadingForDrawables") Bitmap bm = ((BitmapDrawable) context.getResources().getDrawable(image)).getBitmap();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, os);
        return os.toByteArray();
    }

    private static final String INSERT_CHARACTER_1 = "INSERT INTO characters values(null,'Mora',?,1,0,'Chat with me everywhere','Female','2001/03/04','1m55','45kg','Pisces','Japan')";
    private static final String INSERT_CHARACTER_2 = "INSERT INTO characters values(null,'Brain AI',?,0,1,'Brain Shop by Acobot','Male','1999/03/15','1m65','65kg','Pisces','Amarican')";
}
