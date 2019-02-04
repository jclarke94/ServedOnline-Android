package com.servedonline.servedonline_android.Entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.servedonline.servedonline_android.Database.DatabaseColumns;
import com.servedonline.servedonline_android.util.CursorUtils;

public class RecipeSteps implements Parcelable {

   private int id, recipeId, stepNumber, finalStep, timer;
   private String stepDescription;

    public RecipeSteps(int id, int recipeId, int stepNumber, int finalStep, int timer, String stepDescription) {
        this.id = id;
        this.recipeId = recipeId;
        this.stepNumber = stepNumber;
        this.finalStep = finalStep;
        this.timer = timer;
        this.stepDescription = stepDescription;
    }

    protected RecipeSteps(Parcel in) {
        id = in.readInt();
        recipeId = in.readInt();
        stepNumber = in.readInt();
        finalStep = in.readInt();
        timer = in.readInt();
        stepDescription = in.readString();
    }

    public RecipeSteps(Cursor cursor) {
        id = CursorUtils.getCursorValue(cursor, DatabaseColumns.ID, id);
        recipeId = CursorUtils.getCursorValue(cursor, DatabaseColumns.RecipeSteps.RECIPE_ID, recipeId);
        stepNumber = CursorUtils.getCursorValue(cursor, DatabaseColumns.RecipeSteps.STEP_NUMBER, stepNumber);
        finalStep = CursorUtils.getCursorValue(cursor, DatabaseColumns.RecipeSteps.FINAL_STEP, finalStep);
        timer = CursorUtils.getCursorValue(cursor, DatabaseColumns.RecipeSteps.TIMER, timer);
        stepDescription = CursorUtils.getCursorValue(cursor, DatabaseColumns.RecipeSteps.STEP_DESCRIPTION, stepDescription);
    }

    public int getId() {
        return id;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public int getStepNumber() {
        return stepNumber;
    }

    public int getFinalStep() {
        return finalStep;
    }

    public int getTimer() {
        return timer;
    }

    public String getStepDescription() {
        return stepDescription;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {

        out.writeInt(id);
        out.writeInt(recipeId);
        out.writeInt(stepNumber);
        out.writeInt(finalStep);
        out.writeInt(timer);
        out.writeString(stepDescription);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public RecipeSteps createFromParcel(Parcel in) {
            return new RecipeSteps(in);
        }

        @Override
        public Object[] newArray(int i) {
            return new Object[i];
        }
    };
}
