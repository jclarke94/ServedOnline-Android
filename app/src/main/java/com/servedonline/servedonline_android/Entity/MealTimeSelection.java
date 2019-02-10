package com.servedonline.servedonline_android.Entity;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.servedonline.servedonline_android.Database.DatabaseColumns;
import com.servedonline.servedonline_android.Database.DatabaseGoverned;
import com.servedonline.servedonline_android.Database.DatabaseTables;
import com.servedonline.servedonline_android.util.CursorUtils;

public class MealTimeSelection extends DatabaseGoverned implements Parcelable {

    private int id, userId, recipeId, mealType;
    private long dateSelected;

    public MealTimeSelection(int id, int userId, int recipeId, int mealType, long dateSelected) {
        this.id = id;
        this.userId = userId;
        this.recipeId = recipeId;
        this.mealType = mealType;
        this.dateSelected = dateSelected;
    }


    protected MealTimeSelection(Parcel in) {
        id = in.readInt();
        userId = in.readInt();
        recipeId = in.readInt();
        mealType = in.readInt();
        dateSelected = in.readLong();
    }

    public MealTimeSelection(Cursor cursor) {
        id = CursorUtils.getCursorValue(cursor, DatabaseColumns.ID, id);
        userId = CursorUtils.getCursorValue(cursor, DatabaseColumns.MealTimeSelection.USER_ID, userId);
        recipeId = CursorUtils.getCursorValue(cursor, DatabaseColumns.MealTimeSelection.RECIPE_ID, recipeId);
        mealType = CursorUtils.getCursorValue(cursor, DatabaseColumns.MealTimeSelection.MEAL_TYPE, mealType);
        dateSelected = CursorUtils.getCursorValue(cursor, DatabaseColumns.MealTimeSelection.DATE_SELECTED, dateSelected);
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

    public int getMealType() {
        return mealType;
    }

    public long getDateSelected() {
        return dateSelected;
    }

    @Override
    public String getDatabaseTable() {
        return DatabaseTables.MEAL_TIME_SELECTION;
    }

    @Override
    public String getDatabaseId() {
        return String.valueOf(id);
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues out = new ContentValues();
        out.put(DatabaseColumns.ID, id);
        out.put(DatabaseColumns.MealTimeSelection.USER_ID, userId);
        out.put(DatabaseColumns.MealTimeSelection.RECIPE_ID, recipeId);
        out.put(DatabaseColumns.MealTimeSelection.MEAL_TYPE, mealType);
        out.put(DatabaseColumns.MealTimeSelection.DATE_SELECTED, dateSelected);
        return out;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(userId);
        dest.writeInt(recipeId);
        dest.writeInt(mealType);
        dest.writeLong(dateSelected);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MealTimeSelection> CREATOR = new Creator<MealTimeSelection>() {
        @Override
        public MealTimeSelection createFromParcel(Parcel in) {
            return new MealTimeSelection(in);
        }

        @Override
        public MealTimeSelection[] newArray(int size) {
            return new MealTimeSelection[size];
        }
    };
}
