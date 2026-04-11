package com.practice.pet.controller;

import com.practice.pet.model.Expense;
import com.practice.pet.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/expense")
    public ResponseEntity<List<Expense>> getAllExpenses() {
        List<Expense> expenses = expenseService.getAllExpenses();
        if (expenses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    @PostMapping("/expense")
    public ResponseEntity<Expense> addExpense(@Valid @RequestBody Expense expense) {
        return new ResponseEntity<>(expenseService.addExpense(expense), HttpStatus.CREATED);
    }

    @PutMapping("/expense")
    public ResponseEntity<Expense> updateExpense(@Valid @RequestBody Expense expense) {
        return new ResponseEntity<>(expenseService.updateExpense(expense), HttpStatus.OK);
    }

    /**
     * Deletes a single expense by its ID.
     * Uses a path variable per REST convention; a body on DELETE is non-standard
     * and unsupported by some HTTP clients and proxies.
     */
    @DeleteMapping("/expense/{id}")
    public ResponseEntity<Expense> deleteExpense(@PathVariable Integer id) {
        return new ResponseEntity<>(expenseService.deleteExpense(id), HttpStatus.OK);
    }

    @DeleteMapping("/expense/all")
    public ResponseEntity<String> deleteAllExpenses() {
        expenseService.deleteAllExpenses();
        return new ResponseEntity<>("All records deleted.", HttpStatus.OK);
    }
}
