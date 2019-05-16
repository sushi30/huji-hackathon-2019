package com.hackathon.huji.hujihackathon.opening_screen;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hackathon.huji.hujihackathon.R;
import me.gujun.android.taggroup.TagGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateGroupFragment extends DialogFragment {

    public CreateGroupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TagGroup mTagGroup = view.findViewById(R.id.tag_group);
        mTagGroup.setTags("Tag1", "Tag2", "Tag3");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_group, container, false);
    }


}
