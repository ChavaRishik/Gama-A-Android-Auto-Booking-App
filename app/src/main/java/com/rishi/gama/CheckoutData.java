package com.rishi.gama;
public class CheckoutData {
    public String name;
    public String mobileNumber;
    public String selectedDate;
    public String selectedTimeSlot;
    public String price;

    public CheckoutData() {
        // Default constructor required for Firebase
    }

    // Constructor to set data
    public CheckoutData(String name, String mobileNumber, String selectedDate, String selectedTimeSlot, String price) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.selectedDate = selectedDate;
        this.selectedTimeSlot = selectedTimeSlot;
        this.price = price;
    }
}
