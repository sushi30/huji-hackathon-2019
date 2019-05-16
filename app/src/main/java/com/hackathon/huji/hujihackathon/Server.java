package com.hackathon.huji.hujihackathon;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final Server ourInstance = new Server();

    public static Server getInstance() {
        return ourInstance;
    }

    private static int id = 0;

    private MutableLiveData<List<Group>> suggestedGroups;
    private MutableLiveData<List<Group>> groupsContainingUser;

    private Server() {
        // should call remote
        List<Group> containingUser = new ArrayList<>();
        Group hackathon = new Group("hackathon", String.valueOf(++id));
        hackathon.addMember(new User("Itamar"));
        hackathon.addMember(new User("Yuval"));
        hackathon.addTag("Hackathon");
        hackathon.addTag("huji");
        hackathon.addTag("summer");
        hackathon.addTag("partners");
        hackathon.addTag("groups");
        hackathon.addTag("headache");

        Group revenges = new Group("הנוקמים", String.valueOf(++id));
        revenges.addMember(new User("Tal"));
        revenges.addMember(new User("Imri"));
        revenges.addTag("movies");
        revenges.addTag("marvel");

        containingUser.add(hackathon);
        containingUser.add(revenges);

        groupsContainingUser = new MutableLiveData<>();
        groupsContainingUser.setValue(containingUser);
    }

    public void fetchSuggestedGroups(Group group) {
        // should call remote api and get the list
        List<Group> suggested = new ArrayList<>();
        Group avengers = new Group("Avengers", String.valueOf(++id), "https://ichef.bbci.co.uk/news/624/cpsprodpb/BF0D/production/_106090984_2e39b218-c369-452e-b5be-d2476f9d8728.jpg", "hackathon", "huji");
        avengers.addMember(new User("Tal"));
        avengers.addMember(new User("Itay"));
        avengers.addMember(new User("Imri"));
        suggested.add(avengers);

        Group maccabi = new Group("Maccabi SP", String.valueOf(++id), "https://ichef.bbci.co.uk/news/624/cpsprodpb/BF0D/production/_106090984_2e39b218-c369-452e-b5be-d2476f9d8728.jpg", "Basketball", "Sacher_Park");
        maccabi.addMember(new User("Yuval"));
        maccabi.addMember(new User("Itamar"));
        suggested.add(maccabi);

        Group north = new Group("The North Remembers", String.valueOf(++id), "https://ichef.bbci.co.uk/news/624/cpsprodpb/BF0D/production/_106090984_2e39b218-c369-452e-b5be-d2476f9d8728.jpg", "dive", "from:jerusalem", "to:tiberias");
        north.addMember(new User("Assaf"));
        suggested.add(north);

        suggestedGroups = new MutableLiveData<>();
        suggestedGroups.postValue(suggested);
    }

    public MutableLiveData<List<Group>> getSuggestedGroups() {
        return suggestedGroups;
    }

    public void addMember(Group group, User user) {
        group.addMember(user);
    }

    public LiveData<List<Group>> getGroupsContainingUser() {
        return groupsContainingUser;
    }
}
