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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.servedonline.servedonline_android.Entity.Recipe;
import com.servedonline.servedonline_android.Entity.User;
import com.servedonline.servedonline_android.MainActivity;
import com.servedonline.servedonline_android.Network.JSON.IDResponse;
import com.servedonline.servedonline_android.R;

public class NewRecipeFragment extends Fragment {

    public static final String BACKSTACK_TAG = "_newRecipeFragment";

    private Handler handler;

    private EditText etTitle, etDescription;
    private Button btNext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_new_recipe, container, false);

        handler = new Handler();

        etTitle = (EditText) v.findViewById(R.id.etTitle);
        etDescription = (EditText) v.findViewById(R.id.etDescription);
        btNext = (Button) v.findViewById(R.id.btnNewRecipeNext);



        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).clearFocus();

                Log.d("Evaluation", "create recipe button pressed = " + System.currentTimeMillis());
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
            sendRecipe(title, desc);
        } else {
            Toast.makeText(getContext(), "Please complete fields before progressing", Toast.LENGTH_LONG).show();
        }
    }

    private void sendRecipe(final String title, final String desc) {

        ((MainActivity) getActivity()).showBlocker();
        if (((MainActivity) getActivity()).getConnectionHelper().isNetworkAvailable()) {
            final User user = ((MainActivity) getActivity()).getCurrentUser();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    final IDResponse response = ((MainActivity) getActivity()).getConnectionHelper().createNewRecipe(user.getId(), title, desc, 0);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (response != null) {
                                if (response.isSuccess()) {
                                    Log.d("send", "recipe response Id = " + response.getData());
                                    ((MainActivity) getActivity()).hideBlocker();

                                    Log.d("Evaluation", "create new recipe response = " + System.currentTimeMillis());
                                    progressToSteps(response.getData());
                                }
                            }
                        }
                    });
                }
            }).start();
        }

    }

    private void progressToSteps(int recipeId) {

        Bundle args = new Bundle();
        CreatingStepsFragment fragment = new CreatingStepsFragment();
        args.putInt(CreatingStepsFragment.RECIPE_KEY, recipeId);
        args.putInt(CreatingStepsFragment.STEP_NUMBER_KEY, 1);
        fragment.setArguments(args);
        ((MainActivity) getActivity()).navigate(fragment, BACKSTACK_TAG);
    }


}
