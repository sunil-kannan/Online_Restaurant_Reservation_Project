package com.example.project1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import com.example.project1.entities.Slot;
import com.example.project1.entities.SlotBooked;
import com.example.project1.repository.*;

@Service
public class SlotServices {

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private SlotBookedRepository slotBookedRepository;

    public List<Slot> findAllSlot() {
        //this will compare the current date with slot available date and will display only future slot
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = now.format(format);
        List<Slot> slot = slotRepository.getSlotByTime(formatDateTime);
        return slot;
    }
   
    public Slot findById(Long id) {
        return slotRepository.getSlotById(id);
    }

    public Slot saveSlot(Slot slot){
        return slotRepository.save(slot);
    }

    public SlotBooked saveSlotBooked(SlotBooked slotBooked) {
        return slotBookedRepository.save(slotBooked);
    }

    public List<SlotBooked> detailsOfSlotBooked(String email) {
        //this will compare the current date with slot booked date
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = now.format(format);
        List<SlotBooked> slot = slotBookedRepository.getSlotBookedByCustomerEmail(email, formatDateTime);

        return slot;
    }



    
}
