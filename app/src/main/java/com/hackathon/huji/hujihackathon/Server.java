package com.hackathon.huji.hujihackathon;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

class Server {
    private static final Server ourInstance = new Server();

    static Server getInstance() {
        return ourInstance;
    }

    private static int id = 0;

    private MutableLiveData<List<Group>> suggestedGroups;

    private Server() {
        // should be initialized by calling remote server
        List<Group> suggested = new ArrayList<>();
        Group avengers = new Group("Avengers", String.valueOf(++id), "hackathon", "huji");
        avengers.addMember(new User("Tal"));
        avengers.addMember(new User("Itay"));
        avengers.addMember(new User("Imri"));
        suggested.add(avengers);

        Group maccabi = new Group("Maccabi SP", String.valueOf(++id), "Basketball", "Sacher_Park");
        maccabi.addMember(new User("Yuval"));
        maccabi.addMember(new User("Itamar"));
        suggested.add(maccabi);

        Group north = new Group("The North Remembers", String.valueOf(++id), "dive", "from:jerusalem", "to:tiberias");
        north.addMember(new User("Assaf"));
        suggested.add(north);

        suggestedGroups = new MutableLiveData<>();
        suggestedGroups.postValue(suggested);
    }

    public LiveData<List<Group>> getSuggestedGroups() {
        return suggestedGroups;
    }

    public void addMember(Group group, User user) {
        group.addMember(user);
    }
}
