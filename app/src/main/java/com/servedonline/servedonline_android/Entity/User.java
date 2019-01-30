package com.servedonline.servedonline_android.Entity;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private int id;
    private String displayName, firstName, familyName, preferredName, dateOfBirth;
    private int[] following, followers;

    public User(int id, String displayName, String firstName, String familyName, String preferredName, String dateOfBirth, int[] following, int[] followers) {
        this.id = id;
        this.displayName = displayName;
        this.firstName = firstName;
        this.familyName = familyName;
        this.preferredName = preferredName;
        this.dateOfBirth = dateOfBirth;
        this.following = following;
        this.followers = followers;
    }

    public User(Parcel in) {
        id = in.readInt();
        displayName = in.readString();
        firstName = in.readString();
        familyName = in.readString();
        preferredName = in.readString();
        dateOfBirth = in.readString();
        following = in.createIntArray();
        followers = in.createIntArray();
    }



    public int getId() {
        return id;
    }

    public int[] getFollowing() {
        return following;
    }

    public int[] getFollowers() {
        return followers;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getPreferredName() {
        return preferredName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }



    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {

        out.writeInt(id);
        out.writeString(displayName);
        out.writeString(firstName);
        out.writeString(familyName);
        out.writeString(preferredName);
        out.writeString(dateOfBirth);
        out.writeIntArray(followers);
        out.writeIntArray(following);
    }
}
