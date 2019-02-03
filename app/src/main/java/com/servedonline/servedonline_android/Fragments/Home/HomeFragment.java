package com.servedonline.servedonline_android.Fragments.Home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.servedonline.servedonline_android.Entity.Recipe;
import com.servedonline.servedonline_android.Listitem;
import com.servedonline.servedonline_android.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    public static final String BACKSTACK_TAG = "_homeLandingFragment";

    private static final String ARG_ITEMS = "_items";

    public static final int TYPE_WRITE_NEW = 0;
    public static final int TYPE_RECIPE_CARD = 1;
    public static final int TYPE_FILTER_SELECTION = 2;


    private RecyclerView rvFeed;
    private GridLayoutManager layoutManager;
    private FeedAdapter adapter;

    private ArrayList<Listitem> items = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        rvFeed = (RecyclerView) v.findViewById(R.id.rvHomeFeed);

        if (savedInstanceState != null) {
            items = savedInstanceState.getParcelableArrayList(ARG_ITEMS);
        }

        layoutManager = new GridLayoutManager(getContext(), 1);
        adapter = new FeedAdapter();
        rvFeed.setAdapter(adapter);
        rvFeed.setLayoutManager(layoutManager);



        return v;
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(ARG_ITEMS, items);
    }

    private void setupTopButtons() {
        items.add(new FilterItem(getString(R.string.home_fragment_tab_discover), getString(R.string.home_fragment_tab_foodies), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo change recyclerView items to reflect foodies items.
                setupFoodiesItems();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo change recyclerView items to reflect discover items.
                setupDiscoverItems();
            }
        }));
    }

    private void setupDiscoverItems() {
        //todo
    }

    private void setupFoodiesItems() {
        //todo
    }

    public class FeedAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_WRITE_NEW) {
                View v = getActivity().getLayoutInflater().inflate(R.layout.listitem_write_new, parent, false);
                return new WriteNewViewHolder(v);
            } else if (viewType == TYPE_RECIPE_CARD) {
                View v = getActivity().getLayoutInflater().inflate(R.layout.listitem_recipe_card, parent, false);
                return new RecipeCardViewHolder(v);
            } else {
                View v = getActivity().getLayoutInflater().inflate(R.layout.listitem_filter, parent, false);
                return new FilterViewHolder(v);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            int viewType = items.get(position).getViewType();

            if(viewType == TYPE_WRITE_NEW) {
                WriteNewViewHolder wHolder = (WriteNewViewHolder) holder;
                WriteNewItem item = (WriteNewItem) items.get(position);

                wHolder.title.setText(item.buttonTitle);

            } else if (viewType == TYPE_RECIPE_CARD) {
                final RecipeCardViewHolder rHolder = (RecipeCardViewHolder) holder;
                final RecipeCardItem item = (RecipeCardItem) items.get(position);

                rHolder.recipeTitle.setText(item.getRecipe().getRecipeName());
                rHolder.recipeDescription.setText(item.getRecipe().getRecipeDescription());
                rHolder.displayName.setText(item.getRecipe().getRecipeName());
                if (item.getRecipe().getYum() == 0) {
                    rHolder.yum.setText(R.string.recipe_card_yum);
                    rHolder.yum.setTextColor(getResources().getColor(R.color.colorGrey));
                } else {
                    rHolder.yum.setText(R.string.recipe_card_yummy);
                    rHolder.yum.setTextColor(getResources().getColor(R.color.colorBlue));
                }
                rHolder.yum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (item.getRecipe().getYum() == 0) {
                            item.getRecipe().setYum(1);
                        } else {
                            item.getRecipe().setYum(0);
                        }
                    }
                });
            } else if (viewType == TYPE_FILTER_SELECTION) {
                FilterViewHolder fHolder = (FilterViewHolder) holder;
                FilterItem item = (FilterItem) items.get(position);

                fHolder.tvDiscoverTitle.setText(item.getTvDiscover());
                fHolder.tvFoodiesTitle.setText(item.getTvFoodies());
            }
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    public class WriteNewViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout llWriteNew;
        public TextView title;

        public WriteNewViewHolder(View itemView) {
            super(itemView);
        }
    }

    @SuppressLint("ParcelCreator")
    public class WriteNewItem extends Listitem {

        private String buttonTitle;
        private View.OnClickListener onClickListener;

        public WriteNewItem(String buttonTitle, View.OnClickListener onClickListener) {
            this.buttonTitle = buttonTitle;
            this.onClickListener = onClickListener;
        }

        @Override
        public int getViewType() {
            return TYPE_WRITE_NEW;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {

        }
    }

    public class RecipeCardViewHolder extends RecyclerView.ViewHolder {
        public TextView recipeTitle, displayName, recipeDescription, yum;
        public ImageView ivProfilePic, ivRecipePic;


        public RecipeCardViewHolder(View itemView) {
            super(itemView);

            recipeTitle = (TextView) itemView.findViewById(R.id.tvRecipeName);
            displayName = (TextView) itemView.findViewById(R.id.tvDisplayName);
            recipeDescription = (TextView) itemView.findViewById(R.id.tvRecipeDescription);
            yum = (TextView) itemView.findViewById(R.id.tvRecipeYum);
            ivProfilePic = (ImageView) itemView.findViewById(R.id.ivUserPic);
            ivRecipePic = (ImageView) itemView.findViewById(R.id.ivRecipePic);
        }
    }

    @SuppressLint("ParcelCreator")
    public class RecipeCardItem extends Listitem {

        private Recipe recipe;

        public RecipeCardItem(Recipe recipe) {
            this.recipe = recipe;
        }

        public Recipe getRecipe() { return recipe; }

        @Override
        public int getViewType() {
            return TYPE_RECIPE_CARD;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {

        }
    }

    public class FilterViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout llFoodiesTab, llDiscoverTab;
        public TextView tvFoodiesTitle, tvDiscoverTitle;
        public FrameLayout flFoodiesUnderline, flDiscoverUnderline;

        public FilterViewHolder(View itemView) {
            super(itemView);

            llFoodiesTab = (LinearLayout) itemView.findViewById(R.id.llFoodiesTab);
            llDiscoverTab = (LinearLayout) itemView.findViewById(R.id.llDiscoverTab);
            tvFoodiesTitle = (TextView) itemView.findViewById(R.id.tvFoodiesTitle);
            tvDiscoverTitle = (TextView) itemView.findViewById(R.id.tvDiscoverTitle);
            flFoodiesUnderline = (FrameLayout) itemView.findViewById(R.id.flFoodiesUnderLine);
            flDiscoverUnderline = (FrameLayout) itemView.findViewById(R.id.flDiscoverUnderLine);
        }
    }

    @SuppressLint("ParcelCreator")
    public class FilterItem extends Listitem {

        private View.OnClickListener foodiesOnClick, discoverOnClick;
        private String tvFoodies, tvDiscover;

        public FilterItem(String tvDiscover, String tvFoodies, View.OnClickListener foodiesOnClick, View.OnClickListener discoverOnClick) {
            this.foodiesOnClick = foodiesOnClick;
            this.discoverOnClick = discoverOnClick;
            this.tvDiscover = tvDiscover;
            this.tvFoodies = tvFoodies;
        }

        public String getTvFoodies() {
            return tvFoodies;
        }

        public String getTvDiscover() {
            return tvDiscover;
        }

        @Override
        public int getViewType() {
            return TYPE_FILTER_SELECTION;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {

        }
    }
}