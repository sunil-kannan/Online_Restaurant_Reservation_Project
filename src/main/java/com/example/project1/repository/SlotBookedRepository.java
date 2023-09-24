package com.example.project1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.project1.entities.SlotBooked;

public interface SlotBookedRepository extends JpaRepository<SlotBooked, Long>{
     @Query(value = "SELECT p.id, p.slot_id, p.no_of_slot, p.customer_email FROM slot_booked p join slot s  WHERE p.slot_id = s.id and p.customer_email = :email and s.date>=:date order by s.date ASC", nativeQuery = true)
     List<SlotBooked> getSlotBookedByCustomerEmail(String email, String date);
}
