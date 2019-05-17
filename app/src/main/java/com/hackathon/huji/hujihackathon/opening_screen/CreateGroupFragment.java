package com.hackathon.huji.hujihackathon.opening_screen;


import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import com.hackathon.huji.hujihackathon.Group;
import com.hackathon.huji.hujihackathon.R;
import com.hackathon.huji.hujihackathon.Server;
import me.gujun.android.taggroup.TagGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateGroupFragment extends DialogFragment {

    private List<String> chosenTags = new ArrayList<>();
    private List<String> initialSuggestedTags = new ArrayList<>();

    public CreateGroupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OpeningActivityViewModel viewModel = ViewModelProviders.of(this).get(OpeningActivityViewModel.class);
        initialSuggestedTags = viewModel.getInitialSuggestedTags();

        view.setBackgroundColor(Color.WHITE);
        final TagGroup chosenTagsViews = view.findViewById(R.id.chosen_tags);
        final TagGroup suggestedTags = view.findViewById(R.id.suggested_tags);
        suggestedTags.setTags(initialSuggestedTags);
        suggestedTags.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                chosenTags.add(tag);
                chosenTagsViews.setTags(chosenTags);
                ArrayList<String> copy = new ArrayList<>(initialSuggestedTags);
                copy.remove(tag);
                initialSuggestedTags = new ArrayList<>(copy);
                suggestedTags.setTags(initialSuggestedTags);
            }
        });

        final EditText editText = view.findViewById(R.id.editText);
        ImageView send = view.findViewById(R.id.send_button);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                editText.getText().clear();
                chosenTags.add(text);
                chosenTagsViews.setTags(chosenTags);
            }
        });

        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText groupName = view.findViewById(R.id.editText2);
                if (groupName.getText().toString().equals(""))
                    return;
                String[] tagsArray = new String[chosenTags.size()];
                Group group = new Group(groupName.getText().toString(), "999", null, chosenTags.toArray(tagsArray));
                Server.getInstance().createGroup(group);
                getActivity().getSupportFragmentManager().beginTransaction().remove(CreateGroupFragment.this).commit();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_group, container, false);
    }


}
