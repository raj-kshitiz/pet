package com.practice.pet.repository;

import com.practice.pet.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FilterRepository extends JpaRepository<Expense, Integer> {

    @Query("SELECT expense FROM Expense expense WHERE expense.date = CURRENT_DATE")
    List<Expense> findExpensesToday();

    // ============ LAST X DAYS ============
        @Query("""
        SELECT e
        FROM Expense e
        WHERE e.date >= :startDate
          AND e.date <= :endDate
        ORDER BY e.date DESC
        """)
        List<Expense> findExpensesBetween(@Param("startDate") LocalDate startDate,
                                          @Param("endDate") LocalDate endDate);

    // ============ THIS WEEK ============
    @Query("SELECT e FROM Expense e " +
            "WHERE DATE_TRUNC('week', e.date) = DATE_TRUNC('week', CURRENT_DATE)")
    List<Expense> findExpensesThisWeek();


    // ============ THIS MONTH ============
    @Query("SELECT e FROM Expense e " +
            "WHERE EXTRACT(YEAR FROM e.date) = EXTRACT(YEAR FROM CURRENT_DATE) " +
            "AND EXTRACT(MONTH FROM e.date) = EXTRACT(MONTH FROM CURRENT_DATE)")
    List<Expense> findExpensesThisMonth();


    // ============ THIS YEAR ============
    @Query("SELECT e FROM Expense e " +
            "WHERE EXTRACT(YEAR FROM e.date) = EXTRACT(YEAR FROM CURRENT_DATE)")
    List<Expense> findExpensesThisYear();


}
