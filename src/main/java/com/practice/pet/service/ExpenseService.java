package com.practice.pet.service;

import com.practice.pet.model.Expense;
import com.practice.pet.repository.ExpenseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    /** Returns all expenses sorted newest-first. */
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));
    }

    public Expense addExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public Expense updateExpense(Expense expense) {
        // Ensure the record exists before updating to surface a clear 404 rather than silently inserting.
        if (!expenseRepository.existsById(expense.getId())) {
            throw new EntityNotFoundException("Expense not found with id: " + expense.getId());
        }
        return expenseRepository.save(expense);
    }

    /**
     * Deletes an expense by its ID.
     *
     * @param id the ID of the expense to delete
     * @return the deleted expense
     * @throws EntityNotFoundException if no expense exists with the given ID
     */
    public Expense deleteExpense(Integer id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Expense not found with id: " + id));
        expenseRepository.delete(expense);
        return expense;
    }

    public void deleteAllExpenses() {
        expenseRepository.deleteAll();
    }
}
