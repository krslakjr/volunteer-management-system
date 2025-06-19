package com.example.userservice; // Ili gdje god se DataInitializer nalazi

import com.example.userservice.models.Role;
import com.example.userservice.models.User;
import com.example.userservice.repository.RoleRepository;
import com.example.userservice.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class DataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    /**
     * Bean koji se izvršava pri pokretanju Spring Boot aplikacije.
     * Koristi se za inicijalizaciju uloga i korisnika u bazi podataka.
     *
     * @param roleRepository     Repozitorij za Role entitet.
     * @param userRepository     Repozitorij za User entitet.
     * @param passwordEncoder    Koder lozinki za sigurno spremanje lozinki.
     * @return CommandLineRunner instanca.
     */
    @Bean
    public CommandLineRunner initData(RoleRepository roleRepository,
                                      UserRepository userRepository,
                                      PasswordEncoder passwordEncoder) {
        return args -> {
            logger.info("Pokreće se inicijalizacija podataka...");

            // Inicijalizacija uloga
            Role volunteerRole = roleRepository.findByRoleName("ROLE_VOLUNTEER")
                .orElseGet(() -> {
                    logger.info("Kreiram ulogu: ROLE_VOLUNTEER");
                    return roleRepository.save(new Role(null, "ROLE_VOLUNTEER"));
                });

            Role organizerRole = roleRepository.findByRoleName("ROLE_ORGANIZER")
                .orElseGet(() -> {
                    logger.info("Kreiram ulogu: ROLE_ORGANIZER");
                    return roleRepository.save(new Role(null, "ROLE_ORGANIZER"));
                });

            Role adminRole = roleRepository.findByRoleName("ROLE_ADMIN")
                .orElseGet(() -> {
                    logger.info("Kreiram ulogu: ROLE_ADMIN");
                    return roleRepository.save(new Role(null, "ROLE_ADMIN"));
                });

            // Inicijalizacija korisnika: testuser
            if (userRepository.findByUsername("testuser").isEmpty()) {
                logger.info("Kreiram korisnika: testuser");

                User user = new User();
                user.setUsername("testuser");
                user.setFirstName("Test");
                user.setLastName("User");
                user.setEmail("testuser@example.com");
                user.setPasswordHash(passwordEncoder.encode("Password123!"));
                user.setProfilePicture("https://placehold.co/100x100/aabbcc/ffffff?text=TU");

                Set<Role> roles = new HashSet<>();
                roles.add(volunteerRole);
                user.setRoles(roles);

                userRepository.save(user);
            }

             if (userRepository.findByUsername("ilhana").isEmpty()) {
                logger.info("Kreiram korisnika: testuser");

                User user = new User();
                user.setUsername("ilhana");
                user.setFirstName("Test");
                user.setLastName("User");
                user.setEmail("ilhana@example.com");
                user.setPasswordHash(passwordEncoder.encode("test"));
                user.setProfilePicture("https://placehold.co/100x100/aabbcc/ffffff?text=TU");

                Set<Role> roles = new HashSet<>();
                roles.add(organizerRole);
                user.setRoles(roles);

                userRepository.save(user);
            }

            // Inicijalizacija korisnika: adminuser
            if (userRepository.findByUsername("adminuser").isEmpty()) {
                logger.info("Kreiram korisnika: adminuser");

                User adminUser = new User();
                adminUser.setUsername("adminuser");
                adminUser.setFirstName("Admin");
                adminUser.setLastName("User");
                adminUser.setEmail("admin@example.com");
                adminUser.setPasswordHash(passwordEncoder.encode("AdminPassword123!"));
                adminUser.setProfilePicture("https://placehold.co/100x100/ccbbaa/ffffff?text=AU");

                Set<Role> roles = new HashSet<>();
                roles.add(adminRole);
                adminUser.setRoles(roles);

                userRepository.save(adminUser);
            }

            logger.info("Inicijalizacija podataka završena.");
        };
    }
}
