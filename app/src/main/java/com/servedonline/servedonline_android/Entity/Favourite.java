package com.servedonline.servedonline_android.Entity;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.servedonline.servedonline_android.Database.DatabaseColumns;
import com.servedonline.servedonline_android.Database.DatabaseGoverned;
import com.servedonline.servedonline_android.Database.DatabaseTables;
import com.servedonline.servedonline_android.util.CursorUtils;

public class Favourite extends DatabaseGoverned implements Parcelable {

    private int id, userId, recipeId;

    public Favourite(int id, int userId, int recipeId) {
        this.id = id;
        this.userId = userId;
        this.recipeId = recipeId;
    }

    protected Favourite(Parcel in) {
        id = in.readInt();
        userId = in.readInt();
        recipeId = in.readInt();
    }

    public Favourite(Cursor cursor) {
        id = CursorUtils.getCursorValue(cursor, DatabaseColumns.ID, id);
        userId = CursorUtils.getCursorValue(cursor, DatabaseColumns.Favourites.USER_ID, userId);
        recipeId = CursorUtils.getCursorValue(cursor, DatabaseColumns.Favourites.RECIPE_ID, recipeId);
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(userId);
        dest.writeInt(recipeId);
    }

    @Override
    public String getDatabaseTable() {
        return DatabaseTables.FAVOURITES;
    }

    @Override
    public String getDatabaseId() {
        return String.valueOf(id);
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues out = new ContentValues();
        out.put(DatabaseColumns.ID, id);
        out.put(DatabaseColumns.Favourites.USER_ID, userId);
        out.put(DatabaseColumns.Favourites.RECIPE_ID, recipeId);

        return out;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Favourite> CREATOR = new Creator<Favourite>() {
        @Override
        public Favourite createFromParcel(Parcel in) {
            return new Favourite(in);
        }

        @Override
        public Favourite[] newArray(int size) {
            return new Favourite[size];
        }
    };
}
