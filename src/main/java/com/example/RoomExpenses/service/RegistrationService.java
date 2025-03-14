package com.example.RoomExpenses.service;

import com.example.RoomExpenses.model.RoomFellow;
import com.example.RoomExpenses.repository.RoomFellowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RegistrationService {

    @Autowired
    private RoomFellowRepository roomFellowRepository;

    public List<RoomFellow> getAllRoomFellows() {
        return roomFellowRepository.findAll();
    }
}
