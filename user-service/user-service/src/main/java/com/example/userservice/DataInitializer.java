package com.example.userservice;

import com.example.userservice.models.*;
import com.example.userservice.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ActivityRepository activityRepository;
    private final UserPermissionRepository userPermissionRepository;
    private final SocialShareRepository socialShareRepository;
    private final VolunteerCertificateRepository volunteerCertificateRepository;
    private final PermissionRepository permissionRepository;

    public DataInitializer(UserRepository userRepository, RoleRepository roleRepository,
                           ActivityRepository activityRepository, UserPermissionRepository userPermissionRepository,
                           SocialShareRepository socialShareRepository, VolunteerCertificateRepository volunteerCertificateRepository,
                           PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.activityRepository = activityRepository;
        this.userPermissionRepository = userPermissionRepository;
        this.socialShareRepository = socialShareRepository;
        this.volunteerCertificateRepository = volunteerCertificateRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Role role = new Role();
        role.setRoleName("Admin");
        roleRepository.save(role);

        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPasswordHash("hashedpassword123");
        user.setProfilePicture("profilePicUrl");
        user.setRole(role); 
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        userRepository.save(user);

        Role role2 = new Role();
        role2.setRoleName("Volunteer");
        roleRepository.save(role2);

        User user2 = new User();
        user2.setFirstName("Johnny");
        user2.setLastName("Cash");
        user2.setEmail("johnnycash@example.com");
        user2.setPasswordHash("hashedpassword1223");
        user2.setProfilePicture("profilePicUrl");
        user2.setRole(role2);
        user2.setCreatedAt(new Date());
        user2.setUpdatedAt(new Date());
        userRepository.save(user2);

        Activity activity = new Activity();
        activity.setActivityName("Charity Event");
        activity.setActivityDate(new Date());
        activity.setDescription("A charity event for helping the local community.");
        activity.setOrganizer(user);
        activity.setCreatedAt(new Date());
        activity.setUpdatedAt(new Date());
        activityRepository.save(activity);

        Permission perm1 = new Permission();
        perm1.setPermissionName("READ_USERS");
        perm1.setCreatedAt(new Date());
        perm1.setUpdatedAt(new Date());
        
        Permission perm2 = new Permission();
        perm2.setPermissionName("EDIT_EVENTS");
        perm2.setCreatedAt(new Date());
        perm2.setUpdatedAt(new Date());
        
        permissionRepository.saveAll(List.of(perm1, perm2));

        UserPermission userPermission1 = new UserPermission();
        userPermission1.setUser(user);
        userPermission1.setPermission(perm1);
        userPermission1.setCreatedAt(new Date());
        userPermission1.setUpdatedAt(new Date());
        
        UserPermission userPermission2 = new UserPermission();
        userPermission2.setUser(user);
        userPermission2.setPermission(perm2);
        userPermission2.setCreatedAt(new Date());
        userPermission2.setUpdatedAt(new Date());
        
        userPermissionRepository.saveAll(List.of(userPermission1, userPermission2));

        user.getActivities().add(activity);
        userRepository.save(user);

        SocialShare socialShare = new SocialShare();
        socialShare.setUser(user);
        socialShare.setActivity(activity);
        socialShare.setPlatform("Facebook");
        socialShare.setSharedAt(new Date());
        socialShare.setCreatedAt(new Date());
        socialShare.setUpdatedAt(new Date());
        socialShareRepository.save(socialShare);

        VolunteerCertificate volunteerCertificate = new VolunteerCertificate();
        volunteerCertificate.setUser(user);
        volunteerCertificate.setActivity(activity);
        volunteerCertificate.setCertificateDate(new Date());
        volunteerCertificate.setCertificatePdfLink("https://www.africau.edu/images/default/sample.pdf");
        volunteerCertificate.setIssuedAt(new Date());
        volunteerCertificate.setCreatedAt(new Date());
        volunteerCertificate.setUpdatedAt(new Date());
        volunteerCertificateRepository.save(volunteerCertificate);

        user.getSocialShares().add(socialShare);
        user.getVolunteerCertificates().add(volunteerCertificate);
        userRepository.save(user);
    }
}