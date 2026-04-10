package com.practice.pet.service;

import com.practice.pet.model.Expense;
import com.practice.pet.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    private final
    ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense addExpense(Expense expense) {
        expenseRepository.save(expense);
        return expense;
    }

    public Expense updateExpense(Expense expense) {
        expenseRepository.save(expense);
        return expense;
    }
}
