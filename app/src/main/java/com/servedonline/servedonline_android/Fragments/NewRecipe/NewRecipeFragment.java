package com.servedonline.servedonline_android.Fragments.NewRecipe;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.servedonline.servedonline_android.Entity.Recipe;
import com.servedonline.servedonline_android.Entity.User;
import com.servedonline.servedonline_android.MainActivity;
import com.servedonline.servedonline_android.R;

public class NewRecipeFragment extends Fragment {

    public static final String RECIPE_KEY = "_recipe";
    public static final String BACKSTACK_TAG = "_newRecipeFragment";

    private EditText etTitle, etDescription;
    private Button btNext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_new_recipe, container, false);

        etTitle = (EditText) v.findViewById(R.id.etTitle);
        etDescription = (EditText) v.findViewById(R.id.etDescription);
        btNext = (Button) v.findViewById(R.id.btnNewRecipeNext);



        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkFieldsCompleted();
            }
        });


        return v;
    }

    private void checkFieldsCompleted() {
        String title = String.valueOf(etTitle.getText());
        String desc = String .valueOf(etDescription.getText());
        boolean completed = true;

        if (title == null || title.matches("")){
            completed = false;
        }
        if (desc == null || desc.matches("")) {
            completed = false;
        }

        if (completed) {
            progressToSteps(title, desc);
        } else {
            Toast.makeText(getContext(), "Please complete fields before progressing", Toast.LENGTH_LONG).show();
        }
    }

    private void progressToSteps(String title, String desc) {
        User user = ((MainActivity) getActivity()).getCurrentUser();

        Recipe recipe = new Recipe(0, title, desc, user.getDisplayName(), user.getId(), 0, 0);

        Bundle args = new Bundle();
        CreatingStepsFragment fragment = new CreatingStepsFragment();
        args.putParcelable(CreatingStepsFragment.RECIPE_KEY, recipe);
        args.putInt(CreatingStepsFragment.STEP_NUMBER_KEY, 1);

        fragment.setArguments(args);
        ((MainActivity) getActivity()).navigate(fragment, BACKSTACK_TAG);
    }


}
