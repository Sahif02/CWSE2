package com.example.reservation;

import java.io.Serializable;

public class Reservation implements Serializable {
    private String customerName;
    private String customerPhoneNumber;
    private String meal;
    private String seatingArea;
    private int tableSize;
    private String date;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public String getSeatingArea() {
        return seatingArea;
    }

    public void setSeatingArea(String seatingArea) {
        this.seatingArea = seatingArea;
    }

    public int getTableSize() {
        return tableSize;
    }

    public void setTableSize(int tableSize) {
        this.tableSize = tableSize;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}

