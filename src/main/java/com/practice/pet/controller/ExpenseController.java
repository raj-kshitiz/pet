package com.practice.pet.controller;

import com.practice.pet.model.Expense;
import com.practice.pet.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ExpenseController {

    private final
    ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/expense")
    public ResponseEntity<List<Expense>> getAllExpenses(){
        List<Expense> expenses = expenseService.getAllExpenses();
        if(expenses.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    @PostMapping("/expense")
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense) {
        Expense addedExpense = expenseService.addExpense(expense);
        return new ResponseEntity<>(addedExpense, HttpStatus.OK);
    }

    @PutMapping("/expense")
    public ResponseEntity<Expense> updateExpense(@RequestBody Expense expense) {
        Expense updatedExpense = expenseService.updateExpense(expense);
        return new ResponseEntity<>(updatedExpense, HttpStatus.OK);
    }
}
