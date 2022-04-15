package com.android.chatwithimaginaryfriends.model;

import java.io.Serializable;

public class HeartModel implements Serializable {
    private long id;
    private String heartName;
    private String description;

    public HeartModel(long id, String heartName, String description){
        this.id = id;
        this.heartName = heartName;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeartName() {
        return heartName;
    }

    public void setHeartName(String heartName) {
        this.heartName = heartName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
