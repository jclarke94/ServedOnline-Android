package com.servedonline.servedonline_android.Entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.servedonline.servedonline_android.Database.DatabaseColumns;
import com.servedonline.servedonline_android.util.CursorUtils;

public class UserFollows implements Parcelable {
    private int id, userId, followId;

    public UserFollows(int id, int userId, int followId) {
        this.id = id;
        this.userId = userId;
        this.followId = followId;
    }

    protected UserFollows(Parcel in) {
        id = in.readInt();
        userId = in.readInt();
        followId = in.readInt();
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getFollowId() {
        return followId;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(userId);
        dest.writeInt(followId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserFollows> CREATOR = new Creator<UserFollows>() {
        @Override
        public UserFollows createFromParcel(Parcel in) {
            return new UserFollows(in);
        }

        @Override
        public UserFollows[] newArray(int size) {
            return new UserFollows[size];
        }
    };
}
