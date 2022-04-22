package com.android.chatwithimaginaryfriends.dao;

import com.android.chatwithimaginaryfriends.model.HeartModel;

import java.util.List;

public interface IHeartDAO {
    long addHeart(HeartModel heart);
    List<HeartModel> getAll();
    void deleteHeart(long id);
    int updateHeart(HeartModel heartModel);
    HeartModel findOne(long id);
}
