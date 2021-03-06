package com.hulo.chatwithimaginaryfriends.dao;

import com.hulo.chatwithimaginaryfriends.model.CharacterModel;

import java.util.List;

public interface ICharacterDAO {
    long addCharacter(CharacterModel character);
    int updateCharacter(CharacterModel character);
    void deleteCharacter(long id);
    void deleteCharacterByHeart(long id);
    List<CharacterModel> getAll();
    List<CharacterModel> findCharacterByHeart(long heartId);
    CharacterModel findOne(long id);
}
