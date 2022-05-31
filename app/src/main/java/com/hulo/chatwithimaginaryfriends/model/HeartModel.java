package com.hulo.chatwithimaginaryfriends.model;

import java.io.Serializable;

public class HeartModel implements Serializable {
    private long id;
    private String heartName;
    private String description;
    private String finalReply;

    public HeartModel(String heartName, String description, String finalReply) {
        this.heartName = heartName;
        this.description = description;
        this.finalReply = finalReply;
    }

    public HeartModel() {

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

    public String getFinalReply() {
        return finalReply;
    }

    public void setFinalReply(String finalReply) {
        this.finalReply = finalReply;
    }
}
