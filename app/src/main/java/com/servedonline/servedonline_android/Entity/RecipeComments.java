package com.servedonline.servedonline_android.Entity;

import android.os.Parcel;
import android.os.Parcelable;

public class RecipeComments implements Parcelable {

    private String displayName, comment;
    private int commentNumber;

    public RecipeComments(String displayName, String comment, int commentNumber) {
        this.displayName = displayName;
        this.comment = comment;
        this.commentNumber = commentNumber;
    }

    public RecipeComments(Parcel in) {

        displayName = in.readString();
        comment = in.readString();
        commentNumber = in.readInt();
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getComment() {
        return comment;
    }

    public int getCommentNumber() {
        return commentNumber;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {

        out.writeString(displayName);
        out.writeString(comment);
        out.writeInt(commentNumber);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public RecipeComments createFromParcel(Parcel parcel) {
            return new RecipeComments(parcel);
        }

        @Override
        public Object[] newArray(int i) {
            return new Object[i];
        }
    };
}
