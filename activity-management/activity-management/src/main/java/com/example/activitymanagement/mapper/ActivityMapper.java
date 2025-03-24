package com.example.activitymanagement.mapper;

import com.example.activitymanagement.dto.ActivityDTO;
import com.example.activitymanagement.models.Activity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ActivityMapper {

    ActivityMapper INSTANCE = Mappers.getMapper(ActivityMapper.class);

    ActivityDTO toActivityDTO(Activity activity);

    @Mapping(target = "activityVolunteers", ignore = true) 
    @Mapping(target = "teamActivities", ignore = true) 
    Activity toActivity(ActivityDTO activityDTO);
}
