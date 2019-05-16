package com.hackathon.huji.hujihackathon.swipe_screen;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.hackathon.huji.hujihackathon.Group;
import com.hackathon.huji.hujihackathon.Server;


import java.util.List;

public class SwipingViewModel extends ViewModel {

    private LiveData<List<Group>> suggestedGroups;

    public SwipingViewModel() {
        suggestedGroups = Server.getInstance().getSuggestedGroups();
    }

    public LiveData<List<Group>> getSuggestedGroups() {
        return suggestedGroups;
    }


}
