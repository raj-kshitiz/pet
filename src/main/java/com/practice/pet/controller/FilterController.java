package com.practice.pet.controller;

import com.practice.pet.model.Expense;
import com.practice.pet.service.FilterService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/filter")
public class FilterController {

    private final FilterService filterService;

    @GetMapping("/category")
    public ResponseEntity<List<Expense>> findExpensesByCategory(@RequestParam String category) {
        List<Expense> expenses = filterService.findExpensesByCategory(category);
        if(expenses.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new  ResponseEntity<>(expenses, HttpStatus.OK);
    }

    @GetMapping("/paidTo")
    public ResponseEntity<List<Expense>> findExpensesByPaidTo(@RequestParam String paidTo) {
        List<Expense> expenses = filterService.findExpensesByPaidTo(paidTo);
        if(expenses.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new  ResponseEntity<>(expenses, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Expense>> findExpenses(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String paidTo,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false, defaultValue = "0") Double minAmount
    ) {
        return new ResponseEntity<>(filterService.filterExpenses(category, paidTo, startDate, endDate, minAmount), HttpStatus.OK);
    }


}
