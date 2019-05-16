package com.hackathon.huji.hujihackathon;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private String id;
    private MutableLiveData<String> name;
    private MutableLiveData<List<User>> members;

    public Group(String groupName, String id) {
        this.id = id;
        name = new MutableLiveData<>();
        members = new MutableLiveData<>();
        name.setValue(groupName);
        members.setValue(new ArrayList<User>());
    }

    public LiveData<List<User>> getMembers(){
        return members;
    }

    public LiveData<String> getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}