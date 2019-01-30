package com.servedonline.servedonline_android.Entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Recipe implements Parcelable {

    private String displayName, recipeName, recipeDescription;
    private int userId;
    private int yum; //0 = false, 1 = true
    private RecipeSteps[] recipeSteps;
    private RecipeComments[] recipeComments;

    public Recipe(String displayName, String recipeName, String recipeDescription, int userId, int yum, RecipeSteps[] recipeSteps, RecipeComments[] recipeComments) {
        this.displayName = displayName;
        this.recipeName = recipeName;
        this.recipeDescription = recipeDescription;
        this.userId = userId;
        this.yum = yum;
        this.recipeSteps = recipeSteps;
        this.recipeComments = recipeComments;
    }

    public Recipe(Parcel in) {

        displayName = in.readString();
        recipeName = in.readString();
        recipeDescription = in.readString();
        userId = in.readInt();
        yum = in.readInt();
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public int getUserId() { return userId; }

    public int getYum() {
        return yum;
    }

    public void setYum(int yum) { this.yum = yum; }

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

        out.writeString(displayName);
        out.writeString(recipeName);
        out.writeString(recipeDescription);
        out.writeInt(userId);
        out.writeInt(yum);
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