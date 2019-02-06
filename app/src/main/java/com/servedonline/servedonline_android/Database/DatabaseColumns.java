package com.servedonline.servedonline_android.Database;

public class DatabaseColumns {

    public static final String ID = "_id";

    public static class User {
        public static final String FIRST_NAME = "_fName";
        public static final String LAST_NAME = "_lName";
        public static final String DISPLAY_NAME = "_displayName";
        public static final String EMAIL = "_email";
        public static final String PASSWORD = "_password";
    }

    public static class UserAddress {
        public static final String USER_ID = "_userId";
        public static final String ADDRESS_1 = "_address1";
        public static final String ADDRESS_2 = "_address2";
        public static final String ADDRESS_3 = "_address3";
        public static final String CITY = "_city";
        public static final String COUNTY = "_county";
        public static final String COUNTRY = "_country";
        public static final String PRIMARY_ADDRESS = "_primaryAddress";
    }

    public static class Recipe {
        public static final String USER_ID = "_userId";
        public static final String RECIPE_TITLE = "_recipeTitle";
        public static final String RECIPE_DESCRIPTION = "_recipeDescription";
        public static final String TIMER_LENGTH = "_timerLength";
    }

    public static class RecipeSteps {
        public static final String RECIPE_ID = "_recipeId";
        public static final String STEP_DESCRIPTION = "_stepDescription";
        public static final String STEP_NUMBER = "_stepNumber";
        public static final String FINAL_STEP = "_finalStep";
        public static final String TIMER = "_timer";
    }

    public static class RecipeStepsStarted {
        public static final String RECIPE_STEP_ID = "_recipeStepId";
        public static final String USER_ID = "_userId";
        public static final String TIMESTAMP_STARTED = "_timestampStarted";
    }

    public static class Favourites {
        public static final String USER_ID = "_userId";
        public static final String RECIPE_ID = "_recipeId";
    }

    public static class RecipeComments {
        public static final String RECIPE_ID = "_recipeId";
        public static final String USER_ID = "_userId";
        public static final String COMMENT = "_comment";
    }

    public static class MealTimeSelection {
        public static final String USER_ID = "_userId";
        public static final String RECIPE_ID = "_recipeId";
        public static final String MEAL_TYPE = "_mealType";
        public static final String DATE_SELECTED = "_dateSelected";
    }

}
