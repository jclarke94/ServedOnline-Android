package com.servedonline.servedonline_android.Fragments.NewRecipe;

import android.annotation.SuppressLint;
import android.graphics.Rect;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.servedonline.servedonline_android.Entity.Ingredient;
import com.servedonline.servedonline_android.Entity.RecipeSteps;
import com.servedonline.servedonline_android.Listitem;
import com.servedonline.servedonline_android.MainActivity;
import com.servedonline.servedonline_android.Network.JSON.BaseResponse;
import com.servedonline.servedonline_android.Network.JSON.IDResponse;
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
    private static final int TYPE_LOADING = 3;

    private TextView tvStep;
    private EditText etDesc;
    private Button btnComplete, btnAddStep;
    private RecyclerView rvIngredients;
    private IngredientsAdapter adapter;
    private GridLayoutManager layoutManager;

    private int recipeId;
    private int stepNo;

    private int paddingMedium = 0;

    private Handler handler;

    private ArrayList<RecipeSteps> steps = new ArrayList<>();
    private ArrayList<Listitem> items = new ArrayList<>();
    private ArrayList<Ingredient> ingredients = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_creating_steps, container, false);

        handler = new Handler();

        tvStep = (TextView) v.findViewById(R.id.tvStepsTitle);
        etDesc = (EditText) v.findViewById(R.id.etDescription);
        btnComplete = (Button) v.findViewById(R.id.btnCompleteSteps);
        btnAddStep = (Button) v.findViewById(R.id.btnAddStep);
        rvIngredients = (RecyclerView) v.findViewById(R.id.rvIngredients);

        if (getArguments() != null) {
            if (getArguments().containsKey(RECIPE_KEY)) {
                recipeId = getArguments().getInt(RECIPE_KEY);
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
        }

        if (savedInstanceState != null) {
            items = savedInstanceState.getParcelableArrayList(ITEMS_KEY);
            recipeId = savedInstanceState.getInt(RECIPE_KEY);
            steps = savedInstanceState.getParcelableArrayList(STEPS_KEY);
            ingredients = savedInstanceState.getParcelableArrayList(INGREDIENTS_KEY);
        }

        paddingMedium = (int) getResources().getDimension(R.dimen.padding_medium);

        tvStep.setText("Step " + stepNo);

        layoutManager = new GridLayoutManager(getContext(), 2);
        adapter = new IngredientsAdapter();
        rvIngredients.setLayoutManager(layoutManager);
        rvIngredients.setAdapter(adapter);
        rvIngredients.addItemDecoration(new IngredientsDecoration());

        setupIngredients();

        btnAddStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).clearFocus();

                if (checkFieldsCompleted()) {
//                    saveStep();
                    sendStep(String.valueOf(etDesc.getText()), 0);
                } else {
                    Toast.makeText(getContext(), "Complete the description", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).clearFocus();

                if (checkFieldsCompleted()) {
//                    saveFinalStep();
                    sendStep(String.valueOf(etDesc.getText()), 1);
                } else {
                    Toast.makeText(getContext(), "Complete the description", Toast.LENGTH_SHORT).show();
                }
            }
        });

        v.setAlpha(0f);
        v.animate().setStartDelay(100).setDuration(300).alpha(1f);

        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(RECIPE_KEY, recipeId);
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

//    private void saveFinalStep() {
//
//        for (Listitem listitem : items) {
//            if (listitem.getViewType() == TYPE_INGREDIENT) {
//                IngredientItem item = (IngredientItem) listitem;
//                ingredients.add(new Ingredient(0, 0, stepNo, item.getIngredient()));
//            }
//        }
//
//        String desc = String.valueOf(etDesc.getText());
//        RecipeSteps step = new RecipeSteps(0, 0, stepNo, 1, 0, desc);
//        steps.add(step);
//
//        send();
//    }

//    private void send() {
//        Bundle args = new Bundle();
//        args.putInt(SendNewRecipeFragment.RECIPE_KEY, recipeId);
//        args.putParcelableArrayList(SendNewRecipeFragment.RECIPE_STEPS_KEY, steps);
//        args.putParcelableArrayList(SendNewRecipeFragment.INGREDIENTS_KEY, ingredients);
//
//        SendNewRecipeFragment fragment = new SendNewRecipeFragment();
//        fragment.setArguments(args);
//        ((MainActivity) getActivity()).navigate(fragment, BACKSTACK_TAG);
//    }

//    private void saveStep() {
//        for (Listitem listitem : items) {
//            if (listitem.getViewType() == TYPE_INGREDIENT) {
//                IngredientItem item = (IngredientItem) listitem;
//                ingredients.add(new Ingredient(0, 0, stepNo, item.getIngredient()));
//            }
//        }
//
//        String desc = String.valueOf(etDesc.getText());
//        RecipeSteps step = new RecipeSteps(0, 0, stepNo, 0, 0, desc);
//        steps.add(step);
//
//        createNewStep();
//    }

    private void createNewStep() {
        Bundle args = new Bundle();
        args.putInt(RECIPE_KEY, recipeId);
        args.putInt(STEP_NUMBER_KEY, stepNo + 1);
        args.putParcelableArrayList(STEPS_KEY, steps);
        args.putParcelableArrayList(INGREDIENTS_KEY, ingredients);

        CreatingStepsFragment fragment = new CreatingStepsFragment();
        fragment.setArguments(args);
        ((MainActivity) getActivity()).navigate(fragment, BACKSTACK_TAG);

    }

    private void finish() {
        CompleteFragment fragment = new CompleteFragment();
        ((MainActivity) getActivity()).navigate(fragment, BACKSTACK_TAG);
    }

    private void sendStep(final String desc, final int complete) {
        if (((MainActivity) getActivity()).getConnectionHelper().isNetworkAvailable()) {
            ((MainActivity) getActivity()).showBlocker();

            new Thread(new Runnable() {
                @Override
                public void run() {
                   final IDResponse response = ((MainActivity) getActivity()).getConnectionHelper().createNewRecipeStep(recipeId, desc, stepNo, complete, 0);

                   handler.post(new Runnable() {
                       @Override
                       public void run() {
                           if (response != null) {
                               if (response.isSuccess()) {
                                   ((MainActivity) getActivity()).hideBlocker();

                                   if (complete == 1) {
                                       finish();
                                   } else {
                                       createNewStep();
                                   }
                               }
                           }
                       }
                   });
                }
            }).start();
        }
    }

    private void addIngredient(final Ingredient ingredient) {
        if (((MainActivity) getActivity()).getConnectionHelper().isNetworkAvailable()) {
            addLoadingItem();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    final IDResponse response = ((MainActivity) getActivity()).getConnectionHelper().createRecipeIngredient(recipeId, stepNo, ingredient.getIngredient());

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (response != null) {
                                if (response.isSuccess()) {
                                    removeLoadingItem();

                                    items.add(0, new IngredientItem(new Ingredient(response.getData(), recipeId, stepNo, ingredient.getIngredient())));
                                    adapter.notifyItemChanged(0);
                                }
                            }
                        }
                    });
                }
            }).start();
        }
    }

    private void addLoadingItem() {
        items.add(0, new LoadingItem());
        adapter.notifyItemInserted(0);
    }

    private void removeLoadingItem() {
        if (items.get(0).getViewType() == TYPE_LOADING) {
            items.remove(0);
        }
    }

    private void setupIngredients() {
        if (items.size() == 0) {
            items.add(new AddItem());
        }
    }

    private void removeIngredient(final int id) {
        if (((MainActivity) getActivity()).getConnectionHelper().isNetworkAvailable()) {
            ((MainActivity) getActivity()).showBlocker();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    final BaseResponse response = ((MainActivity) getActivity()).getConnectionHelper().deleteIngredient(id);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (response != null)  {
                                if (response.isSuccess()) {
                                    ((MainActivity) getActivity()).hideBlocker();
                                    Listitem removingItem = null;
                                    for (Listitem listitem : items) {
                                        if (listitem.getViewType() == TYPE_INGREDIENT) {
                                            IngredientItem item = (IngredientItem) listitem;
                                            if (item.getIngredient().getId() == id ){
                                                removingItem = listitem;
                                            }
                                        }
                                    }
                                    removeItem(removingItem);
                                }
                            }
                        }
                    });
                }
            }).start();
        }
    }

    private void removeItem(Listitem listitem) {
        if (listitem != null) {
            items.remove(listitem);
            adapter.notifyDataSetChanged();
        }
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
                        Ingredient ing = new Ingredient(0, recipeId, stepNo,String.valueOf(aHolder.etAddIngredient.getText()));

                        addIngredient(ing);

                        aHolder.etAddIngredient.getText().clear();
                        ((MainActivity) getActivity()).clearFocus();
                    }
                });




            } else if (viewType == TYPE_INGREDIENT) {
                IngredientViewHolder iHolder = (IngredientViewHolder) holder;
                final IngredientItem item = (IngredientItem) items.get(position);

                iHolder.tvIngredient.setText(item.getIngredient().getIngredient());

                iHolder.llIngredient.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        removeIngredient(item.getIngredient().getId());
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
        private Ingredient ingredient;

        public IngredientItem(Ingredient ingredient) {
            this.ingredient = ingredient;
        }

        public Ingredient getIngredient() {
            return ingredient;
        }

        public void setIngredient(Ingredient ingredient) {
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
        public void writeToParcel(Parcel parcel, int i) {

        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        public LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class IngredientsDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            int viewType = parent.getLayoutManager().getItemViewType(view);

            if (viewType == TYPE_INGREDIENT || viewType == TYPE_ADD) {
                outRect.top = paddingMedium;
                outRect.bottom = paddingMedium;
                outRect.left = paddingMedium;
                outRect.right = paddingMedium;
            }
        }
    }

}
