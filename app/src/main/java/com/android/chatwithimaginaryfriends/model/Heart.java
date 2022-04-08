package com.android.chatwithimaginaryfriends.model;

public class Heart {
    private String botName;
    private String description;

    public  Heart(String name, String desc){
        botName = name;
        description = desc;
    }

    public String getBotName() {
        return botName;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
