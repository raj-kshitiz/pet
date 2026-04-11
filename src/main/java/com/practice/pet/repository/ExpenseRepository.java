package com.practice.pet.repository;

import com.practice.pet.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    // ============ TODAY ============
    @Query(value = "SELECT * FROM expense e WHERE e.date = CURRENT_DATE", nativeQuery = true)
    List<Expense> findExpensesToday();

    // ============ THIS MONTH ============
    @Query(value = """
            SELECT * FROM expense e
            WHERE EXTRACT(YEAR FROM e.date) = EXTRACT(YEAR FROM CURRENT_DATE)
              AND EXTRACT(MONTH FROM e.date) = EXTRACT(MONTH FROM CURRENT_DATE)
            """, nativeQuery = true)
    List<Expense> findExpensesThisMonth();

    // ============ THIS YEAR ============
    @Query(value = """
            SELECT * FROM expense e
            WHERE EXTRACT(YEAR FROM e.date) = EXTRACT(YEAR FROM CURRENT_DATE)
            """, nativeQuery = true)
    List<Expense> findExpensesThisYear();

    // ============ LAST X DAYS ============
    @Query(value = """
            SELECT * FROM expense e
            WHERE e.date >= CURRENT_DATE - (:days * INTERVAL '1 day')
              AND e.date <= CURRENT_DATE
            ORDER BY e.date DESC
            """, nativeQuery = true)
    List<Expense> findExpensesLastXDays(@Param("days") int days);

    // ============ BONUS QUERIES ============

    // Find by specific date
    List<Expense> findByDate(LocalDate date);

    // Find by category
    List<Expense> findByCategory(String category);

    // Find by date range
    @Query("SELECT e FROM Expense e WHERE e.date BETWEEN :startDate AND :endDate ORDER BY e.date DESC")
    List<Expense> findByDateRange(@Param("startDate") LocalDate startDate,
                                  @Param("endDate") LocalDate endDate);

    // Find last 7 days
    @Query(value = """
            SELECT * FROM expense e
            WHERE e.date >= CURRENT_DATE - INTERVAL '7 days'
              AND e.date <= CURRENT_DATE
            ORDER BY e.date DESC
            """, nativeQuery = true)
    List<Expense> findLast7Days();

    // Find last 30 days
    @Query(value = """
            SELECT * FROM expense e
            WHERE e.date >= CURRENT_DATE - INTERVAL '30 days'
              AND e.date <= CURRENT_DATE
            ORDER BY e.date DESC
            """, nativeQuery = true)
    List<Expense> findLast30Days();
}