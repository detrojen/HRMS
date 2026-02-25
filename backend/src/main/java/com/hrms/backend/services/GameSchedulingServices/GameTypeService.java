package com.hrms.backend.services.GameSchedulingServices;

import com.hrms.backend.dtos.requestDto.gameScheduling.CreateUpdateGameTypeRequestDto;
import com.hrms.backend.dtos.responseDtos.gameSheduling.UpdateGameTypeResponseDto;
import com.hrms.backend.entities.GameSchedulingEntities.GameType;
import com.hrms.backend.exceptions.InvalidActionException;
import com.hrms.backend.exceptions.ItemNotFoundExpection;
import com.hrms.backend.repositories.GameSchedulingRepositories.GameTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameTypeService {
    private final GameTypeRepository gameTypeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public GameTypeService(GameTypeRepository gameTypeRepository, ModelMapper modelMapper){
        this.gameTypeRepository = gameTypeRepository;
        this.modelMapper = modelMapper;
    }

    public GameType getById(Long id){
        return this.gameTypeRepository.findById(id).orElseThrow(()->new ItemNotFoundExpection("Game type with this id not found"));
    }

    public UpdateGameTypeResponseDto createGameType(CreateUpdateGameTypeRequestDto requestDto){
        if(requestDto.getOpeningHours().isAfter(requestDto.getClosingHours())){
            throw  new InvalidActionException("games openig hour must be before closing hour.");
        }
        GameType gameType = modelMapper.map(requestDto, GameType.class);
        gameType = gameTypeRepository.save(gameType);
        return modelMapper.map(gameType, UpdateGameTypeResponseDto.class);
    }

    public UpdateGameTypeResponseDto updateGameType(CreateUpdateGameTypeRequestDto requestDto){
        if(requestDto.getOpeningHours().isAfter(requestDto.getClosingHours())){
            throw  new InvalidActionException("games openig hour must be before closing hour.");
        }
        GameType gameType = getById(requestDto.getId());
        gameType.setGame(requestDto.getGame());
        gameType.setOpeningHours(requestDto.getOpeningHours());
        gameType.setClosingHours(requestDto.getClosingHours());
        gameType.setMaxSlotPerDay(requestDto.getMaxSlotPerDay());
        gameType.setMaxActiveSlotPerDay(requestDto.getMaxActiveSlotPerDay());
        gameType.setMaxNoOfPlayers(requestDto.getMaxNoOfPlayers());
        gameType.setInMaintenance(requestDto.isInMaintenance());
        gameType.setSlotDuration(requestDto.getSlotDuration());
        gameType.setSlotCanBeBookedBefore(requestDto.getSlotCanBeBookedBefore());
        gameType = gameTypeRepository.save(gameType);
        return modelMapper.map(gameType, UpdateGameTypeResponseDto.class);
    }

    public UpdateGameTypeResponseDto getGameTypeById(Long id){
        GameType gameType = gameTypeRepository.findById(id).orElseThrow(()->new ItemNotFoundExpection("Game type with this id not found"));
        int count = gameType.getEmployeeWiseGameInterests().stream().filter(e->e.isInterested()).collect(Collectors.toUnmodifiableList()).size();
        UpdateGameTypeResponseDto dto = modelMapper.map(gameType, UpdateGameTypeResponseDto.class);
        dto.setNoOfInteretedEmployees(count);
        return dto;
    }

    public List<UpdateGameTypeResponseDto> getAllGameTypes(){
        List<GameType> gameTypes = gameTypeRepository.findAll();
        return gameTypes.stream().map(gameType -> {
            int count = gameType.getEmployeeWiseGameInterests().stream().filter(e->e.isInterested()).collect(Collectors.toUnmodifiableList()).size();
            UpdateGameTypeResponseDto dto = modelMapper.map(gameType, UpdateGameTypeResponseDto.class);
            dto.setNoOfInteretedEmployees(count);
            return dto;
        }).collect(Collectors.toUnmodifiableList());
    }

    public void sp_resetCurrentCycleOfGameType(){
        gameTypeRepository.sp_resetCurrentCycleOfGameType();
    }
}
