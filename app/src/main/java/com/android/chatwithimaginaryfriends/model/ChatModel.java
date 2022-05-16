package com.android.chatwithimaginaryfriends.model;
public class ChatModel {
    private Long id;
    private Long charterId;
    private String message;
    private String time;
    private Boolean isSend;

    public ChatModel(Long charterId, String message, String time, Boolean isSend) {
        this.charterId = charterId;
        this.message = message;
        this.time = time;
        this.isSend = isSend;
    }

    public ChatModel() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCharterId() {
        return charterId;
    }

    public void setCharterId(Long charterId) {
        this.charterId = charterId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getIsSend() {
        return isSend;
    }

    public void setIsSend(Boolean send) {
        isSend = send;
    }
}
