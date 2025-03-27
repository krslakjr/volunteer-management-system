package com.example.activitymanagement.mapper;

import com.example.activitymanagement.dto.ActivityDTO;
import com.example.activitymanagement.models.Activity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
<<<<<<< HEAD
    date = "2025-03-25T19:51:56+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
=======
    date = "2025-03-27T17:19:00+0100",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.14 (OpenLogic)"
>>>>>>> 1f92f07d26c618f4ab802b3c248b0b97d353dacb
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
        activityDTO.setDate( activity.getDate() );
        activityDTO.setLocation( activity.getLocation() );
        activityDTO.setVolunteersNeeded( activity.getVolunteersNeeded() );

        return activityDTO;
    }

    @Override
    public Activity toActivity(ActivityDTO activityDTO) {
        if ( activityDTO == null ) {
            return null;
        }

        Activity activity = new Activity();

        activity.setActivityId( activityDTO.getActivityId() );
        activity.setDescription( activityDTO.getDescription() );
        activity.setDate( activityDTO.getDate() );
        activity.setLocation( activityDTO.getLocation() );
        activity.setVolunteersNeeded( activityDTO.getVolunteersNeeded() );

        return activity;
    }
}
