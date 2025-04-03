package com.example.RoomExpenses.controller;

import com.example.RoomExpenses.model.RoomExpense;
import com.example.RoomExpenses.repository.RoomExpenseRepository;
import com.example.RoomExpenses.repository.RoomFellowRepository;  // Import the repository for room fellows
import com.example.RoomExpenses.service.EmailService;
import com.example.RoomExpenses.service.RoomExpenseService;
import com.example.RoomExpenses.model.RoomFellow;  // Import the RoomFellow model

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/expenses")
public class RoomExpenseController {

    @Autowired
    private RoomExpenseRepository roomExpenseRepository;

    @Autowired
    private RoomExpenseService roomExpenseService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RoomFellowRepository roomFellowRepository;  // Repository to fetch room fellows' emails

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

    // ✅ Send Invoice Email to Room Fellows
    @PostMapping("/sendInvoiceEmail")
    public ResponseEntity<String> sendInvoiceEmail(
            @RequestParam("invoice") MultipartFile invoice
    ) {
        try {
            // Save the invoice temporarily
            File tempFile = new File("Room_Expense_Invoice.pdf");
            invoice.transferTo(tempFile);

            // Fetch all room fellows' emails (assuming you have a RoomFellow model with email field)
            List<String> roomFellowsEmails = roomFellowRepository.findAll().stream()
                    .map(RoomFellow::getEmail )
                    .collect(Collectors.toList());

            // Convert room fellows' emails to array
            String[] emailList = roomFellowsEmails.toArray(new String[0]);

            // Send the email with the attachment to room fellows
            emailService.sendEmailWithAttachment(emailList, tempFile);

            // Clean up the temporary file
            tempFile.delete();

            return ResponseEntity.ok("Invoice sent successfully!");
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to send invoice email.");
        }
    }
}
