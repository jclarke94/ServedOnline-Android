package com.servedonline.servedonline_android.Entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.servedonline.servedonline_android.Database.DatabaseColumns;
import com.servedonline.servedonline_android.util.CursorUtils;

public class RecipeStepsStarted implements Parcelable{
    private int id, recipeStepId, userId;
    private long timestamp;

    public RecipeStepsStarted(int id, int recipeStepId, int userId, long timestamp) {
        this.id = id;
        this.recipeStepId = recipeStepId;
        this.userId = userId;
        this.timestamp = timestamp;
    }

    protected RecipeStepsStarted(Parcel in) {
        id = in.readInt();
        recipeStepId = in.readInt();
        userId = in.readInt();
        timestamp = in.readLong();
    }

    public RecipeStepsStarted(Cursor cursor) {
        id = CursorUtils.getCursorValue(cursor, DatabaseColumns.ID, id);
        recipeStepId = CursorUtils.getCursorValue(cursor, DatabaseColumns.RecipeStepsStarted.RECIPE_STEP_ID, recipeStepId);
        userId = CursorUtils.getCursorValue(cursor, DatabaseColumns.RecipeStepsStarted.USER_ID, userId);
        timestamp = CursorUtils.getCursorValue(cursor, DatabaseColumns.RecipeStepsStarted.TIMESTAMP_STARTED, timestamp);
    }

    public int getId() {
        return id;
    }

    public int getRecipeStepId() {
        return recipeStepId;
    }

    public int getUserId() {
        return userId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(recipeStepId);
        dest.writeInt(userId);
        dest.writeLong(timestamp);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RecipeStepsStarted> CREATOR = new Creator<RecipeStepsStarted>() {
        @Override
        public RecipeStepsStarted createFromParcel(Parcel in) {
            return new RecipeStepsStarted(in);
        }

        @Override
        public RecipeStepsStarted[] newArray(int size) {
            return new RecipeStepsStarted[size];
        }
    };
}
