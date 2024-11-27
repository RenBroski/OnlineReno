package com.example.onlinereno;

public class OrderHistory {
    private int id;
    private String fruitName;
    private double pricePerKg;
    private double quantity;
    private double totalPrice;

    // Constructor
    public OrderHistory(int id, String fruitName, double pricePerKg, double quantity, double totalPrice) {
        this.id = id;
        this.fruitName = fruitName;
        this.pricePerKg = pricePerKg;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getFruitName() {
        return fruitName;
    }

    public double getPricePerKg() {
        return pricePerKg;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
