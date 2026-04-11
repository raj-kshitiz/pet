package com.practice.pet.controller;

import com.practice.pet.model.Expense;
import com.practice.pet.service.FilterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/expenses/summary")
public class FilterByDaysController {

    private final FilterService filterService;
    private List<Expense> expenseList;
    // ============ SINGLE FLEXIBLE ENDPOINT ============
    @GetMapping
    public ResponseEntity<List<Expense>> getSummary(
            @RequestParam(required = false) String period) {

        if (period == null || period.equalsIgnoreCase("today")) {
            expenseList = filterService.getExpensesToday();
        } else if (period.equalsIgnoreCase("week")) {
            expenseList = filterService.getExpensesThisWeek();
        } else if (period.equalsIgnoreCase("month")) {
            expenseList = filterService.getExpensesThisMonth();
        } else if (period.equalsIgnoreCase("year")) {
            expenseList = filterService.getExpensesThisYear();
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(expenseList, HttpStatus.OK);
    }

    // ============ SPECIFIC ENDPOINTS (for convenience/clarity) ============

    @GetMapping("/today")
    public ResponseEntity<List<Expense>> getToday() {
        expenseList = filterService.getExpensesToday();
        return new ResponseEntity<>(expenseList, HttpStatus.OK);
    }

    @GetMapping("/this-week")
    public ResponseEntity<List<Expense>> getThisWeek() {
        expenseList = filterService.getExpensesThisWeek();
        return new ResponseEntity<>(expenseList, HttpStatus.OK);
    }

    @GetMapping("/this-month")
    public ResponseEntity<List<Expense>> getThisMonth() {
        expenseList = filterService.getExpensesThisMonth();
        return new ResponseEntity<>(expenseList, HttpStatus.OK);
    }

    @GetMapping("/this-year")
    public ResponseEntity<List<Expense>> getThisYear() {
        expenseList = filterService.getExpensesThisYear();
        return new ResponseEntity<>(expenseList, HttpStatus.OK);
    }

    @GetMapping("/last/{days}")
    public ResponseEntity<List<Expense>> getLastXDays(@PathVariable int days) {
        expenseList = filterService.getExpensesLastXDays(days);
        return new ResponseEntity<>(expenseList, HttpStatus.OK);
    }
}