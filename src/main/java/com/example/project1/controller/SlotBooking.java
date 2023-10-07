package com.example.project1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import com.example.project1.entities.Slot;
import com.example.project1.entities.SlotBooked;
import com.example.project1.services.SlotServices;

@Controller
@PreAuthorize("hasAnyRole('USER', 'ADMIN', 'Manager')")
@RequestMapping("/slots")
public class SlotBooking {

    @Autowired
    private SlotServices slotServices;

    // method will show the slot booking page
    @GetMapping("/")
    public String page(Authentication authentication, Model model) {

        String LoginUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        List<SlotBooked> slotsallotted = slotServices.detailsOfSlotBooked(LoginUserName);
        model.addAttribute("slotsallotted", slotsallotted);
        List<Slot> slot = slotServices.findAllSlot();
        
        model.addAttribute("slot", slot);
        return "slotBooking";
    }

    // method to handle the slot booking
    @GetMapping("/booking/{id}")
    public String BookYourSlot(Authentication authentication, @PathVariable Long id,
            @RequestParam("number") Integer number, Model model) {

        String LoginUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        Slot slot = slotServices.findById(id);

        if (slot != null && slot.getAvailableSlot() >= number) {
            slot.setAvailableSlot(slot.getAvailableSlot() - number);
                slotServices.saveSlot(slot);
            SlotBooked slotBooked = new SlotBooked();
            slotBooked.setCustomerEmail(LoginUserName);
            slotBooked.setNoOfSlot(number);
            slotBooked.setSlot(slot);
            slotServices.saveSlotBooked(slotBooked);
            model.addAttribute("slot", slot);
            return "redirect:/slots/";
        } else {
            model.addAttribute("SlotNotFound", id);
            return "redirect:/slots/?SlotNotFound";
        }

    }

    // method to show the addSlot page
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/addSlot")
    public String addSlot() {
        return "slotAdd";
    }

    // method to handle the add slot submission
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping("/addSlot/save")
    public String addSlots(@ModelAttribute("slot") Slot slot) {
        System.out.println("Sunil" +slot);
            slotServices.saveSlot(slot);
        return "redirect:/slots/addSlot?successfullyAdded";
    }

}
