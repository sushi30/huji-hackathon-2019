package com.hackathon.huji.hujihackathon;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Server {
    private static final Server ourInstance = new Server();
    private static final String LOG_TAG = "Server";

    public static Server getInstance() {
        return ourInstance;
    }

    private static int id = 0;

    private MutableLiveData<List<Group>> suggestedGroups;
    private MutableLiveData<List<Group>> groupsContainingUser;

    private Server() {
        // should call remote
        List<Group> containingUser = new ArrayList<>();
        Group hackathon = new Group("hackathon", String.valueOf(++id), null);
        hackathon.addMember(new User("Itamar"));
        hackathon.addMember(new User("Yuval"));
        hackathon.addTag("Hackathon");
        hackathon.addTag("huji");
        hackathon.addTag("summer");
        hackathon.addTag("partners");
        hackathon.addTag("groups");
        hackathon.addTag("headache");

        Group revenges = new Group("הנוקמים", String.valueOf(++id), null);
        revenges.addMember(new User("Tal"));
        revenges.addMember(new User("Imri"));
        revenges.addTag("movies");
        revenges.addTag("marvel");

        containingUser.add(hackathon);
        containingUser.add(revenges);

        groupsContainingUser = new MutableLiveData<>();
        groupsContainingUser.setValue(containingUser);
    }

    public void fetchSuggestedGroups(Group group, Context context) {
        final JSONObject body = new JSONObject();
        try {
            body.put("id", 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://www.google.com";
        final List<Group> suggested = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonGroups = new JSONArray(response);
                            Gson gson = new Gson();

                            for (int i = 0; i < jsonGroups.length(); i++) {
                                Group group = gson.fromJson(jsonGroups.getJSONObject(i).toString(), Group.class);
                                suggested.add(group);
                            }
                        } catch (JSONException e) {
                            Log.d(LOG_TAG, Arrays.toString(e.getStackTrace()));
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Log.d(LOG_TAG, Arrays.toString(e.getStackTrace()));
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return body.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        queue.add(stringRequest);

        // should call remote api and get the list
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
