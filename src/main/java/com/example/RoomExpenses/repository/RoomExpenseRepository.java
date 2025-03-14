package com.example.RoomExpenses.repository;

import com.example.RoomExpenses.model.RoomExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomExpenseRepository extends JpaRepository<RoomExpense, Long> {

    /**
     * Custom Query to Fetch Expenses by Date Range
     * @param startDate - First date of the month
     * @param endDate - Last date of the month
     * @return List of RoomExpense objects within the specified date range
     */
    @Query("SELECT e FROM RoomExpense e WHERE e.date BETWEEN :startDate AND :endDate")
    List<RoomExpense> findByDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
