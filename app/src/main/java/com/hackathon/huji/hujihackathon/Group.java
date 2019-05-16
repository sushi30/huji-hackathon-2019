package com.hackathon.huji.hujihackathon;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Group implements Parcelable {

    private String id;
    private String name;
    private String image;
    private List<User> members;
    private List<String> tags;

    public Group(String groupName, String id, String imageUrl, String... tags) {
        this.id = id;
        name = groupName;
        members = new ArrayList<>();
        image = imageUrl;
        this.tags = new ArrayList<>(Arrays.asList(tags));
    }

    protected Group(Parcel in) {
        members = new ArrayList<>();
        int membersSize = in.readInt();
        for (int i = 0; i < membersSize; i++) {
            members.add((User) in.readParcelable(User.class.getClassLoader()));
        }

        tags = new ArrayList<>();
        int tagsSize = in.readInt();
        for (int i = 0; i < tagsSize; i++) {
            tags.add(in.readString());
        }

        id = in.readString();
        name = in.readString();
        image = in.readString();
    }

    public static final Creator<Group> CREATOR = new Creator<Group>() {
        @Override
        public Group createFromParcel(Parcel in) {
            return new Group(in);
        }

        @Override
        public Group[] newArray(int size) {
            return new Group[size];
        }
    };

    public void addMember(User user) {
        members.add(user);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public List<User> getMembers() {
        return members;
    }

    public List<String> getTags() {
        return tags;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(members.size());
        for (User user : members) {
            dest.writeParcelable(user, 0);
        }
        dest.writeInt(tags.size());
        for (String tag : tags) {
            dest.writeString(tag);
        }
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(image);

    }
}
