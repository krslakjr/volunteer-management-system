package com.example.userservice.mapper;

import com.example.userservice.dto.ActivityDTO;
import com.example.userservice.models.Activity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-29T21:09:03+0100",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.4.1 (Oracle Corporation)"
)
@Component
public class ActivityMapperImpl implements ActivityMapper {

    @Override
    public ActivityDTO toActivityDTO(Activity activity) {
        if ( activity == null ) {
            return null;
        }

        ActivityDTO activityDTO = new ActivityDTO();

        activityDTO.setActivityId( activity.getActivityId() );
        activityDTO.setDescription( activity.getDescription() );

        return activityDTO;
    }

    @Override
    public Activity toActivity(ActivityDTO activityDTO) {
        if ( activityDTO == null ) {
            return null;
        }

        Activity activity = new Activity();

        activity.setDescription( activityDTO.getDescription() );

        return activity;
    }
}
