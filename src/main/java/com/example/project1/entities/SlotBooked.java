package com.example.project1.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
public class SlotBooked {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Slot slot;

    private Integer noOfSlot;

    private String customerEmail;
}
