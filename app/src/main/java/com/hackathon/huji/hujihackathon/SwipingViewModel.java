package com.hackathon.huji.hujihackathon;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class SwipingViewModel extends ViewModel {

    private MutableLiveData<List<Group>> suggestedGroups;

    public SwipingViewModel() {
        suggestedGroups = new MutableLiveData<>();
        suggestedGroups.setValue(new ArrayList<Group>());
    }

    public LiveData<List<Group>> getSuggestedGroups() {
        return suggestedGroups;
    }

    public void setSuggestedGroups(List<Group> suggestedGroups){
        this.suggestedGroups.setValue(suggestedGroups);
    }
}
