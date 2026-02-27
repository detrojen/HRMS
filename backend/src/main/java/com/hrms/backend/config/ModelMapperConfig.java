package com.hrms.backend.config;

import com.hrms.backend.dtos.responseDtos.gameSheduling.EmployeeWiseGameInterestResponseDto;
import com.hrms.backend.dtos.responseDtos.gameSheduling.GameSlotResponseDto;
import com.hrms.backend.dtos.responseDtos.gameSheduling.UpdateGameTypeResponseDto;
import com.hrms.backend.dtos.responseDtos.job.CreateJobResponseDto;
import com.hrms.backend.dtos.responseDtos.job.CvReviewResponseDto;
import com.hrms.backend.dtos.responseDtos.post.DeletePostResponseDto;
import com.hrms.backend.dtos.responseDtos.post.PostResponseDto;
import com.hrms.backend.dtos.responseDtos.travel.TravelDocumentResponseDto;
import com.hrms.backend.entities.GameSchedulingEntities.EmployeeWiseGameInterest;
import com.hrms.backend.entities.GameSchedulingEntities.GameSlot;
import com.hrms.backend.entities.GameSchedulingEntities.GameType;
import com.hrms.backend.entities.JobListingEntities.CvReview;
import com.hrms.backend.entities.JobListingEntities.Job;
import com.hrms.backend.entities.PostEntities.Post;
import com.hrms.backend.entities.TravelEntities.TravelDocument;
import com.hrms.backend.entities.TravelEntities.TravelWiseEmployeeWiseDocument;
import org.hibernate.collection.spi.PersistentBag;
import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        addPostResponseMapping(modelMapper);
        addDeletedPostResponseDtoMapping(modelMapper);
        addGameSlotResponseDto(modelMapper);
        addEmployeeWiseGameInterestResponseDto(modelMapper);
        addCvReviewResponseDto(modelMapper);
//        addCreateJobResponseDto(modelMapper);
        return modelMapper;
    }

    public void addPostResponseMapping(ModelMapper modelMapper){
        modelMapper.createTypeMap(Post.class, PostResponseDto.class)
                .addMappings(mapper->{
                    mapper.map(src -> src.getCreatedBy().getFullName(),PostResponseDto::setCreatedBy);
                });
    }

    public void addGameSlotResponseDto(ModelMapper modelMapper){
        modelMapper.createTypeMap(GameSlot.class, GameSlotResponseDto.class)
                .addMappings(mapper->{
                    mapper.map(src -> src.getGameType().getId(),GameSlotResponseDto::setGameTypeId);
                    mapper.map(src->src.getGameType().getGame(),GameSlotResponseDto::setGameType);
                });
    }

    public void addDeletedPostResponseDtoMapping(ModelMapper modelMapper){
        modelMapper.createTypeMap(Post.class, DeletePostResponseDto.class)
                .addMappings(mapper->{
                    mapper.map(src->src.getCreatedBy().getFullName(),DeletePostResponseDto::setCreatedBy);
                    mapper.map(src->src.getDeletedBy().getFullName(),DeletePostResponseDto::setDeletedBy);
                });
    }

    public void addEmployeeWiseGameInterestResponseDto(ModelMapper modelMapper){
        modelMapper.createTypeMap(EmployeeWiseGameInterest.class, EmployeeWiseGameInterestResponseDto.class).addMappings(
                mapper->{
                    mapper.map(src->src.getGameType().getGame(),EmployeeWiseGameInterestResponseDto::setGameType);
                    mapper.map(src->src.getGameType().getId(),EmployeeWiseGameInterestResponseDto::setGameTypeId);
                }
        );
    }
    public void addCvReviewResponseDto(ModelMapper modelMapper){
        modelMapper.createTypeMap(CvReview.class, CvReviewResponseDto.class).addMappings(
                mapper->{
                    mapper.map(src->src.getCvReviewer().getReviewer(), CvReviewResponseDto::setReviewedBy);
                }
        );
    }

//    public void addCreateJobResponseDto(ModelMapper modelMapper){
//        modelMapper.createTypeMap(Job.class, CreateJobResponseDto.class).addMappings(
//                mapper->{
//                    mapper.map(src->src.getCvReviewers().stream().map(jobWiseCvReviewer -> jobWiseCvReviewer.getReviewer()).toList(), CreateJobResponseDto::setReviewers);
//                }
//        );
//    }

}
