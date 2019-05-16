package com.hackathon.huji.hujihackathon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Group {

    private String id;
    private String name;
    private List<User> members;
    private List<String> tags;

    public Group(String groupName, String id, String... tags) {
        this.id = id;
        name = groupName;
        members = new ArrayList<>();
        this.tags = new ArrayList<>(Arrays.asList(tags));
    }

    public void addMember(User user) {
        members.add(user);
    }

    public void addTag(String tag){
        tags.add(tag);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<User> getMembers() {
        return members;
    }

    public List<String> getTags() {
        return tags;
    }


}
