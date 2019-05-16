package com.hackathon.huji.hujihackathon;

public class User {

    private static int ids = 0;

    private String id;
    private String name;
    private String picture;

    public User(String name, String picture) {
        this.id = String.valueOf(++ids);
        this.name = name;
        this.picture = picture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
