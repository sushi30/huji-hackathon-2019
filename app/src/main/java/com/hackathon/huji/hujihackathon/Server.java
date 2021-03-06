package com.hackathon.huji.hujihackathon;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.okhttp.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final Server ourInstance = new Server();
    private static final String LOG_TAG = "Server";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient client = new OkHttpClient();

    private static String id;

    public static Server getInstance() {
        return ourInstance;
    }

    private MutableLiveData<List<Group>> suggestedGroups;
    private MutableLiveData<List<Group>> groupsContainingUser;

    private Server() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {

                JsonObject json = new JsonObject();
                json.addProperty("name", "Tal");
                json.addProperty("age", "27");

                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, json.toString());

                Request request = new Request.Builder()
                        .url("https://0x0dhiy01f.execute-api.us-west-2.amazonaws.com/v1/new").
                                post(body)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    id = response.body().string().replaceAll("\"", "");
                } catch (Exception e) {
                    Log.d(LOG_TAG, Arrays.toString(e.getStackTrace()));
                }
            }
        });

        // should call remote
        List<Group> containingUser = new ArrayList<>();
        Group hackathon = new Group("hackathon", "01e3c72f-c945-4cc1-91e3-61487cba48e6", ImageGenerator.get());
        hackathon.addMember(new User("Itamar"));
        hackathon.addMember(new User("Yuval"));
        hackathon.addTag("Hackathon");
        hackathon.addTag("huji");
        hackathon.addTag("summer");
        hackathon.addTag("partners");
        hackathon.addTag("groups");
        hackathon.addTag("headache");

        Group revenges = new Group("הנוקמים", "3008c713-7c80-47c6-9f6b-3d5b53949327", ImageGenerator.get());
        revenges.addMember(new User("Tal"));
        revenges.addMember(new User("Imri"));
        revenges.addTag("movies");
        revenges.addTag("marvel");

        Group food = new Group("food!!!", "66900df7-1810-4e44-b3a9-3fa1e5af9db3", ImageGenerator.get());
        food.addMember(new User("Tal"));
        food.addMember(new User("Imri"));
        food.addTag("food");

        containingUser.add(hackathon);
        containingUser.add(revenges);
        containingUser.add(food);

        groupsContainingUser = new MutableLiveData<>();
        groupsContainingUser.setValue(containingUser);
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
                Group avengers = new Group("Avengers", "123", ImageGenerator.get(), "hackathon", "huji");
                avengers.addMember(new User("Tal"));
                avengers.addMember(new User("Itay"));
                avengers.addMember(new User("Imri"));
                suggested.add(avengers);

                Group maccabi = new Group("Maccabi SP", "456", ImageGenerator.get(), "Basketball", "Sacher_Park");
                maccabi.addMember(new User("Yuval"));
                maccabi.addMember(new User("Itamar"));
                suggested.add(maccabi);

                Group north = new Group("The North Remembers", "789", ImageGenerator.get(), "dive", "from:jerusalem", "to:tiberias");
                north.addMember(new User("Assaf"));
                suggested.add(north);

                suggestedGroups.postValue(new ArrayList<>(suggested));
                suggested.clear();

                Request request = new Request.Builder()
                        .url("https://0x0dhiy01f.execute-api.us-west-2.amazonaws.com/v1/suggests?id=" + userGroup.getId()).
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

    public void createGroup(final Group group) {
        final List<Group> groups = groupsContainingUser.getValue();
        if (groups == null) return;
        groups.add(group);
        groupsContainingUser.postValue(groups);

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < group.getTags().size() - 1; i++) {
                    builder.append("\"").append(group.getTags().get(i)).append("\"").append(",");
                }
                builder.append("\"").append(group.getTags().get(group.getTags().size() - 1)).append("\"");

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("members", new JSONArray(new String[]{id}));
                    jsonObject.put("tags", new JSONArray(group.getTags()));
                    jsonObject.put("name", group.getName());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, jsonObject.toString());

                Request request = new Request.Builder()
                        .url("https://0x0dhiy01f.execute-api.us-west-2.amazonaws.com/v1/game")
                        .post(body)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String id = response.body().string().replaceAll("\"", "");
                    group.setId(id);
                } catch (Exception e) {
                    Log.d(LOG_TAG, Arrays.toString(e.getStackTrace()));
                }
            }
        });
    }

    public LiveData<List<Group>> getGroupsContainingUser() {
        return groupsContainingUser;
    }
}
