package com.example.lab_3;

public class Order {
    private int id;
    private String flower;
    private String color;
    private String price;

    public Order() {}

    public Order(String flower, String color, String price) {
        this.flower = flower;
        this.color = color;
        this.price = price;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getFlower() { return flower; }
    public void setFlower(String flower) { this.flower = flower; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    @Override
    public String toString() {
        return "ID: " + id + "\nКвітка: " + flower + "\nКолір: " + color + "\nЦіна: " + price;
    }
}