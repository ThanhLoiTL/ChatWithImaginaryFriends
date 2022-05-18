package com.android.chatwithimaginaryfriends.constant;

import com.android.chatwithimaginaryfriends.R;

public class SystemConstant {
    public static final int DEFAULT_THEME = R.style.BottomSheetDialogTheme;

    public static final String TABLE_HEART = "hearts";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_HEART_NAME = "name";
    public static final String COLUMN_HEART_DESCRIPTION = "description";

    public static final String TABLE_INTERACTION = "interactions";
    public static final String COLUMN_HEART_ID = "heart_id";
    public static final String COLUMN_FINAL_REPLY = "final_reply";
    public static final String COLUMN_TRIGGER_WORD = "trigger_word";
    public static final String COLUMN_REPLY_PATTERN = "reply_pattern";

    public static final String TABLE_CHARACTER = "characters";
    public static final String COLUMN_CHAR_NAME = "name";
    public static final String COLUMN_CHAR_AVATAR = "avatar";
    public static final String COLUMN_CHAR_HEART = "heart";
    public static final String COLUMN_CHAR_BOT = "bot";
    public static final String COLUMN_CHAR_SHORT_DESCRIPTION = "short_description";
    public static final String COLUMN_CHAR_GENDER = "gender";
    public static final String COLUMN_CHAR_BIRTHDAY = "birthday";
    public static final String COLUMN_CHAR_HEIGHT = "height";
    public static final String COLUMN_CHAR_WEIGHT = "weight";
    public static final String COLUMN_CHAR_ZODIAC = "zodiac";
    public static final String COLUMN_CHAR_ADDRESS = "address";

    public static final String TABLE_CHAT = "chats";
    public static final String COLUMN_CHAT_CHARACTER_ID = "character_id";
    public static final String COLUMN_CHAT_MESSAGE = "message";
    public static final String COLUMN_CHAT_TIME = "time";
    public static final String COLUMN_CHAT_IS_SEND = "is_send";

    public static final String TABLE_BOT = "bots";
    public static final String COLUMN_BOT_NAME = "name";
    public static final String COLUMN_BOT_DESCRIPTION = "description";
    public static final String COLUMN_BOT_AVATAR = "avatar";
    public static final String COLUMN_BOT_URL = "url";
}
