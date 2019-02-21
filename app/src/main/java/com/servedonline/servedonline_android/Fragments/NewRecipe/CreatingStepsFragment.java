package com.servedonline.servedonline_android.Fragments.NewRecipe;

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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.servedonline.servedonline_android.Entity.Recipe;
import com.servedonline.servedonline_android.Entity.RecipeSteps;
import com.servedonline.servedonline_android.Listitem;
import com.servedonline.servedonline_android.R;

import java.util.ArrayList;

public class CreatingStepsFragment extends Fragment {

    private static final String BACKSTACK_TAG = "_creatingStepsFragment";
    private static final String RECIPE_KEY = "_recipe";
    private static final String ITEMS = "_items";
    private static final String STEPS = "_steps";

    private static final int TYPE_ADD = 1;
    private static final int TYPE_INGREDIENT = 2;

    private TextView tvStep;
    private EditText etDesc;
    private Button btnComplete, btnAddStep;
    private RecyclerView rvIngredients;
    private IngredientsAdapter adapter;
    private GridLayoutManager layoutManager;

    private Recipe recipe;


    private ArrayList<RecipeSteps> steps = new ArrayList<>();
    private ArrayList<Listitem> items = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_creating_steps, container, false);

        tvStep = (TextView) v.findViewById(R.id.tvStepsTitle);
        etDesc = (EditText) v.findViewById(R.id.etDescription);
        btnComplete = (Button) v.findViewById(R.id.btnCompleteSteps);
        btnAddStep = (Button) v.findViewById(R.id.btnAddStep);
        rvIngredients = (RecyclerView) v.findViewById(R.id.rvIngredients);

        layoutManager = new GridLayoutManager(getContext(), 2);
        adapter = new IngredientsAdapter();
        rvIngredients.setLayoutManager(layoutManager);
        rvIngredients.setAdapter(adapter);



        if (getArguments() != null) {
            recipe = getArguments().getParcelable(NewRecipeFragment.RECIPE_KEY);
        }

        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(RECIPE_KEY, recipe);
        outState.putParcelableArrayList(ITEMS, items);
        outState.putParcelableArrayList(STEPS, steps);
    }

    private void checkFieldsCompleted() {

    }

    public class IngredientsAdapter extends RecyclerView.Adapter {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == TYPE_ADD) {
                View v = getActivity().getLayoutInflater().inflate(R.layout.listitem_add_ingredient, parent, false);
                return new AddViewHolder(v);
            } else {
                View v = getActivity().getLayoutInflater().inflate(R.layout.listitem_ingredient, parent, false);
                return new IngredientViewHolder(v);
            }

        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            int viewType = items.get(position).getViewType();

            if (viewType == TYPE_ADD) {
                AddViewHolder aHolder = (AddViewHolder) holder;
                AddItem item = (AddItem) items.get(position);


            } else if (viewType == TYPE_INGREDIENT) {

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
    private class AddItem extends Listitem {

        @Override
        public int getViewType() {
            return TYPE_ADD;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {

        }
    }

    private class AddViewHolder extends RecyclerView.ViewHolder {

        public EditText etAddIngredient;
        public ImageButton btnAddIngredient;

        public AddViewHolder(View itemView) {
            super(itemView);

            etAddIngredient = itemView.findViewById(R.id.etAddIngredient);
            btnAddIngredient = itemView.findViewById(R.id.btnAddIngredient);
        }
    }

    @SuppressLint("ParcelCreator")
    private class IngredientItem extends Listitem {



        @Override
        public int getViewType() {
            return TYPE_INGREDIENT;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {

        }
    }

    private class IngredientViewHolder extends RecyclerView.ViewHolder{

        public LinearLayout llIngredient;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            llIngredient = itemView.findViewById(R.id.llIngredient);
        }
    }

}
