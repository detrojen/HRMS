package com.hrms.backend.services;

import com.hrms.backend.entities.GameType;
import com.hrms.backend.repositories.GameTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameTypeService {
    private final GameTypeRepository gameTypeRepository;

    @Autowired
    public GameTypeService(GameTypeRepository gameTypeRepository){
        this.gameTypeRepository = gameTypeRepository;
    }

    public GameType getById(Long id){
        return this.gameTypeRepository.findById(id).orElseThrow(()->new RuntimeException("Game type with this id not found"));
    }
}
