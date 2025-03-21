package com.example.userservice;

import com.example.userservice.models.*;
import com.example.userservice.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ActivityRepository activityRepository;
    private final UserPermissionRepository userPermissionRepository;
    private final SocialShareRepository socialShareRepository;
    private final VolunteerCertificateRepository volunteerCertificateRepository;

    public DataInitializer(UserRepository userRepository, RoleRepository roleRepository,
                           ActivityRepository activityRepository, UserPermissionRepository userPermissionRepository,
                           SocialShareRepository socialShareRepository, VolunteerCertificateRepository volunteerCertificateRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.activityRepository = activityRepository;
        this.userPermissionRepository = userPermissionRepository;
        this.socialShareRepository = socialShareRepository;
        this.volunteerCertificateRepository = volunteerCertificateRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Kreiranje Role
        Role role = new Role();
        role.setRoleName("Admin");
        roleRepository.save(role);

        // Kreiranje User
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPasswordHash("hashedpassword123");
        user.setProfilePicture("profilePicUrl");
        user.setRole(role);  // Postavljanje role
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        userRepository.save(user);

        // Kreiranje Activity
        Activity activity = new Activity();
        activity.setActivityName("Charity Event");
        activity.setActivityDate(new Date());
        activity.setDescription("A charity event for helping the local community.");
        activity.setOrganizer(user);
        activity.setCreatedAt(new Date());
        activity.setUpdatedAt(new Date());
        activityRepository.save(activity);

        // Kreiranje UserPermissions
        UserPermission userPermission = new UserPermission();
        userPermission.setUser(user);
        userPermission.setCreatedAt(new Date());
        userPermission.setUpdatedAt(new Date());
        userPermissionRepository.save(userPermission);

        // Dodavanje u aktivnosti korisnika
        user.getActivities().add(activity);
        userRepository.save(user);

        // Kreiranje SocialShare za korisnika i aktivnost
        SocialShare socialShare = new SocialShare();
        socialShare.setUser(user);
        socialShare.setActivity(activity);
        socialShare.setPlatform("Facebook");
        socialShare.setSharedAt(new Date());
        socialShare.setCreatedAt(new Date());
        socialShare.setUpdatedAt(new Date());
        socialShareRepository.save(socialShare);

        // Kreiranje VolunteerCertificate
        VolunteerCertificate volunteerCertificate = new VolunteerCertificate();
        volunteerCertificate.setUser(user);
        volunteerCertificate.setActivity(activity);
        volunteerCertificate.setCertificateDate(new Date());
        volunteerCertificate.setCreatedAt(new Date());
        volunteerCertificate.setUpdatedAt(new Date());
        volunteerCertificateRepository.save(volunteerCertificate);

        // Dodavanje SocialShares i VolunteerCertificates u User
        user.getSocialShares().add(socialShare);
        user.getVolunteerCertificates().add(volunteerCertificate);
        userRepository.save(user);
    }
}
