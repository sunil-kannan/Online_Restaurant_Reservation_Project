package com.example.project1.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.project1.entities.Slot;

public interface SlotRepository extends JpaRepository<Slot, Long>{

     @Query(value = "SELECT p.id, p.date, p.available_slot FROM slot p WHERE p.date>=:date order by p.date ASC", nativeQuery = true)
     List<Slot> getSlotByTime(String date);
    
    Slot getSlotById(Long id);
}
