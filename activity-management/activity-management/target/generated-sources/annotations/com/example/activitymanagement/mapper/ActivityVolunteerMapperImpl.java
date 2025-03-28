package com.example.activitymanagement.mapper;

import com.example.activitymanagement.dto.ActivityVolunteerDTO;
import com.example.activitymanagement.models.ActivityVolunteer;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-28T14:35:00+0100",
    comments = "version: 1.5.2.Final, compiler: Eclipse JDT (IDE) 3.42.0.v20250325-2231, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class ActivityVolunteerMapperImpl implements ActivityVolunteerMapper {

    @Override
    public ActivityVolunteerDTO toDTO(ActivityVolunteer activityVolunteer) {
        if ( activityVolunteer == null ) {
            return null;
        }

        ActivityVolunteerDTO activityVolunteerDTO = new ActivityVolunteerDTO();

        activityVolunteerDTO.setActivityId( ActivityVolunteerMapper.mapActivityToId( activityVolunteer.getActivity() ) );
        activityVolunteerDTO.setVolunteerId( ActivityVolunteerMapper.mapVolunteerToId( activityVolunteer.getVolunteer() ) );
        activityVolunteerDTO.setId( activityVolunteer.getId() );

        return activityVolunteerDTO;
    }

    @Override
    public ActivityVolunteer toEntity(ActivityVolunteerDTO activityVolunteerDTO) {
        if ( activityVolunteerDTO == null ) {
            return null;
        }

        ActivityVolunteer activityVolunteer = new ActivityVolunteer();

        activityVolunteer.setId( activityVolunteerDTO.getId() );

        return activityVolunteer;
    }
}
