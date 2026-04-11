package com.practice.pet.service;

import com.practice.pet.model.Expense;
import com.practice.pet.repository.ExpenseRepository;
import com.practice.pet.repository.FilterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilterService {

    private final FilterRepository filterRepository;

    public FilterService(FilterRepository filterRepository) {
        this.filterRepository = filterRepository;
    }

    // ============ MAIN: Flexible filtering method ============
    public List<Expense> filterExpenses(String category, String paidTo,
                                        LocalDate startDate, LocalDate endDate,
                                        Double minAmount) {

        return filterRepository.findAll().stream()
                .filter(e -> category == null || e.getCategory().equalsIgnoreCase(category))
                .filter(e -> paidTo == null || (e.getPaidTo() != null && e.getPaidTo().equalsIgnoreCase(paidTo)))
                .filter(e -> startDate == null || !e.getDate().isBefore(startDate))
                .filter(e -> endDate == null || !e.getDate().isAfter(endDate))
                .filter(e -> minAmount == null || e.getAmount() >= minAmount)
                .collect(Collectors.toList());
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

    public List<Expense> findExpensesByMinAmount(Double min, Double max) {
        return filterExpenses(null, null, null, null, min);
    }


    // Filter By days
    public List<Expense> getExpensesToday() {
        return filterRepository.findExpensesToday();
    }

    public List<Expense> getExpensesThisWeek() {
        return filterRepository.findExpensesThisWeek();
    }

    public List<Expense> getExpensesThisMonth() {
        return filterRepository.findExpensesThisMonth();
    }

    public List<Expense> getExpensesThisYear() {
        return filterRepository.findExpensesThisYear();
    }

    public List<Expense> getExpensesLastXDays(int days) {
        return filterRepository.findExpensesBetween(LocalDate.now().minusDays(days), LocalDate.now());
    }
}