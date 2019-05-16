package com.hackathon.huji.hujihackathon;

import android.graphics.drawable.Drawable;

public class User {

    private String id;
    private String name;
    private Drawable picture;

    public User(String id, String name, Drawable picture) {
        this.id = id;
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

    public Drawable getPicture() {
        return picture;
    }

    public void setPicture(Drawable picture) {
        this.picture = picture;
    }
}