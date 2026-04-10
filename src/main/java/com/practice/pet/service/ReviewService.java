package com.practice.pet.service;

import com.practice.pet.model.Expense;
import com.practice.pet.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public String getAmountSpentToday() {
        List<Expense> expenses = reviewRepository.findExpensesToday();
        if(expenses.isEmpty()){
            return "No expenses found for today";
        }
        return expenses.stream()
                .map(Expense::getAmount)
                .reduce(0.0, Double::sum)
                .toString();
    }

    public String getAmountSpentThisWeek() {
        List<Expense> expenses = reviewRepository.findExpensesThisWeek();
        if(expenses.isEmpty()){
            return "No expenses found for this week";
        }
        return expenses.stream()
                .map(Expense::getAmount)
                .reduce(0.0, Double::sum)
                .toString();
    }

    public String getAmountSpentThisMonth() {
        List<Expense> expenses = reviewRepository.findExpensesThisMonth();
        if(expenses.isEmpty()){
            return "No expenses found for this month";
        }
        return expenses.stream()
                .map(Expense::getAmount)
                .reduce(0.0, Double::sum)
                .toString();
    }

    public String getAmountSpentThisYear() {
        List<Expense> expenses = reviewRepository.findExpensesThisYear();
        if(expenses.isEmpty()){
            return "No expenses found for this year";
        }
        return expenses.stream()
                .map(Expense::getAmount)
                .reduce(0.0, Double::sum)
                .toString();
    }

    public String getAmountSpentLastXDays(int days) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(days);
        List<Expense> expenses = reviewRepository.findExpensesBetween(startDate, endDate);
        if(expenses.isEmpty()){
            return "No expenses found for the last "+ days +" days";
        }
        return expenses.stream()
                .map(Expense::getAmount)
                .reduce(0.0, Double::sum)
                .toString();
    }
}
