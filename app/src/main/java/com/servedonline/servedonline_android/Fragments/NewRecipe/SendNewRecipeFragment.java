package com.servedonline.servedonline_android.Fragments.NewRecipe;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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

        ((MainActivity) getActivity()).hideTopbar();

        handler = new Handler();

        recipeId = 0;
        newRetryCounter = 0;
        oldRetryCounter = 0;

        if (getArguments() != null) {
            recipeId = getArguments().getInt(RECIPE_KEY);
            ingredients = getArguments().getParcelableArrayList(INGREDIENTS_KEY);
            recipeSteps = getArguments().getParcelableArrayList(RECIPE_STEPS_KEY);
        }

        if (savedInstanceState != null) {
            recipeId = savedInstanceState.getInt(RECIPE_ID_KEY);
            newRetryCounter = savedInstanceState.getInt(RETRY_COUNTER_KEY);
            oldRetryCounter = savedInstanceState.getInt(OLD_RETRY_COUNTER_KEY);
        }

//        createRecipe();
        sendRecipeSteps(recipeId);

        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(RECIPE_ID_KEY, recipeId);
        outState.putInt(RETRY_COUNTER_KEY, newRetryCounter);
        outState.putInt(OLD_RETRY_COUNTER_KEY, oldRetryCounter);
    }

//    private void createRecipe() {
//        if (((MainActivity) getActivity()).getConnectionHelper().isNetworkAvailable()) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    sendRecipe();
//
//                }
//            }).start();
//        }
//    }

//    private void sendRecipe() {
//        if (recipe != null) {
//            final IDResponse response = ((MainActivity) getActivity()).getConnectionHelper().createNewRecipe(recipe.getUserId(), recipe.getRecipeTitle(), recipe.getRecipeDescription(), 0);
//
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    if (response != null) {
//                        if (response.isSuccess()) {
//                            Log.d("send", "recipe response Id = " + response.getData());
//                            sendRecipeSteps(response.getData());
////                            sendRecipeIngredients(response.getData());
//                        }
//                    }
//                }
//            });
//        }
//    }

    private void sendRecipeSteps(final int recipeId) {

        int x = 0;

        Log.d("send", "recipeSteps.size = " + recipeSteps.size());

        while (x < recipeSteps.size()) {

            Log.d("send", "x = " + x);
            final RecipeSteps step = recipeSteps.get(x);

            final Handler sHandler = new Handler();

            new Thread(new Runnable() {
                @Override
                public void run() {

//            sendStep(recipeId, step, sHandler);

                    final IDResponse response = ((MainActivity) getActivity()).getConnectionHelper().createNewRecipeStep(recipeId, step.getStepDescription(), step.getStepNumber(), step.getFinalStep(), step.getTimer());

                    sHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (response != null) {
                                if (!response.isSuccess()) {
                                    Log.d("send", "Step not successful");


                                    newRetryCounter++;
                                } else {
                                    Log.d("send", "step successful. step ID = " + response.getData());
                                }
                            } else {
                                Log.d("send", "Step == null");
                                newRetryCounter++;
                            }
                        }
                    });
                }
            }).start();

            if (newRetryCounter > oldRetryCounter) {

                Log.d("send", "retry counter activated");
                oldRetryCounter = newRetryCounter;
            } else {

                Log.d("send", "x++");
                x++;
            }
        }

        sendRecipeIngredients(recipeId);
    }

    private void sendRecipeIngredients(final int recipeId) {

        int x = 0;

        while (x < ingredients.size()) {
            Log.d("send", "Ingredients x = " + x);
            final Ingredient ing = ingredients.get(x);

            final Handler iHandler = new Handler();

            new Thread(new Runnable() {
                @Override
                public void run() {


//            sendIngredient(recipeId, ing, iHandler);

                    final IDResponse response = ((MainActivity) getActivity()).getConnectionHelper().createRecipeIngredient(recipeId, ing.getStepNumber(), ing.getIngredient());

                    iHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (response != null) {
                                if (!response.isSuccess()) {
                                    Log.d("send", "Ingredients not successful");
                                    newRetryCounter++;
                                } else {
                                    Log.d("send", "Ingredient successful");
                                }
                            } else {
                                Log.d("send", "Ingredients == null");
                                newRetryCounter++;
                            }
                        }
                    });
                }
            }).start();





            if (newRetryCounter > oldRetryCounter) {

                Log.d("send", "Ingredients retry counter activated");
                oldRetryCounter = newRetryCounter;
            } else {
                Log.d("send", "Ingredients x++");
                x++;
            }
        }

        CompleteFragment fragment = new CompleteFragment();
        ((MainActivity) getActivity()).navigate(fragment, BACKSTACK_TAG);
    }

    private void sendStep(final int recipeId, final RecipeSteps recipeStep, Handler sHandler) {
        final IDResponse response = ((MainActivity) getActivity()).getConnectionHelper().createNewRecipeStep(recipeId, recipeStep.getStepDescription(), recipeStep.getStepNumber(), recipeStep.getFinalStep(), recipeStep.getTimer());

        sHandler.post(new Runnable() {
            @Override
            public void run() {
                if (response != null) {
                    if (!response.isSuccess()) {
                        Log.d("send", "Step not successful");
                        newRetryCounter++;
                    } else {
                        Log.d("send", "step successful. step ID = " + response.getData());
                    }
                } else {
                    Log.d("send", "Step == null");
                    newRetryCounter++;
                }
            }
        });
    }

    private void sendIngredient(final int recipeId, final Ingredient ingredient, Handler iHandler) {
        final IDResponse response = ((MainActivity) getActivity()).getConnectionHelper().createRecipeIngredient(recipeId, ingredient.getStepNumber(), ingredient.getIngredient());

        iHandler.post(new Runnable() {
            @Override
            public void run() {
                if (response != null) {
                    if (!response.isSuccess()) {
                        Log.d("send", "Ingredients not successful");
                        newRetryCounter++;
                    } else {
                        Log.d("send", "Ingredient successful");
                    }
                } else {
                    Log.d("send", "Ingredients == null");
                    newRetryCounter++;
                }
            }
        });
    }
}
