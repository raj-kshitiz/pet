package com.practice.pet.repository;

import com.practice.pet.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Single repository for all Expense persistence operations.
 * Extends {@link JpaSpecificationExecutor} to support dynamic, DB-level filtering
 * via {@code Specification<Expense>} in {@code FilterService} — no in-memory streaming.
 */
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer>,
        JpaSpecificationExecutor<Expense> {

    // ============ TODAY ============
    @Query(value = "SELECT * FROM expense e WHERE e.date = CURRENT_DATE", nativeQuery = true)
    List<Expense> findExpensesToday();

    // ============ THIS WEEK ============
    @Query("SELECT e FROM Expense e WHERE DATE_TRUNC('week', e.date) = DATE_TRUNC('week', CURRENT_DATE)")
    List<Expense> findExpensesThisWeek();

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

    // ============ DATE RANGE ============
    @Query("SELECT e FROM Expense e WHERE e.date BETWEEN :startDate AND :endDate ORDER BY e.date DESC")
    List<Expense> findByDateRange(@Param("startDate") LocalDate startDate,
                                  @Param("endDate") LocalDate endDate);

    // ============ LAST X DAYS ============
    @Query(value = """
            SELECT * FROM expense e
            WHERE e.date >= CURRENT_DATE - (:days * INTERVAL '1 day')
              AND e.date <= CURRENT_DATE
            ORDER BY e.date DESC
            """, nativeQuery = true)
    List<Expense> findExpensesLastXDays(@Param("days") int days);
}