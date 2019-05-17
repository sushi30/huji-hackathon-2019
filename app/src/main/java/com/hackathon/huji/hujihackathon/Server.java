package com.hackathon.huji.hujihackathon;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import com.squareup.okhttp.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final Server ourInstance = new Server();
    private static final String LOG_TAG = "Server";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient client;

    public static Server getInstance() {
        return ourInstance;
    }

    private MutableLiveData<List<Group>> suggestedGroups;
    private MutableLiveData<List<Group>> groupsContainingUser;

    private Server() {
        // should call remote
        List<Group> containingUser = new ArrayList<>();
        Group hackathon = new Group("hackathon", "01e3c72f-c945-4cc1-91e3-61487cba48e6", null);
        hackathon.addMember(new User("Itamar"));
        hackathon.addMember(new User("Yuval"));
        hackathon.addTag("Hackathon");
        hackathon.addTag("huji");
        hackathon.addTag("summer");
        hackathon.addTag("partners");
        hackathon.addTag("groups");
        hackathon.addTag("headache");

        Group revenges = new Group("הנוקמים", "3008c713-7c80-47c6-9f6b-3d5b53949327", null);
        revenges.addMember(new User("Tal"));
        revenges.addMember(new User("Imri"));
        revenges.addTag("movies");
        revenges.addTag("marvel");

        containingUser.add(hackathon);
        containingUser.add(revenges);

        groupsContainingUser = new MutableLiveData<>();
        groupsContainingUser.setValue(containingUser);
        client = new OkHttpClient();
        suggestedGroups = new MutableLiveData<>();
        suggestedGroups.setValue(new ArrayList<Group>());
    }

    private List<User> getMembers(List<String> ids) {
        final List<User> members = new ArrayList<>();
        for (String id : ids) {
            Request request = new Request.Builder()
                    .url("https://0x0dhiy01f.execute-api.us-west-2.amazonaws.com/v1/user?id=" + id).
                            get().build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (response.body() != null) {
                    String responseBody = response.body().string();
                    JSONObject jsonUser = new JSONObject(responseBody);
                    User user = new User(jsonUser);
                    members.add(user);
                }
            } catch (JSONException | IOException e) {
                Log.d(LOG_TAG, Arrays.toString(e.getStackTrace()));
            }
        }
        return members;
    }


    public void fetchSuggestedGroups(final Group userGroup) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                final List<Group> suggested = new ArrayList<>();
                // should call remote api and get the list
                Group avengers = new Group("Avengers", "123", "https://ichef.bbci.co.uk/news/624/cpsprodpb/BF0D/production/_106090984_2e39b218-c369-452e-b5be-d2476f9d8728.jpg", "hackathon", "huji");
                avengers.addMember(new User("Tal"));
                avengers.addMember(new User("Itay"));
                avengers.addMember(new User("Imri"));
                suggested.add(avengers);

                Group maccabi = new Group("Maccabi SP", "456", "https://ichef.bbci.co.uk/news/624/cpsprodpb/BF0D/production/_106090984_2e39b218-c369-452e-b5be-d2476f9d8728.jpg", "Basketball", "Sacher_Park");
                maccabi.addMember(new User("Yuval"));
                maccabi.addMember(new User("Itamar"));
                suggested.add(maccabi);

                Group north = new Group("The North Remembers", "789", "https://ichef.bbci.co.uk/news/624/cpsprodpb/BF0D/production/_106090984_2e39b218-c369-452e-b5be-d2476f9d8728.jpg", "dive", "from:jerusalem", "to:tiberias");
                north.addMember(new User("Assaf"));
                suggested.add(north);

                suggestedGroups.postValue(suggested);

                Request request = new Request.Builder()
                        .url("https://0x0dhiy01f.execute-api.us-west-2.amazonaws.com/v1/suggest?id=" + userGroup.getId()).
                                get().build();
                try {
                    Response response = client.newCall(request).execute();
                    String responseBody = response.body().string();
                    if (responseBody.charAt(0) == '{') {
                        JSONObject groupJson = new JSONObject(responseBody);
                        JSONArray idsArray = groupJson.getJSONArray("members");
                        List<String> ids = JSONArrayToList(idsArray);
                        List<User> users = getMembers(ids);
                        Group group = new Group(groupJson, users);
                        suggested.add(group);
                    } else {
                        JSONArray jsonGroups = new JSONArray(responseBody);
                        for (int i = 0; i < jsonGroups.length(); i++) {
                            JSONObject groupJson = jsonGroups.getJSONObject(i);
                            JSONArray idsArray = groupJson.getJSONArray("members");
                            List<String> ids = JSONArrayToList(idsArray);
                            List<User> users = getMembers(ids);
                            Group group = new Group(groupJson, users);
                            suggested.add(group);
                        }
                    }
                    Log.d(LOG_TAG, "Returned with suggested groups");
                    suggestedGroups.postValue(suggested);
                } catch (JSONException | IOException e) {
                    Log.d(LOG_TAG, Arrays.toString(e.getStackTrace()));
                }
            }
        });
    }

    private List<String> JSONArrayToList(JSONArray array) {
        List<String> res = new ArrayList<>();
        try {
            for (int i = 0; i < array.length(); i++) {
                res.add(array.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return res;
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
