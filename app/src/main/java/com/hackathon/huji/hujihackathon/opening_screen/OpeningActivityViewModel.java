package com.hackathon.huji.hujihackathon.opening_screen;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.hackathon.huji.hujihackathon.Group;
import com.hackathon.huji.hujihackathon.Server;

import java.util.Arrays;
import java.util.List;

public class OpeningActivityViewModel extends ViewModel {

    private LiveData<List<Group>> groupsContainingUser;
    private List<String> initialSuggestedTags;

    public OpeningActivityViewModel() {
        groupsContainingUser = Server.getInstance().getGroupsContainingUser();
        initialSuggestedTags = Arrays.asList("Football", "Travel", "Studies", "Meeting", "Basketball", "Playing");
    }

    public LiveData<List<Group>> getGroupsContainingUser() {
        return groupsContainingUser;
    }

    public List<String> getInitialSuggestedTags() {
        return initialSuggestedTags;
    }
}
