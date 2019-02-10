package com.servedonline.servedonline_android.Entity;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.servedonline.servedonline_android.Database.DatabaseColumns;
import com.servedonline.servedonline_android.Database.DatabaseGoverned;
import com.servedonline.servedonline_android.Database.DatabaseTables;
import com.servedonline.servedonline_android.util.CursorUtils;

public class RecipeComments extends DatabaseGoverned implements Parcelable {

    private String comment;
    private int id, recipeId, userId;

    public RecipeComments(int id, int recipeId, int userId, String comment) {
        this.id = id;
        this.recipeId = recipeId;
        this.userId = userId;
        this.comment = comment;
    }

    public RecipeComments(Parcel in) {
        id = in.readInt();
        recipeId = in.readInt();
        userId = in.readInt();
        comment = in.readString();
    }

    public RecipeComments(Cursor cursor) {
        id = CursorUtils.getCursorValue(cursor, DatabaseColumns.ID, id);
        recipeId = CursorUtils.getCursorValue(cursor, DatabaseColumns.RecipeComments.RECIPE_ID, recipeId);
        userId = CursorUtils.getCursorValue(cursor, DatabaseColumns.RecipeComments.USER_ID, userId);
        comment = CursorUtils.getCursorValue(cursor, DatabaseColumns.RecipeComments.COMMENT, comment);
    }

    @Override
    public String getDatabaseTable() {
        return DatabaseTables.RECIPE_COMMENTS;
    }

    @Override
    public String getDatabaseId() {
        return String.valueOf(id);
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues out = new ContentValues();
        out.put(DatabaseColumns.ID, id);
        out.put(DatabaseColumns.RecipeComments.USER_ID, userId);
        out.put(DatabaseColumns.RecipeComments.RECIPE_ID, recipeId);
        out.put(DatabaseColumns.RecipeComments.COMMENT, comment);
        return out;
    }

    public int getId() { return id; }

    public int getRecipeId() { return recipeId; }

    public int getUserId() { return userId; }

    public String getComment() {
        return comment;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {

        out.writeInt(id);
        out.writeInt(recipeId);
        out.writeInt(userId);
        out.writeString(comment);
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
