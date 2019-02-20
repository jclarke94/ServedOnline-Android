package com.servedonline.servedonline_android.Fragments.NewRecipe;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.servedonline.servedonline_android.Entity.Recipe;
import com.servedonline.servedonline_android.Entity.RecipeSteps;
import com.servedonline.servedonline_android.R;

import java.util.ArrayList;

public class CreatingStepsFragment extends Fragment {

    private static final String BACKSTACK_TAG = "_creatingStepsFragment";
    private static final String RECIPE_KEY = "_recipe";
    private static final String ITEMS = "_items";

    private TextView tvStep;
    private EditText etDesc;
    private Button btnComplete, btnAddStep;
    private RecyclerView rvIngredients;

    private Recipe recipe;

    private ArrayList<RecipeSteps> items = new ArrayList<>();

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
    }

    private void checkFieldsCompleted() {

    }
}
