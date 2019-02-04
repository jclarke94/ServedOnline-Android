package com.servedonline.servedonline_android.Entity;

import android.os.Parcel;
import android.os.Parcelable;

public class RecipeLikes implements Parcelable {

    private int id, recipeId, userId;
    private long dateLiked;

    public RecipeLikes(int id, int recipeId, int userId, long dateLiked) {
        this.id = id;
        this.recipeId = recipeId;
        this.userId = userId;
        this.dateLiked = dateLiked;
    }

    protected RecipeLikes(Parcel in) {
        id = in.readInt();
        recipeId = in.readInt();
        userId = in.readInt();
        dateLiked = in.readLong();
    }

    public int getId() {
        return id;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public int getUserId() {
        return userId;
    }

    public long getDateLiked() {
        return dateLiked;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(recipeId);
        dest.writeInt(userId);
        dest.writeLong(dateLiked);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RecipeLikes> CREATOR = new Creator<RecipeLikes>() {
        @Override
        public RecipeLikes createFromParcel(Parcel in) {
            return new RecipeLikes(in);
        }

        @Override
        public RecipeLikes[] newArray(int size) {
            return new RecipeLikes[size];
        }
    };
}
