package com.hackathon.huji.hujihackathon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Group {

    private String id;
    private String name;
    private String image;
    private List<User> members;
    private List<String> tags;

    public Group(String groupName, String id, String imageUrl, String... tags) {
        this.id = id;
        name = groupName;
        members = new ArrayList<>();
        image = imageUrl;
        this.tags = new ArrayList<>(Arrays.asList(tags));
    }

    public void addMember(User user) {
        members.add(user);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public String getImage() {
        return image;
    }

    public List<User> getMembers() {
        return members;
    }

    public List<String> getTags() {
        return tags;
    }
}
