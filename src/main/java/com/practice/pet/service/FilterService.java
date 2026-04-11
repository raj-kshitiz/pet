package com.practice.pet.service;

import com.practice.pet.model.Expense;
import com.practice.pet.repository.ExpenseRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides filtering and time-based aggregation of expenses.
 * All filtering is pushed to the database via JPA Specifications —
 * no in-memory loading of all rows.
 */
@Service
public class FilterService {

    private final ExpenseRepository expenseRepository;

    public FilterService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    // ============ MAIN: Flexible DB-level filtering ============

    /**
     * Dynamically filters expenses using only the non-null parameters.
     * Each predicate is applied at the SQL level — safe even with large datasets.
     */
    public List<Expense> filterExpenses(String category, String paidTo,
                                        LocalDate startDate, LocalDate endDate,
                                        Double minAmount) {
        Specification<Expense> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (category != null) {
                predicates.add(cb.equal(cb.lower(root.get("category")), category.toLowerCase()));
            }
            if (paidTo != null) {
                predicates.add(cb.equal(cb.lower(root.get("paidTo")), paidTo.toLowerCase()));
            }
            if (startDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("date"), startDate));
            }
            if (endDate != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("date"), endDate));
            }
            if (minAmount != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("amount"), minAmount));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return expenseRepository.findAll(spec);
    }

    // ============ CONVENIENCE: Specific filters ============

    public List<Expense> findExpensesByCategory(String category) {
        return filterExpenses(category, null, null, null, null);
    }

    public List<Expense> findExpensesByPaidTo(String paidTo) {
        return filterExpenses(null, paidTo, null, null, null);
    }

    public List<Expense> findExpensesByDateRange(LocalDate start, LocalDate end) {
        return filterExpenses(null, null, start, end, null);
    }

    public List<Expense> findExpensesByMinAmount(Double min) {
        return filterExpenses(null, null, null, null, min);
    }

    // ============ Time-based convenience methods ============

    public List<Expense> getExpensesToday() {
        return expenseRepository.findExpensesToday();
    }

    public List<Expense> getExpensesThisWeek() {
        return expenseRepository.findExpensesThisWeek();
    }

    public List<Expense> getExpensesThisMonth() {
        return expenseRepository.findExpensesThisMonth();
    }

    public List<Expense> getExpensesThisYear() {
        return expenseRepository.findExpensesThisYear();
    }

    public List<Expense> getExpensesLastXDays(int days) {
        return expenseRepository.findExpensesLastXDays(days);
    }
}