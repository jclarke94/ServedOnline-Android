package com.servedonline.servedonline_android.Entity;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.servedonline.servedonline_android.Database.DatabaseColumns;
import com.servedonline.servedonline_android.Database.DatabaseGoverned;
import com.servedonline.servedonline_android.Database.DatabaseTables;
import com.servedonline.servedonline_android.util.CursorUtils;

public class Recipe extends DatabaseGoverned implements Parcelable {

    private int id;
    private String recipeTitle, recipeDescription, userName;
    private int userId, likes;
    private long timerLength;
    private RecipeSteps[] recipeSteps;
    private RecipeComments[] recipeComments;

    public Recipe(int id, String displayName, String recipeDescription, String userName,int userId, long timerLength, int likes, RecipeSteps[] recipeSteps, RecipeComments[] recipeComments) {
        this.id = id;
        this.recipeTitle = displayName;
        this.recipeDescription = recipeDescription;
        this.userName = userName;
        this.userId = userId;
        this.timerLength = timerLength;
        this.likes = likes;
        this.recipeSteps = recipeSteps;
        this.recipeComments = recipeComments;
    }

    public Recipe(Parcel in) {

        id = in.readInt();
        recipeTitle = in.readString();
        recipeDescription = in.readString();
        userName = in.readString();
        userId = in.readInt();
        timerLength = in.readLong();
        likes = in.readInt();
    }

    public Recipe(Cursor cursor) {
        id = CursorUtils.getCursorValue(cursor, DatabaseColumns.ID, id);
        recipeTitle = CursorUtils.getCursorValue(cursor, DatabaseColumns.Recipe.RECIPE_TITLE, recipeTitle);
        recipeDescription = CursorUtils.getCursorValue(cursor, DatabaseColumns.Recipe.RECIPE_DESCRIPTION, recipeDescription);
        userName = CursorUtils.getCursorValue(cursor, DatabaseColumns.Recipe.USER_NAME, userName);
        userId = CursorUtils.getCursorValue(cursor, DatabaseColumns.Recipe.USER_ID, userId);
        timerLength = CursorUtils.getCursorValue(cursor, DatabaseColumns.Recipe.TIMER_LENGTH, timerLength);
        likes = CursorUtils.getCursorValue(cursor, DatabaseColumns.Recipe.LIKES, likes);
    }

    @Override
    public String getDatabaseTable() {
        return DatabaseTables.RECIPE;
    }

    @Override
    public String getDatabaseId() {
        return String.valueOf(id);
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues out = new ContentValues();
        out.put(DatabaseColumns.ID, id);
        out.put(DatabaseColumns.Recipe.USER_ID, userId);
        out.put(DatabaseColumns.Recipe.RECIPE_TITLE, recipeTitle);
        out.put(DatabaseColumns.Recipe.RECIPE_DESCRIPTION, recipeDescription);
        out.put(DatabaseColumns.Recipe.USER_NAME, userName);
        out.put(DatabaseColumns.Recipe.TIMER_LENGTH, timerLength);
        out.put(DatabaseColumns.Recipe.LIKES, likes);
        return out;
    }

    public int getId() { return id; }

    public String getRecipeTitle() {
        return recipeTitle;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public String getUserName() { return userName; }

    public int getUserId() { return userId; }

    public long getTimerLength() { return timerLength; }

    public int getLikes() {
        return likes;
    }

    public RecipeSteps[] getRecipeSteps() {
        return recipeSteps;
    }

    public RecipeComments[] getRecipeComments() {
        return recipeComments;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {

        out.writeInt(id);
        out.writeString(recipeTitle);
        out.writeString(recipeDescription);
        out.writeString(userName);
        out.writeInt(userId);
        out.writeLong(timerLength);
        out.writeInt(likes);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public Recipe createFromParcel(Parcel parcel) {
            return new Recipe(parcel);
        }

        @Override
        public Object[] newArray(int i) {
            return new Object[i];
        }
    };
}