package com.servedonline.servedonline_android.ViewRecipe;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.servedonline.servedonline_android.Entity.Ingredient;
import com.servedonline.servedonline_android.Entity.Recipe;
import com.servedonline.servedonline_android.Entity.RecipeSteps;
import com.servedonline.servedonline_android.Listitem;
import com.servedonline.servedonline_android.MainActivity;
import com.servedonline.servedonline_android.Network.JSON.IngredientsListResponse;
import com.servedonline.servedonline_android.Network.JSON.RecipeStepsResponse;
import com.servedonline.servedonline_android.R;

import java.util.ArrayList;
import java.util.Arrays;

public class ViewRecipeFragment extends Fragment {

    public static final String BACKSTACK_TAG = "_viewRecipeFragment";
    public static final String RECIPE_KEY = "_recipe";
    private static final String ITEMS_KEY = "_items";

    private static final int TYPE_INGREDIENT = 1;
    private static final int TYPE_LOADING = 0;

    private TextView tvRecipeTitle, tvRecipeDescription, tvUserName;
    private Button btnStart, btnSave;
    private RecyclerView rvIngredients;

    private Recipe recipe;
    private Ingredient[] ingredients;

    private Handler handler;
    private Handler stepsHandler;

    private IngredientsAdapter adapter;
    private GridLayoutManager layoutManager;

    private ArrayList<Listitem> items = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_view_recipe, container, false);

        tvRecipeTitle = v.findViewById(R.id.tvRecipeTitle);
        tvRecipeDescription = v.findViewById(R.id.tvRecipeDescription);
        tvUserName = v.findViewById(R.id.tvDisplayName);
        btnStart = v.findViewById(R.id.btnStart);
        btnSave = v.findViewById(R.id.btnSaveRecipe);
        rvIngredients = v.findViewById(R.id.rvIngredients);

        handler = new Handler();
        stepsHandler = new Handler();

        if (getArguments() != null) {
            recipe = getArguments().getParcelable(RECIPE_KEY);
        }
        if (savedInstanceState != null) {
            items = savedInstanceState.getParcelableArrayList(ITEMS_KEY);
        }

        layoutManager = new GridLayoutManager(getContext(), 1);
        adapter = new IngredientsAdapter();
        rvIngredients.setLayoutManager(layoutManager);
        rvIngredients.setAdapter(adapter);

        tvUserName.setText(recipe.getUserName());
        tvRecipeTitle.setText(recipe.getRecipeTitle());
        tvRecipeDescription.setText(recipe.getRecipeDescription());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "OOOOPS! This is still in development...", Toast.LENGTH_LONG).show();

                //todo
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendStartToken();
            }
        });

        if (items.size() == 0) {
            getIngredientsList();
        }

        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(ITEMS_KEY, items);
    }

    private void sendStartToken() {

        //todo activate Alexa


        getSteps();
    }

    private void passToSteps(RecipeSteps[] steps) {
        ArrayList<RecipeSteps> stepsList = new ArrayList<>(Arrays.asList(steps));
        ArrayList<Ingredient> ingredientsList = new ArrayList<Ingredient>(Arrays.asList(ingredients));

        ((MainActivity) getActivity()).hideBlocker();

        Bundle args = new Bundle();
        args.putInt(ViewStepsFragment.STEP_NUMBER_KEY, 1);
        args.putParcelableArrayList(ViewStepsFragment.INGREDIENTS_KEY, ingredientsList);
        args.putParcelableArrayList(ViewStepsFragment.RECIPE_STEP_KEY, stepsList);

        ViewStepsFragment fragment = new ViewStepsFragment();
        fragment.setArguments(args);
        ((MainActivity) getActivity()).navigate(fragment, BACKSTACK_TAG);
    }

    private void getSteps() {
        if (((MainActivity) getActivity()).getConnectionHelper().isNetworkAvailable()) {
            ((MainActivity) getActivity()).showBlocker();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    final RecipeStepsResponse response = ((MainActivity) getActivity()).getConnectionHelper().getRecipeSteps(recipe.getId());

                    stepsHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (response != null) {
                                if (response.isSuccess()) {
                                    passToSteps(response.getData());
                                }
                            }
                        }
                    });
                }
            }).start();
        }
    }

    private void getIngredientsList() {
        items.add(new LoadingItem());
        adapter.notifyDataSetChanged();

        if (((MainActivity) getActivity()).getConnectionHelper().isNetworkAvailable()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final IngredientsListResponse response = ((MainActivity) getActivity()).getConnectionHelper().getIngredients(recipe.getId());

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            items.clear();
                            adapter.notifyDataSetChanged();

                            if (response != null) {
                                if (response.isSuccess()) {
                                    ingredients = response.getData();
                                    for (Ingredient ingredient : response.getData()) {
                                        items.add(new IngredientsItem(ingredient));
                                    }

                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
                }
            }).start();
        }
    }

    private class IngredientsAdapter extends RecyclerView.Adapter {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == TYPE_INGREDIENT) {
                View v = getActivity().getLayoutInflater().inflate(R.layout.listitem_view_ingredient, parent, false);
                return new IngredientViewHolder(v);
            } else {
                View v = getActivity().getLayoutInflater().inflate(R.layout.listitem_generic_loading, parent, false);
                return new LoadingViewHolder(v);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            int viewType = items.get(position).getViewType();

            if (viewType == TYPE_INGREDIENT) {
                IngredientsItem  item = (IngredientsItem) items.get(position);
                IngredientViewHolder iHolder = (IngredientViewHolder) holder;

                iHolder.tvIngredient.setText(item.getIngredient().getIngredient());

            } else if (viewType == TYPE_LOADING) {
                LoadingItem item = (LoadingItem) items.get(position);
                LoadingViewHolder lHolder = (LoadingViewHolder) holder;
            }
        }

        @Override
        public int getItemViewType(int position) {
            super.getItemViewType(position);
            return items.get(position).getViewType();
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    @SuppressLint("ParcelCreator")
    private class IngredientsItem extends Listitem {

        private Ingredient ingredient;

        public IngredientsItem(Ingredient ingredient) {
            this.ingredient = ingredient;
        }

        public Ingredient getIngredient() {
            return ingredient;
        }

        @Override
        public int getViewType() {
            return TYPE_INGREDIENT;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

        }
    }

    private class IngredientViewHolder extends RecyclerView.ViewHolder {

        public TextView tvIngredient;

        public IngredientViewHolder(View itemView) {
            super(itemView);

            tvIngredient = itemView.findViewById(R.id.tvIngredient);
        }
    }

    @SuppressLint("ParcelCreator")
    private class LoadingItem extends Listitem {

        @Override
        public int getViewType() {
            return TYPE_LOADING;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        public LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }
}
