package com.example.app5;

public class Item {
    int id;
    String name;

    public Item(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Item() {}

    @Override
    public String toString() {
        return "Item ID: " + id + ", Name: " + name;
    }
}
