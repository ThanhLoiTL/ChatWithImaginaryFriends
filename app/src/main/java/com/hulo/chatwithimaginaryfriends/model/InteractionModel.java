package com.hulo.chatwithimaginaryfriends.model;

import java.io.Serializable;

public class InteractionModel implements Serializable {
    private Long id;
    private Long idHeart;
    private String triggerWord;
    private String replyWord;

    public InteractionModel(Long id, Long idHeart, String triggerWord, String replyWord) {
        this.id = id;
        this.idHeart = idHeart;
        this.triggerWord = triggerWord;
        this.replyWord = replyWord;
    }

    public InteractionModel() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdHeart() {
        return idHeart;
    }

    public void setIdHeart(Long idHeart) {
        this.idHeart = idHeart;
    }

    public String getTriggerWord() {
        return triggerWord;
    }

    public void setTriggerWord(String triggerWord) {
        this.triggerWord = triggerWord;
    }

    public String getReplyWord() {
        return replyWord;
    }

    public void setReplyWord(String replyWord) {
        this.replyWord = replyWord;
    }

}
