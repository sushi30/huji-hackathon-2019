package com.hackathon.huji.hujihackathon.opening_screen;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import com.hackathon.huji.hujihackathon.Group;
import com.hackathon.huji.hujihackathon.Server;

import java.util.List;

public class OpeningActivityViewModel extends ViewModel {

    private LiveData<List<Group>> groupsContainingUser;

    public OpeningActivityViewModel() {
        groupsContainingUser = Server.getInstance().getGroupsContainingUser();
    }

    public LiveData<List<Group>> getGroupsContainingUser() {
        return groupsContainingUser;
    }
}
