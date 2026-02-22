package com.hrms.backend.services.GameSchedulingServices;

import com.hrms.backend.dtos.responseDtos.gameSheduling.GameSlotResponseDto;
import com.hrms.backend.entities.GameSchedulingEntities.GameSlot;
import com.hrms.backend.exceptions.ItemNotFoundExpection;
import com.hrms.backend.repositories.GameSchedulingRepositories.GameSlotRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class GameSlotService {

    private final GameSlotRepository gameSlotRepository;
    private final ModelMapper modelMapper;
    @Autowired
    public GameSlotService(GameSlotRepository gameSlotRepository, ModelMapper modelMapper){
        this.gameSlotRepository = gameSlotRepository;
        this.modelMapper = modelMapper;
    }

    public List<GameSlotResponseDto> getGameSlotsByDate(Long gameTypeId, LocalDate slotDate){
        List<GameSlot> slots = this.gameSlotRepository.findAllByGameType_IdAndSlotDateIs(gameTypeId,slotDate);
        return slots.stream().map(slot->this.modelMapper.map(slot, GameSlotResponseDto.class)).toList();
    }

    public GameSlotResponseDto getSlotById(Long slotId){
        GameSlot slot = this.gameSlotRepository.findById(slotId).orElseThrow(()-> new ItemNotFoundExpection("slot not found"));
        return this.modelMapper.map(slot,GameSlotResponseDto.class);
    }

    public GameSlot getReference(Long slotId){
        return this.gameSlotRepository.getReferenceById(slotId);
    }

    public GameSlot makeSlotAvailable(GameSlot slot){
        slot.setAvailable(true);
        return gameSlotRepository.save(slot);
    }

    public void makeSlotUnavailable(GameSlot slot){
        slot.setAvailable(false);
        gameSlotRepository.save(slot);
    }

    public void createSlots(int days){
        gameSlotRepository.createGameSlots(days);
    }

}
