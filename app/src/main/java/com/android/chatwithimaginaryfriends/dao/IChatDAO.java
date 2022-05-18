package com.android.chatwithimaginaryfriends.dao;

import com.android.chatwithimaginaryfriends.model.ChatModel;

import java.util.List;

public interface IChatDAO {
    long addChat(ChatModel chat);
    List<ChatModel> getAll();
    void deleteChat(long id);
    void deleteChatByCharacter(long id);
    int updateChat(ChatModel chat);
    ChatModel findOne(long id);
    List<ChatModel> findByCharacter(long characterId);
    List<ChatModel> findByChat();
}
