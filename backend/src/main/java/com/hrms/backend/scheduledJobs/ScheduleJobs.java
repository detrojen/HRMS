package com.hrms.backend.scheduledJobs;

import com.hrms.backend.services.GameSchedulingServices.GameSlotService;
import com.hrms.backend.services.GameSchedulingServices.GameTypeService;
import com.hrms.backend.services.PostServices.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ScheduleJobs {
    private final GameSlotService gameSlotService;
    private final PostService postService;
    private final GameTypeService gameTypeService;
    @Autowired

    public ScheduleJobs(GameSlotService gameSlotService, PostService postService,GameTypeService gameTypeService){
        this.gameSlotService = gameSlotService;
        this.postService = postService;
        this.gameTypeService = gameTypeService;
    }
    @Scheduled(cron = "0 0 5 * * 7")
    public void initialiseSlots(){
        System.out.println("hello");
        gameSlotService.createSlots(7);
    }
    @Scheduled(cron = "0 1 0 * * *")
    public void genrateBirthdayAndWorkAniversaryPosts(){
        postService.genrateBirthdayAndWorkAniversaryPost();
    }
    @Scheduled(cron = "0 1 0 * * *")
    public void resetCurrentSlotConsumed(){
        gameTypeService.sp_resetCurrentCycleOfGameType();
    }

}
