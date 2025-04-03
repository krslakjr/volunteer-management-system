package com.example.feedbackservice.repository;

import com.example.feedbackservice.models.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    Page<Activity> findAll(Pageable pageable);

    
     List<Activity> findByDescriptionContaining(String description);

 
     List<Activity> findByDateAfter(Date date);
 

     List<Activity> findByLocationContaining(String location);
 
  
     List<Activity> findByVolunteersNeededGreaterThanEqual(int volunteersNeeded);
 
 
     List<Activity> findByDescriptionContainingAndLocationContaining(String description, String location);
 
}
