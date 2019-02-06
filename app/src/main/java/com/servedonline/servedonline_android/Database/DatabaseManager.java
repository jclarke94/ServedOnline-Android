package com.servedonline.servedonline_android.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "served.db";
    private static final int VERSION = 1;


    public DatabaseManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseManager(Context context) { super(context, DATABASE_NAME, null, VERSION); }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        initialiseDatabase(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private void initialiseDatabase(SQLiteDatabase db) {
        String createUser = "CREATE TABLE " + DatabaseTables.USER + " ("
                + DatabaseColumns.ID + " INTEGER PRIMARY KEY NOT NULL,"
                + DatabaseColumns.User.FIRST_NAME + " TEXT NOT NULL,"
                + DatabaseColumns.User.LAST_NAME + " TEXT NOT NULL,"
                + DatabaseColumns.User.DISPLAY_NAME + " TEXT NOT NULL,"
                + DatabaseColumns.User.EMAIL + " TEXT NOT NULL,"
                + DatabaseColumns.User.PASSWORD + " TEXT NOT NULL"
                + ")";
        db.execSQL(createUser);

        String createUserAddress = "CREATE TABLE " + DatabaseTables.USER_ADDRESS + " ("
                + DatabaseColumns.ID + " INTEGER PRIMARY KEY NOT NULL,"
                + DatabaseColumns.UserAddress.USER_ID + " INTEGER NOT NULL,"
                + DatabaseColumns.UserAddress.ADDRESS_1 + " TEXT,"
                + DatabaseColumns.UserAddress.ADDRESS_2 + " TEXT,"
                + DatabaseColumns.UserAddress.ADDRESS_3 + " TEXT,"
                + DatabaseColumns.UserAddress.CITY + " TEXT,"
                + DatabaseColumns.UserAddress.COUNTY + " TEXT,"
                + DatabaseColumns.UserAddress.COUNTRY + " TEXT,"
                + DatabaseColumns.UserAddress.PRIMARY_ADDRESS + " INTEGER NOT NULL"
                + ")";
        db.execSQL(createUserAddress);

        String createRecipe = "CREATE TABLE " + DatabaseTables.RECIPE + " ("
                + DatabaseColumns.ID + " INTEGER PRIMARY KEY NOT NULL,"
                + DatabaseColumns.Recipe.USER_ID + " INTEGER NOT NULL,"
                + DatabaseColumns.Recipe.RECIPE_TITLE + " TEXT NOT NULL,"
                + DatabaseColumns.Recipe.RECIPE_DESCRIPTION + " TEXT NOT NULL,"
                + DatabaseColumns.Recipe.TIMER_LENGTH + " INTEGER"
                + ")";
        db.execSQL(createRecipe);

        String createRecipeSteps = "CREATE TABLE " + DatabaseTables.RECIPE_STEPS + " ("
                + DatabaseColumns.ID + " INTEGER PRIMARY KEY NOT NULL,"
                + DatabaseColumns.RecipeSteps.RECIPE_ID + " INTEGER NOT NULL,"
                + DatabaseColumns.RecipeSteps.STEP_NUMBER + " INTEGER NOT NULL,"
                + DatabaseColumns.RecipeSteps.STEP_DESCRIPTION + " TEXT,"
                + DatabaseColumns.RecipeSteps.FINAL_STEP + " INTEGER NOT NULL,"
                + DatabaseColumns.RecipeSteps.TIMER + " INTEGER"
                + ")";
        db.execSQL(createRecipeSteps);

        String createRecipeStepsStarted = "CREATE TABLE " + DatabaseTables.RECIPE_STEPS_STARTED + " ("
                + DatabaseColumns.ID + " INETEGER PRIMARY KEY NOT NULL,"
                + DatabaseColumns.RecipeStepsStarted.RECIPE_STEP_ID + " INTEGER NOT NULL,"
                + DatabaseColumns.RecipeStepsStarted.USER_ID + " INTEGER NOT NULL,"
                + DatabaseColumns.RecipeStepsStarted.TIMESTAMP_STARTED + " INTEGER NOT NULL"
                + ")";
        db.execSQL(createRecipeStepsStarted);

        String createFavourites = "CREATE TABLE " + DatabaseTables.FAVOURITES + " ("
                + DatabaseColumns.ID + " INTEGER PRIMARY KEY NOT NULL,"
                + DatabaseColumns.Favourites.RECIPE_ID + " INTEGERS NOT NULL,"
                + DatabaseColumns.Favourites.USER_ID + " INTEGER NOT NULL"
                + ")";
        db.execSQL(createFavourites);

        String createRecipeComments = "CREATE TABLE " + DatabaseTables.RECIPE_COMMENTS + " ("
                + DatabaseColumns.ID + " INTEGER PRIMARY KEY NOT NULL,"
                + DatabaseColumns.RecipeComments.RECIPE_ID + " INTEGER NOT NULL,"
                + DatabaseColumns.RecipeComments.USER_ID + " INTEGER NOT NULL,"
                + DatabaseColumns.RecipeComments.COMMENT + " TEXT"
                + ")";
        db.execSQL(createRecipeComments);

        String createMealTimeSelection = "CREATE TABLE " + DatabaseTables.MEAL_TIME_SELECTION + " ("
                + DatabaseColumns.ID + " INTEGER PRIMARY KEY NOT NULL,"
                + DatabaseColumns.MealTimeSelection.RECIPE_ID + " INTEGER NOT NULL,"
                + DatabaseColumns.MealTimeSelection.USER_ID + " INTEGER NOT NULL,"
                + DatabaseColumns.MealTimeSelection.MEAL_TYPE + " INTEGER NOT NULL,"
                + DatabaseColumns.MealTimeSelection.DATE_SELECTED + " INTEGER(8) NOT NULL"
                + ")";
        db.execSQL(createMealTimeSelection);

    }

}
