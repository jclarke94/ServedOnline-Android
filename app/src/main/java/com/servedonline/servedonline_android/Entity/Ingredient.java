package com.servedonline.servedonline_android.Entity;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.servedonline.servedonline_android.Database.DatabaseColumns;
import com.servedonline.servedonline_android.Database.DatabaseGoverned;
import com.servedonline.servedonline_android.Database.DatabaseTables;

public class Ingredient extends DatabaseGoverned implements Parcelable {
    private int id, recipeId;
    private String ingredient;

    public Ingredient(int id, int recipeId, String ingredient) {
        this.id = id;
        this.recipeId = recipeId;
        this.ingredient = ingredient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getIngredient() {
        return ingredient;
    }

    protected Ingredient(Parcel in) {
        id = in.readInt();
        recipeId = in.readInt();
        ingredient = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(recipeId);
        parcel.writeString(ingredient);
    }

    @Override
    public String getDatabaseTable() {
        return DatabaseTables.INGREDIENTS;
    }

    @Override
    public String getDatabaseId() {
        return String.valueOf(id);
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues out = new ContentValues();
        out.put(DatabaseColumns.ID, id);
        out.put(DatabaseColumns.Ingredients.RECIPE_ID, recipeId);
        out.put(DatabaseColumns.Ingredients.INGREDIENT, ingredient);
        return out;
    }
}
