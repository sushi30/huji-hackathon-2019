package com.hackathon.huji.hujihackathon;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.*;

public class SwipingViewModel extends ViewModel {

    private LiveData<List<Group>> suggestedGroups;

    public SwipingViewModel() {
        suggestedGroups = new MutableLiveData<>();
        suggestedGroups = Server.getInstance().getSuggestedGroups();
    }

    public LiveData<List<Group>> getSuggestedGroups() {
        return suggestedGroups;
    }


}
