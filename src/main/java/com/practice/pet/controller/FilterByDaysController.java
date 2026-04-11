package com.practice.pet.controller;

import com.practice.pet.model.Expense;
import com.practice.pet.service.FilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/expenses/summary")
public class FilterByDaysController {

    private final FilterService filterService;

    // ============ SINGLE FLEXIBLE ENDPOINT ============

    @GetMapping
    public ResponseEntity<List<Expense>> getSummary(@RequestParam(required = false) String period) {
        List<Expense> expenses;

        if (period == null || period.equalsIgnoreCase("today")) {
            expenses = filterService.getExpensesToday();
        } else if (period.equalsIgnoreCase("week")) {
            expenses = filterService.getExpensesThisWeek();
        } else if (period.equalsIgnoreCase("month")) {
            expenses = filterService.getExpensesThisMonth();
        } else if (period.equalsIgnoreCase("year")) {
            expenses = filterService.getExpensesThisYear();
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    // ============ SPECIFIC ENDPOINTS (for convenience/clarity) ============

    @GetMapping("/today")
    public ResponseEntity<List<Expense>> getToday() {
        return new ResponseEntity<>(filterService.getExpensesToday(), HttpStatus.OK);
    }

    @GetMapping("/this-week")
    public ResponseEntity<List<Expense>> getThisWeek() {
        return new ResponseEntity<>(filterService.getExpensesThisWeek(), HttpStatus.OK);
    }

    @GetMapping("/this-month")
    public ResponseEntity<List<Expense>> getThisMonth() {
        return new ResponseEntity<>(filterService.getExpensesThisMonth(), HttpStatus.OK);
    }

    @GetMapping("/this-year")
    public ResponseEntity<List<Expense>> getThisYear() {
        return new ResponseEntity<>(filterService.getExpensesThisYear(), HttpStatus.OK);
    }

    @GetMapping("/last/{days}")
    public ResponseEntity<List<Expense>> getLastXDays(@PathVariable int days) {
        return new ResponseEntity<>(filterService.getExpensesLastXDays(days), HttpStatus.OK);
    }
}