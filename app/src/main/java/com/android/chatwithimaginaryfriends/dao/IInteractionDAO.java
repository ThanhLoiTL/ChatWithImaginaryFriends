package com.android.chatwithimaginaryfriends.dao;

import com.android.chatwithimaginaryfriends.model.InteractionModel;

import java.util.List;

public interface IInteractionDAO {
    long addInteraction(InteractionModel interaction);
    List<InteractionModel> getAll();
    void deleteInteraction(long id);
    int updateInteraction(InteractionModel interactionModel);
    InteractionModel findOne(long id);
    List<InteractionModel> findByHeart(long heartId);
}
