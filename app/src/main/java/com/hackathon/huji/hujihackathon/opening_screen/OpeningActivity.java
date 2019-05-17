package com.hackathon.huji.hujihackathon.opening_screen;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.hackathon.huji.hujihackathon.Group;
import com.hackathon.huji.hujihackathon.R;
import com.hackathon.huji.hujihackathon.Server;
import com.hackathon.huji.hujihackathon.SwipeActivity;

import java.util.List;

public class OpeningActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);

        OpeningActivityViewModel viewModel = ViewModelProviders.of(this).get(OpeningActivityViewModel.class);
        viewModel.getGroupsContainingUser().observe(this, new Observer<List<Group>>() {
            @Override
            public void onChanged(@Nullable List<Group> groups) {
                RecyclerView recyclerView = findViewById(R.id.groups_recycler_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(OpeningActivity.this));
                recyclerView.setAdapter(new GroupAdapter(groups));
            }
        });

        findViewById(R.id.create_group_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                CreateGroupFragment fragment = new CreateGroupFragment();
                //fragment.show(fragmentManager, "create_group");
                fragmentTransaction.add(R.id.create_group_container, fragment).addToBackStack("create_group");
                fragmentTransaction.commit();
            }
        });
    }

    static class GroupHolder extends RecyclerView.ViewHolder {
        // elements we need from the view of the group
        public TextView groupName;
        public LinearLayout groupTags;

        public GroupHolder(@NonNull View itemView) {
            super(itemView);
            groupName = itemView.findViewById(R.id.group_name);
            groupTags = itemView.findViewById(R.id.tags);
        }
    }

    static class GroupAdapter extends RecyclerView.Adapter<GroupHolder> {

        private List<Group> groupContainingUser;
        //private Context context;

        protected GroupAdapter(List<Group> groupContainingUser) {
            this.groupContainingUser = groupContainingUser;
        }

        @NonNull
        @Override
        public GroupHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            Context context = viewGroup.getContext();
            View groupHolder = LayoutInflater.from(context).inflate(R.layout.opening_screen_group_layout, viewGroup, false);
            return new GroupHolder(groupHolder);
        }

        @Override
        public void onBindViewHolder(@NonNull final GroupHolder groupHolder, int i) {
            final Group group = groupContainingUser.get(i);
            // update view holder according to the data from group
            String name = group.getName();
            List<String> tags = group.getTags();
            groupHolder.groupName.setText(name);
            groupHolder.groupName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Server.getInstance().fetchSuggestedGroups(group);
                    Intent intent = new Intent(groupHolder.groupName.getContext(), SwipeActivity.class);
                    groupHolder.groupName.getContext().startActivity(intent);
                }
            });
            for (String tag : tags) {
                TextView textView = new TextView(groupHolder.groupTags.getContext());
                textView.setTextColor(Color.WHITE);
                textView.setTextSize(18);
                textView.setPadding(20, 20, 2, 2);
                String text = "#" + tag;
                textView.setText(text);
                groupHolder.groupTags.addView(textView);
            }
        }

        @Override
        public int getItemCount() {
            return groupContainingUser.size();
        }
    }
}
