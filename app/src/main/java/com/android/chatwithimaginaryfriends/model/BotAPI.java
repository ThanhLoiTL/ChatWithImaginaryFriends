package com.android.chatwithimaginaryfriends.model;

public class BotAPI {
    public String response;
    public String id;
    public int result;
    public String msg;
    public String cnt;

    public BotAPI(String response, String id, int result, String msg, String cnt) {
        this.response = response;
        this.id = id;
        this.result = result;
        this.msg = msg;
        this.cnt = cnt;
    }

    public BotAPI() {
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }
}
