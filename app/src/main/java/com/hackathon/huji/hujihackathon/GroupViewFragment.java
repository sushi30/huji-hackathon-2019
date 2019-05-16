package com.hackathon.huji.hujihackathon;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GroupViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GroupViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupViewFragment extends Fragment {
    // the fragment initialization parameters

    private SwipingViewModel viewModel;

    private OnFragmentInteractionListener mListener;

    public GroupViewFragment() {
        // Required empty public constructor
    }

    public static GroupViewFragment newInstance() {
        GroupViewFragment fragment = new GroupViewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity == null) return;
        viewModel = ViewModelProviders.of(activity).get(SwipingViewModel.class);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View layout = inflater.inflate(R.layout.fragment_group_view, container, false);
        final TextView groupName = layout.findViewById(R.id.group_name);

        viewModel.getCurGroupToShow().observe(this, new Observer<Group>() {
            @Override
            public void onChanged(@Nullable Group group) {
                if (group == null) return;
                groupName.setText(group.getName());

                List<User> users = group.getMembers();
                for (User user : users) {
                    LinearLayout linearLayout = layout.findViewById(R.id.mainLayout);
                    TextView textView = new TextView(GroupViewFragment.this.getContext());
                    textView.setText(user.getName());
                    linearLayout.addView(textView);
                }
            }
        });

        return layout;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
