package com.servedonline.servedonline_android.Entity;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.servedonline.servedonline_android.Database.DatabaseColumns;
import com.servedonline.servedonline_android.Database.DatabaseGoverned;
import com.servedonline.servedonline_android.Database.DatabaseTables;
import com.servedonline.servedonline_android.util.CursorUtils;

public class User extends DatabaseGoverned implements Parcelable {

    private int id;
    private String displayName, firstName, lastName, email, password;
    private UserAddress[] userAddresses;
    private UserFollows[] userFollows;


    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public User(Parcel in) {
        id = in.readInt();
        displayName = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        email = in.readString();
        password = in.readString();
    }

    public User(Cursor cursor) {
        id = CursorUtils.getCursorValue(cursor, DatabaseColumns.ID, id);
        displayName = CursorUtils.getCursorValue(cursor, DatabaseColumns.User.DISPLAY_NAME, displayName);
        firstName = CursorUtils.getCursorValue(cursor, DatabaseColumns.User.FIRST_NAME, firstName);
        lastName = CursorUtils.getCursorValue(cursor, DatabaseColumns.User.LAST_NAME, lastName);
        email = CursorUtils.getCursorValue(cursor, DatabaseColumns.User.EMAIL, email);
        password = CursorUtils.getCursorValue(cursor, DatabaseColumns.User.PASSWORD, password);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() { return email; }

    public String getPassword() { return password; }

    public UserAddress[] getUserAddresses() {
        return userAddresses;
    }

    public void setUserAddresses(UserAddress[] userAddresses) {
        this.userAddresses = userAddresses;
    }

    public UserFollows[] getUserFollows() {
        return userFollows;
    }

    public void setUserFollows(UserFollows[] userFollows) {
        this.userFollows = userFollows;
    }

    @Override
    public String getDatabaseTable() {
        return DatabaseTables.USER;
    }

    @Override
    public String getDatabaseId() {
        return String.valueOf(id);
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues out = new ContentValues();
        out.put(DatabaseColumns.ID, id);
        out.put(DatabaseColumns.User.DISPLAY_NAME, displayName);
        out.put(DatabaseColumns.User.FIRST_NAME, firstName);
        out.put(DatabaseColumns.User.LAST_NAME, lastName);
        out.put(DatabaseColumns.User.EMAIL, email);
        out.put(DatabaseColumns.User.PASSWORD, password);

        return out;
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
        out.writeString(lastName);
        out.writeString(email);
        out.writeString(password);
    }
}
