package com.example.RoomExpenses.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "roomexpense")
public class RoomExpense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Double roomRent = 0.0;

    @NotNull
    private Double vegetable = 0.0;

    @JsonProperty("d_mart")
    private Double dMart = 0.0;

    @NotNull
    private Double wifi = 0.0;

    @NotNull
    private Double electricityBill = 0.0;

    @NotNull
    private Double gas = 0.0;

    @NotNull
    private String month;

    private LocalDate date;

    public RoomExpense() {}

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRoomRent() {
        return roomRent;
    }

    public void setRoomRent(Double roomRent) {
        this.roomRent = roomRent;
    }

    public Double getVegetable() {
        return vegetable;
    }

    public void setVegetable(Double vegetable) {
        this.vegetable = vegetable;
    }

    @Column(name = "d_mart")
    public double getDMart() {
        return dMart;
    }

    public void setDMart(double dMart) {
        this.dMart = dMart;
    }

    public Double getWifi() {
        return wifi;
    }

    public void setWifi(Double wifi) {
        this.wifi = wifi;
    }

    public Double getElectricityBill() {
        return electricityBill;
    }

    public void setElectricityBill(Double electricityBill) {
        this.electricityBill = electricityBill;
    }

    public Double getGas() {
        return gas;
    }

    public void setGas(Double gas) {
        this.gas = gas;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getTotalExpenses() {
        return Objects.requireNonNullElse(roomRent, 0.0) +
                Objects.requireNonNullElse(vegetable, 0.0) +
                Objects.requireNonNullElse(dMart, 0.0) +
                Objects.requireNonNullElse(wifi, 0.0) +
                Objects.requireNonNullElse(electricityBill, 0.0) +
                Objects.requireNonNullElse(gas, 0.0);
    }
}
