package com.android.chatwithimaginaryfriends.dao;

import com.android.chatwithimaginaryfriends.model.BotModel;

import java.util.List;

public interface IBotDAO {
    List<BotModel> getAll();
    BotModel findOne(long id);
}
