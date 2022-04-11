package com.android.chatwithimaginaryfriends.model;

public class CharacterModel {
    private String characterName;
    private String heart;
    private byte[] avatar;

    public CharacterModel(String characterName, String heart, byte[] avatar) {
        this.characterName = characterName;
        this.heart = heart;
        this.avatar = avatar;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public String getHeart() {
        return heart;
    }

    public void setHeart(String heart) {
        this.heart = heart;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }
}
