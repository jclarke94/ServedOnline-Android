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
import android.widget.Toast;

import com.servedonline.servedonline_android.Entity.Ingredient;
import com.servedonline.servedonline_android.Entity.Recipe;
import com.servedonline.servedonline_android.Entity.RecipeSteps;
import com.servedonline.servedonline_android.Listitem;
import com.servedonline.servedonline_android.MainActivity;
import com.servedonline.servedonline_android.R;

import java.util.ArrayList;

public class CreatingStepsFragment extends Fragment {

    private static final String BACKSTACK_TAG = "_creatingStepsFragment";
    public static final String RECIPE_KEY = "_recipe";
    public static final String STEP_NUMBER_KEY = "_stepNumber";
    private static final String ITEMS_KEY = "_items";
    private static final String STEPS_KEY = "_steps";
    private static final String INGREDIENTS_KEY = "_ingredients";

    private static final int TYPE_ADD = 1;
    private static final int TYPE_INGREDIENT = 2;

    private TextView tvStep;
    private EditText etDesc;
    private Button btnComplete, btnAddStep;
    private RecyclerView rvIngredients;
    private IngredientsAdapter adapter;
    private GridLayoutManager layoutManager;

    private Recipe recipe;
    private int stepNo;

    private ArrayList<RecipeSteps> steps = new ArrayList<>();
    private ArrayList<Listitem> items = new ArrayList<>();
    private ArrayList<Ingredient> ingredients = new ArrayList<>();

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

        if (getArguments().containsKey(RECIPE_KEY)) {
            recipe = getArguments().getParcelable(RECIPE_KEY);
        }
        if (getArguments().containsKey(STEP_NUMBER_KEY)) {
            stepNo = getArguments().getInt(STEP_NUMBER_KEY);
        }
        if (getArguments().containsKey(STEPS_KEY)) {
            steps = getArguments().getParcelableArrayList(STEPS_KEY);
        }
        if (getArguments().containsKey(INGREDIENTS_KEY)) {
            ingredients = getArguments().getParcelableArrayList(INGREDIENTS_KEY);
        }

        if (savedInstanceState != null) {
            items = savedInstanceState.getParcelableArrayList(ITEMS_KEY);
            recipe = savedInstanceState.getParcelable(RECIPE_KEY);
            steps = savedInstanceState.getParcelableArrayList(STEPS_KEY);
            ingredients = savedInstanceState.getParcelableArrayList(INGREDIENTS_KEY);
        }

        tvStep.setText("Step " + stepNo);

        layoutManager = new GridLayoutManager(getContext(), 2);
        adapter = new IngredientsAdapter();
        rvIngredients.setLayoutManager(layoutManager);
        rvIngredients.setAdapter(adapter);

        setupIngredients();

        btnAddStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkFieldsCompleted()) {
                    saveStep();
                } else {
                    Toast.makeText(getContext(), "Complete the description", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo 
            }
        });

        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(RECIPE_KEY, recipe);
        outState.putParcelableArrayList(ITEMS_KEY, items);
        outState.putParcelableArrayList(STEPS_KEY, steps);
        outState.putParcelableArrayList(INGREDIENTS_KEY, ingredients);
    }

    private boolean checkFieldsCompleted() {
        String desc = String.valueOf(etDesc.getText());
        if (desc == null || desc.matches("")) {
            return false;
        } else {
            return true;
        }
    }

    private void saveStep() {
        for (Listitem listitem : items) {
            if (listitem.getViewType() == TYPE_INGREDIENT) {
                IngredientItem item = (IngredientItem) listitem;
                ingredients.add(new Ingredient(0, 0, stepNo, item.getIngredient()));
            }
        }

        createNewStep();
    }

    private void createNewStep() {
        Bundle args = new Bundle();
        args.putParcelable(RECIPE_KEY, recipe);
        args.putInt(STEP_NUMBER_KEY, stepNo);
        args.putParcelableArrayList(STEPS_KEY, steps);
        args.putParcelableArrayList(INGREDIENTS_KEY, ingredients);

        CreatingStepsFragment fragment = new CreatingStepsFragment();
        ((MainActivity) getActivity()).navigate(fragment, BACKSTACK_TAG);

    }

    private void setupIngredients() {
        items.add(new AddItem());
    }

    private void addIngredient(String ingredient) {
        items.add(0, new IngredientItem(ingredient, stepNo));
        adapter.notifyItemInserted(0);
    }

    private void removeIngredient(int position) {
        items.remove(position);
        adapter.notifyItemRemoved(position);
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
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
            int viewType = items.get(position).getViewType();

            if (viewType == TYPE_ADD) {
                final AddViewHolder aHolder = (AddViewHolder) holder;
                AddItem item = (AddItem) items.get(position);

                aHolder.btnAddIngredient.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addIngredient(String.valueOf(aHolder.etAddIngredient.getText()));

                    }
                });




            } else if (viewType == TYPE_INGREDIENT) {
                IngredientViewHolder iHolder = (IngredientViewHolder) holder;
                IngredientItem item = (IngredientItem) items.get(position);

                iHolder.tvIngredient.setText(item.getIngredient());

                iHolder.llIngredient.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        removeIngredient(position);
                    }
                });
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
        private String ingredient;
        private int stepNumber;

        public IngredientItem(String ingredient, int stepNumber) {
            this.ingredient = ingredient;
            this.stepNumber = stepNumber;
        }

        public String getIngredient() {
            return ingredient;
        }

        public void setIngredient(String ingredient) {
            this.ingredient = ingredient;
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
        public void writeToParcel(Parcel parcel, int i) {

        }
    }

    private class IngredientViewHolder extends RecyclerView.ViewHolder{

        public LinearLayout llIngredient;
        public TextView tvIngredient;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            llIngredient = itemView.findViewById(R.id.llIngredient);
            tvIngredient = itemView.findViewById(R.id.tvIngredient);
        }
    }

}
