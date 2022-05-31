package com.hulo.chatwithimaginaryfriends.dao;

import com.hulo.chatwithimaginaryfriends.model.BotModel;

import java.util.List;

public interface IBotDAO {
    List<BotModel> getAll();
    BotModel findOne(long id);
}
