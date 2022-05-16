package com.android.chatwithimaginaryfriends.model;

public class BotModel {
    private Long id;
    private String name;
    private String description;
    private byte[] avatar;
    private String url;

    public BotModel(Long id, String name, String description, byte[] avatar, String url) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.avatar = avatar;
        this.url = url;
    }

    public BotModel() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
