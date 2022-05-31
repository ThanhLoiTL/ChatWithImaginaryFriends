package com.hulo.chatwithimaginaryfriends.dao;

import com.hulo.chatwithimaginaryfriends.model.InteractionModel;

import java.util.List;

public interface IInteractionDAO {
    long addInteraction(InteractionModel interaction);
    List<InteractionModel> getAll();
    void deleteInteraction(long id);
    int updateInteraction(InteractionModel interactionModel);
    InteractionModel findOne(long id);
    List<InteractionModel> findByHeart(long heartId);
}
