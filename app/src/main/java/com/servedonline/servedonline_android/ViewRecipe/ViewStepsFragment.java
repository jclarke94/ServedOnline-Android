package com.servedonline.servedonline_android.ViewRecipe;

import android.annotation.SuppressLint;
import android.os.Bundle;
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

import com.servedonline.servedonline_android.Entity.Ingredient;
import com.servedonline.servedonline_android.Entity.RecipeSteps;
import com.servedonline.servedonline_android.Fragments.Home.HomeFragment;
import com.servedonline.servedonline_android.Listitem;
import com.servedonline.servedonline_android.MainActivity;
import com.servedonline.servedonline_android.R;

import java.util.ArrayList;

public class ViewStepsFragment extends Fragment {

    public static final String BACKSTACK_TAG = "_viewStepsFragment";
    public static final String INGREDIENTS_KEY = "_ingredientsKey";
    public static final String RECIPE_STEP_KEY = "_recipeStepKey";
    public static final String STEP_NUMBER_KEY = "_stepNumberKey";
    private static final String ITEMS_KEY = "_itemsKey";

    private static final int TYPE_INGREDIENT = 1;
    private static final int TYPE_LOADING = 0;

    private TextView tvStepTitle, tvDescription;
    private Button btnCancel, btnNext;
    private RecyclerView rvIngredients;

    private GridLayoutManager layoutManager;
    private IngredientsAdapter adapter;

    private int stepNo;
    private RecipeSteps step;

    private ArrayList<RecipeSteps> steps = new ArrayList<>();
    private ArrayList<Ingredient> ingredients = new ArrayList<>();
    private ArrayList<Listitem> items = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_view_steps, container, false);

        tvStepTitle = v.findViewById(R.id.tvStepTitle);
        tvDescription = v.findViewById(R.id.tvStepDescription);
        btnCancel = v.findViewById(R.id.btnCancel);
        btnNext = v.findViewById(R.id.btnNext);
        rvIngredients = v.findViewById(R.id.rvIngredients);

        stepNo = 1;

        if (getArguments() != null) {
            ingredients = getArguments().getParcelableArrayList(INGREDIENTS_KEY);
            stepNo = getArguments().getInt(STEP_NUMBER_KEY);
            steps = getArguments().getParcelableArrayList(RECIPE_STEP_KEY);
        }

        if (savedInstanceState != null) {
            items = savedInstanceState.getParcelableArrayList(ITEMS_KEY);
        }

        step = steps.get(stepNo - 1);

        layoutManager = new GridLayoutManager(getContext(), 1);
        adapter = new IngredientsAdapter();
        rvIngredients.setAdapter(adapter);
        rvIngredients.setLayoutManager(layoutManager);

        tvStepTitle.setText("STEP " + stepNo);
        tvDescription.setText(step.getStepDescription());

        if (items.size() == 0) {
            setupIngredientsList();
        }

        if (step.getFinalStep() == 1) {
            btnNext.setText(getResources().getString(R.string.view_steps_finish));
        } else {
            btnNext.setText(getResources().getString(R.string.view_steps_next));
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (step.getFinalStep() == 1) {
                    HomeFragment fragment = new HomeFragment();
                    ((MainActivity) getActivity()).voidBackstackAndNavigate(HomeFragment.BACKSTACK_TAG, fragment, HomeFragment.BACKSTACK_TAG);
                } else {
                    Bundle args = new Bundle();
                    args.putParcelableArrayList(RECIPE_STEP_KEY, steps);
                    args.putParcelableArrayList(INGREDIENTS_KEY, ingredients);
                    args.putInt(STEP_NUMBER_KEY, stepNo + 1);

                    ViewStepsFragment fragment = new ViewStepsFragment();
                    fragment.setArguments(args);
                    ((MainActivity) getActivity()).navigate(fragment, BACKSTACK_TAG);
                }
            }
        });

        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(ITEMS_KEY, items);
    }

    private void setupIngredientsList() {
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getStepNumber() == stepNo) {
                items.add(new IngredientsItem(ingredient));
            }
        }

        adapter.notifyDataSetChanged();
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
