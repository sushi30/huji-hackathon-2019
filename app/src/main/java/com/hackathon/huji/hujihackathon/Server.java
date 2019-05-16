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
        suggested.add(new Group("הנוקמים", String.valueOf(++id)));
        suggested.add(new Group("כדורסל בגן סאקר", String.valueOf(++id)));
        suggested.add(new Group("נסיעה לטבריה", String.valueOf(++id)));
        suggested.add(new Group("אושן 12", String.valueOf(++id)));

        suggestedGroups = new MutableLiveData<>();
        suggestedGroups.postValue(suggested);
    }

    public LiveData<List<Group>> getSuggestedGroups() {
        return suggestedGroups;
    }
}
