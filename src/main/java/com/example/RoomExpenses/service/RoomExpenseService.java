package com.example.RoomExpenses.service;

import com.example.RoomExpenses.model.RoomExpense;
import com.example.RoomExpenses.repository.RoomExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class RoomExpenseService {

    @Autowired
    private RoomExpenseRepository roomExpenseRepository;

    public List<RoomExpense> getExpensesByMonth(String month) {
        try {
            // Convert month string (YYYY-MM) to LocalDate range
            YearMonth yearMonth = YearMonth.parse(month);
            LocalDate startDate = yearMonth.atDay(1);
            LocalDate endDate = yearMonth.atEndOfMonth();

            // Fetch expenses using the correct repository method
            List<RoomExpense> expenses = roomExpenseRepository.findByDateBetween(startDate, endDate);

            // 🔹 Debugging log to check if data exists
            System.out.println("Service: Retrieved expenses for month " + month + ": " + expenses);

            return expenses != null ? expenses : List.of(); // ✅ Ensure an empty list is returned
        } catch (Exception e) {
            System.err.println("❌ Error fetching expenses: " + e.getMessage());
            throw new RuntimeException("Error fetching expenses: " + e.getMessage());
        }
    }
}
