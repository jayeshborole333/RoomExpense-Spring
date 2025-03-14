package com.example.RoomExpenses.controller;

import com.example.RoomExpenses.model.RoomExpense;
import com.example.RoomExpenses.repository.RoomExpenseRepository;
import com.example.RoomExpenses.service.RoomExpenseService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Collections;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/expenses")
public class RoomExpenseController {

    @Autowired
    private RoomExpenseRepository roomExpenseRepository;

    @Autowired
    private RoomExpenseService roomExpenseService;

    // ✅ Get all expenses
    @GetMapping("/all")
    public ResponseEntity<List<RoomExpense>> getAllExpenses() {
        List<RoomExpense> expenses = roomExpenseRepository.findAll();
        return ResponseEntity.ok(expenses);
    }

    // ✅ Fetch Expenses by Month
    @GetMapping("/month")
    public ResponseEntity<List<RoomExpense>> getExpensesByMonth(@RequestParam String month) {
        try {
            List<RoomExpense> expenses = roomExpenseService.getExpensesByMonth(month);
            return ResponseEntity.ok(expenses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }

    // ✅ Add New Expense
    @PostMapping
    public ResponseEntity<RoomExpense> addExpense(@Valid @RequestBody RoomExpense expense) {
        RoomExpense savedExpense = roomExpenseRepository.save(expense);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedExpense);
    }

    // ✅ Fetch Expense by ID
    @GetMapping("/{id}")
    public ResponseEntity<RoomExpense> getExpenseById(@PathVariable Long id) {
        Optional<RoomExpense> expenseOptional = roomExpenseRepository.findById(id);
        return expenseOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Delete Expense by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteExpense(@PathVariable Long id) {
        System.out.println("Deleting expense with ID: " + id);  // Debugging log

        Optional<RoomExpense> expense = roomExpenseRepository.findById(id);
        if (expense.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", "Expense not found"));
        }

        roomExpenseRepository.deleteById(id);
        return ResponseEntity.ok(Collections.singletonMap("message", "Deleted successfully"));
    }
}
