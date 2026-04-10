package com.practice.pet.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "expense_seq")
    @SequenceGenerator(name = "expense_seq", sequenceName = "expense_id_seq", allocationSize = 1)
    private Integer id;

    @Nullable
    private String paidTo;

    @Nullable
    private String itemBought;

    private LocalDate date;

    private double amount;

    @Nullable
    private String description;

    private String category;
}
