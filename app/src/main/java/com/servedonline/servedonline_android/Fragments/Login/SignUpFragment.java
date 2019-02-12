package com.servedonline.servedonline_android.Fragments.Login;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.servedonline.servedonline_android.Database.DatabaseThread;
import com.servedonline.servedonline_android.Entity.User;
import com.servedonline.servedonline_android.Fragments.Home.HomeFragment;
import com.servedonline.servedonline_android.MainActivity;
import com.servedonline.servedonline_android.Network.JSON.UserResponse;
import com.servedonline.servedonline_android.R;

public class SignUpFragment extends Fragment {

    public static final String BACKSTACK_TAG = "signUpFragment";

    private Handler handler;

    private TextView tvHeading, tvTitle;
    private EditText etFirstName, etLastName, etEmail, etConfirmEmail, etPassword, etConfirmPassword;
    private Button btnSignUp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sign_up, container, false);

        handler = new Handler();

        tvHeading = (TextView) v.findViewById(R.id.tvServedTitle);
        tvTitle = (TextView) v.findViewById(R.id.tvSigningUpTitle);
        btnSignUp = (Button) v.findViewById(R.id.btnSigningUpCompleted);
        etFirstName = (EditText) v.findViewById(R.id.etFirstName);
        etLastName = (EditText) v.findViewById(R.id.etLastName);
        etEmail = (EditText) v.findViewById(R.id.etEmail);
        etConfirmEmail = (EditText) v.findViewById(R.id.etConfirmEmail);
        etPassword = (EditText) v.findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) v.findViewById(R.id.etConfirmPassword);


        tvHeading.setShadowLayer(4, 4, 4, getResources().getColor(R.color.colorGrey));
        tvTitle.setShadowLayer(2, 2, 2, getResources().getColor(R.color.colorGrey));



        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkFieldsCompleted();
            }
        });

        return v;
    }

    private void checkFieldsCompleted() {
        String firstName = null;
        String lastName = null;
        String email = null;
        String confirmEmail = null;
        String password = null;
        String confirmPassword = null;
        boolean fieldsCompleted = true;

        if (etFirstName.getText() != null) {
            firstName = String.valueOf(etFirstName.getText());
        } else {
            fieldsCompleted = false;
        }
        if (etLastName.getText() != null) {
            lastName = String.valueOf(etLastName.getText());
        } else {
            fieldsCompleted = false;
        }
        if (etEmail.getText() != null) {
            email = String.valueOf(etEmail.getText());
        } else {
            fieldsCompleted = false;
        }
        if (etConfirmEmail.getText() != null) {
            confirmEmail = String.valueOf(etConfirmEmail.getText());
        } else {
            fieldsCompleted = false;
        }
        if (etPassword.getText() != null) {
            password = String.valueOf(etPassword.getText());
        } else {
            fieldsCompleted = false;
        }
        if (etConfirmPassword.getText() != null) {
            confirmPassword = String.valueOf(etConfirmPassword.getText());
        } else {
            fieldsCompleted = false;
        }

        if (fieldsCompleted) {
            if (email.matches(confirmEmail) && password.matches(confirmPassword)) {
                final User user = new User(firstName, lastName, email, password);

                createNewUser(user);

            } else if (email.matches(confirmEmail) && !password.matches(confirmPassword)) {
                //todo passwords don't match
                Log.d("SignUpFragment", "passwords don't match");
            } else {
                //todo emails don't match
                Log.d("SignUpFragment", "emails don't match");
            }
        } else {
            //todo fields aren't completed
            Log.d("SIgnUpFragment", "Fields not completed");
        }

    }

    private void createNewUser(final User user) {
        ((MainActivity) getActivity()).showBlocker();

        if (((MainActivity) getActivity()).getConnectionHelper().isNetworkAvailable()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final UserResponse response = ((MainActivity) getActivity()).getConnectionHelper().createUser(user);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (response != null) {
                                if (response.isSuccess()) {
                                    ((MainActivity) getActivity()).getDatabase().insert(response.getData(), new DatabaseThread.OnDatabaseRequestComplete() {
                                        @Override
                                        public void onRequestComplete(Object returnValue) {
                                            ((MainActivity) getActivity()).hideBlocker();

                                            passToHome();
                                        }
                                    });

                                }
                            }
                        }
                    });
                }
            }).start();
        } else {
            Log.d("SignUpFragment", "Network not connected");
        }





    }

    private void passToHome() {
        HomeFragment fragment = new HomeFragment();
        ((MainActivity) getActivity()).navigate(fragment, BACKSTACK_TAG);
    }


}
