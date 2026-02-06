package com.hrms.backend.config;

import com.hrms.backend.dtos.responseDtos.DeletePostResponseDto;
import com.hrms.backend.dtos.responseDtos.PostResponseDto;
import com.hrms.backend.entities.Post;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        addPostResponseMapping(modelMapper);
        addDeletedPostResponseDtoMapping(modelMapper);
        return modelMapper;
    }

    public void addPostResponseMapping(ModelMapper modelMapper){
        modelMapper.createTypeMap(Post.class, PostResponseDto.class)
                .addMappings(mapper->{
                    mapper.map(src -> src.getCreatedBy().getFullName(),PostResponseDto::setCreatedBy);
                });
    }

    public void addDeletedPostResponseDtoMapping(ModelMapper modelMapper){
        modelMapper.createTypeMap(Post.class, DeletePostResponseDto.class)
                .addMappings(mapper->{
                    mapper.map(src->src.getCreatedBy().getFullName(),DeletePostResponseDto::setCreatedBy);
                    mapper.map(src->src.getDeletedBy().getFullName(),DeletePostResponseDto::setDeletedBy);
                });
    }
}
