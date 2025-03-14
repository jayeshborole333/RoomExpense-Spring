package com.example.RoomExpenses.controller;

import com.example.RoomExpenses.model.RoomFellow;
import com.example.RoomExpenses.repository.RoomFellowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/roomfellows")
public class RegistrationController {

    @Autowired
    private RoomFellowRepository roomFellowRepository;

    @PostMapping
    public ResponseEntity<RoomFellow> registerFellow(@RequestBody RoomFellow fellow) {
        RoomFellow savedFellow = roomFellowRepository.save(fellow);
        return ResponseEntity.ok(savedFellow);
    }

    @GetMapping
    public List<RoomFellow> getAllFellows() {
        return roomFellowRepository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomFellow> updateFellow(@PathVariable Long id, @RequestBody RoomFellow fellow) {
        Optional<RoomFellow> existingFellow = roomFellowRepository.findById(id);

        if (existingFellow.isPresent()) {
            RoomFellow updatedFellow = existingFellow.get();

            updatedFellow.setName(fellow.getName());
            updatedFellow.setEmail(fellow.getEmail());
            updatedFellow.setPhone(fellow.getPhone());

            roomFellowRepository.save(updatedFellow);
            return ResponseEntity.ok(updatedFellow);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFellow(@PathVariable Long id) {
        roomFellowRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}