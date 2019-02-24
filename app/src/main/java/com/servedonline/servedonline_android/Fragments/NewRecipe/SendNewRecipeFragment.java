package com.servedonline.servedonline_android.Fragments.NewRecipe;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.servedonline.servedonline_android.Entity.Ingredient;
import com.servedonline.servedonline_android.Entity.Recipe;
import com.servedonline.servedonline_android.Entity.RecipeSteps;
import com.servedonline.servedonline_android.MainActivity;
import com.servedonline.servedonline_android.Network.JSON.IDResponse;
import com.servedonline.servedonline_android.R;

import java.util.ArrayList;

public class SendNewRecipeFragment extends Fragment {

    public static final String RECIPE_KEY = "_recipeKey";
    public static final String INGREDIENTS_KEY = "_ingredientsKey";
    public static final String RECIPE_STEPS_KEY = "_recipeStepsKey";
    public static final String BACKSTACK_TAG = "_sendNewRecipeFragment";
    private static final String RECIPE_ID_KEY = "_recipeIdKey";
    private static final String RETRY_COUNTER_KEY = "_retryCounterKey";
    private static final String OLD_RETRY_COUNTER_KEY = "_oldRetryCounterKey";

    private Handler handler;

    private Recipe recipe;
    private ArrayList<Ingredient> ingredients = new ArrayList<>();
    private ArrayList<RecipeSteps> recipeSteps = new ArrayList<>();

    private int recipeId;
    private int newRetryCounter;
    private int oldRetryCounter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_send_new_recipe, container, false);

        handler = new Handler();

        recipeId = 0;
        newRetryCounter = 0;
        oldRetryCounter = 0;

        if (getArguments() != null) {
            recipe = getArguments().getParcelable(RECIPE_KEY);
            ingredients = getArguments().getParcelableArrayList(INGREDIENTS_KEY);
            recipeSteps = getArguments().getParcelableArrayList(RECIPE_KEY);
        }

        if (savedInstanceState != null) {
            recipeId = savedInstanceState.getInt(RECIPE_ID_KEY);
            newRetryCounter = savedInstanceState.getInt(RETRY_COUNTER_KEY);
            oldRetryCounter = savedInstanceState.getInt(OLD_RETRY_COUNTER_KEY);
        }

        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(RECIPE_ID_KEY, recipeId);
        outState.putInt(RETRY_COUNTER_KEY, newRetryCounter);
        outState.putInt(OLD_RETRY_COUNTER_KEY, oldRetryCounter);
    }

    private void createRecipe() {
        if (((MainActivity) getActivity()).getConnectionHelper().isNetworkAvailable()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    sendRecipe();

                    //todo complete
                }
            }).start();
        }
    }

    private void sendRecipe() {
        if (recipe != null) {
            final IDResponse response = ((MainActivity) getActivity()).getConnectionHelper().createNewRecipe(recipe.getUserId(), recipe.getRecipeTitle(), recipe.getRecipeDescription(), 0);

            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (response != null) {
                        if (response.isSuccess()) {
                            sendRecipeSteps(response.getData());
                            sendRecipeIngredients(response.getData());
                        }
                    }
                }
            });
        }
    }

    private void sendRecipeSteps(final int recipeId) {
        int x = 0;

        while (x < recipeSteps.size()) {
            RecipeSteps step = recipeSteps.get(x);

            sendStep(recipeId, step);

            if (newRetryCounter > oldRetryCounter) {
                oldRetryCounter = newRetryCounter;
            } else {
                x++;
            }
        }
    }

    private void sendRecipeIngredients(final int recipeId) {
        int x = 0;

        while (x < ingredients.size()) {
            Ingredient ing = ingredients.get(x);

            sendIngredient(recipeId, ing);

            if (newRetryCounter > oldRetryCounter) {
                oldRetryCounter = newRetryCounter;
            } else {
                x++;
            }
        }
    }

    private void sendStep(final int recipeId, final RecipeSteps recipeStep) {
        final IDResponse response = ((MainActivity) getActivity()).getConnectionHelper().createNewRecipeStep(recipeId, recipeStep.getStepDescription(), recipeStep.getStepNumber(), recipeStep.getFinalStep(), recipeStep.getTimer());

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (response != null) {
                    if (!response.isSuccess()) {
                        newRetryCounter++;
                    }
                } else {
                    newRetryCounter++;
                }
            }
        });
    }

    private void sendIngredient(final int recipeId, final Ingredient ingredient) {
        final IDResponse response = ((MainActivity) getActivity()).getConnectionHelper().createRecipeIngredient(recipeId, ingredient.getStepNumber(), ingredient.getIngredient());

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (response != null) {
                    if (!response.isSuccess()) {
                        newRetryCounter++;
                    }
                } else {
                    newRetryCounter++;
                }
            }
        });
    }
}
