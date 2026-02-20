package com.hrms.backend.scheduledJobs;

import com.hrms.backend.services.GameSchedulingServices.GameSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleSlotCreation {
    private final GameSlotService gameSlotService;
    @Autowired
    public  ScheduleSlotCreation(GameSlotService gameSlotService){
        this.gameSlotService = gameSlotService;
    }
    @Scheduled(cron = "0 0 10 * * *")
    public void initialiseSlots(){
        gameSlotService.createSlots();
    }

//    @Scheduled
}
