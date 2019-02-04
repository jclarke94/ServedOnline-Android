package com.servedonline.servedonline_android.Entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.servedonline.servedonline_android.Database.DatabaseColumns;
import com.servedonline.servedonline_android.util.CursorUtils;

public class User implements Parcelable {

    private int id;
    private String displayName, firstName, lastName, email, password, salt;


    public User(int id, String displayName, String firstName, String lastName, String email, String password, String salt) {
        this.id = id;
        this.displayName = displayName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.salt = salt;
    }

    public User(Parcel in) {
        id = in.readInt();
        displayName = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        email = in.readString();
        password = in.readString();
        salt = in.readString();
    }

    public User(Cursor cursor) {
        id = CursorUtils.getCursorValue(cursor, DatabaseColumns.ID, id);
        displayName = CursorUtils.getCursorValue(cursor, DatabaseColumns.User.DISPLAY_NAME, displayName);
        firstName = CursorUtils.getCursorValue(cursor, DatabaseColumns.User.FIRST_NAME, firstName);
        lastName = CursorUtils.getCursorValue(cursor, DatabaseColumns.User.LAST_NAME, lastName);
        email = CursorUtils.getCursorValue(cursor, DatabaseColumns.User.EMAIL, email);
        password = CursorUtils.getCursorValue(cursor, DatabaseColumns.User.PASSWORD, password);
        salt = CursorUtils.getCursorValue(cursor, DatabaseColumns.User.SALT, salt);
    }

    public int getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() { return email; }

    public String getPassword() { return password; }

    public String getSalt() { return salt; }



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
        out.writeString(lastName);
        out.writeString(email);
        out.writeString(password);
        out.writeString(salt);
    }
}
