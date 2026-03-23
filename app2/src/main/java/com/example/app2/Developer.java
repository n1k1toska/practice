package com.example.app2;

public class Developer {
    private String name;
    private String publisher;
    private String founded;
    private String headquarters;
    private int imageResId;

    public Developer(String name, String publisher, String founded, String headquarters, int imageResId) {
        this.name = name;
        this.publisher = publisher;
        this.founded = founded;
        this.headquarters = headquarters;
        this.imageResId = imageResId;
    }

    public String getName() { return name; }
    public String getPublisher() { return publisher; }
    public String getFounded() { return founded; }
    public String getHeadquarters() { return headquarters; }
    public int getImageResId() { return imageResId; }
}