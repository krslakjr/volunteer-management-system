package com.example.activitymanagement.mapper;

import com.example.activitymanagement.dto.ActivityVolunteerDTO;
import com.example.activitymanagement.models.ActivityVolunteer;
import com.example.activitymanagement.models.Activity;
import com.example.activitymanagement.models.Volunteer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ActivityVolunteerMapper {

    @Mapping(source = "activity", target = "activityId", qualifiedByName = "mapActivityToId")
    @Mapping(source = "volunteer", target = "volunteerId", qualifiedByName = "mapVolunteerToId")
    ActivityVolunteerDTO toDTO(ActivityVolunteer activityVolunteer);

    @Mapping(source = "activityId", target = "activity", ignore = true)
    @Mapping(source = "volunteerId", target = "volunteer", ignore = true)
    ActivityVolunteer toEntity(ActivityVolunteerDTO activityVolunteerDTO);

    @Named("mapActivityToId")
    static Long mapActivityToId(Activity activity) {
        return activity != null ? activity.getActivityId() : null;
    }

    @Named("mapVolunteerToId")
    static Long mapVolunteerToId(Volunteer volunteer) {
        return volunteer != null ? volunteer.getVolunteerId() : null;
    }
}
