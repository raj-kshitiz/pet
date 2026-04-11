package com.practice.pet.controller;

import com.practice.pet.model.Expense;
import com.practice.pet.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
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

    @DeleteMapping("/expense")
    public ResponseEntity<Expense> deleteExpense(@RequestBody Expense expense) {
        Expense deletedExpense = expenseService.deleteExpense(expense);
        return new ResponseEntity<>(deletedExpense, HttpStatus.OK);
    }

    @DeleteMapping("/expense/all")
    public ResponseEntity<String> deleteAllExpenses() {
        expenseService.deleteAllExpenses();
        return new ResponseEntity<>("All Records Deleted!",HttpStatus.OK);
    }
}
