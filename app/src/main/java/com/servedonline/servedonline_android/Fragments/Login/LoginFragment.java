package com.servedonline.servedonline_android.Fragments.Login;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.servedonline.servedonline_android.Database.DatabaseThread;
import com.servedonline.servedonline_android.Fragments.Home.HomeFragment;
import com.servedonline.servedonline_android.MainActivity;
import com.servedonline.servedonline_android.Network.JSON.UserResponse;
import com.servedonline.servedonline_android.R;

public class LoginFragment extends Fragment {

    public static final String BACKSTACK_TAG = "loginFragment";

    private Handler handler;

    private TextView tvHeading, tvTitle, tvSignUp;
    private EditText etEmail, etPassword;
    private Button btnDone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        tvHeading = (TextView) v.findViewById(R.id.tvServedTitle);
        tvTitle = (TextView) v.findViewById(R.id.tvLoginTitle);
        tvSignUp = (TextView) v.findViewById(R.id.tvSignUp);
        btnDone = (Button) v.findViewById(R.id.btnDone);
        etEmail = (EditText) v.findViewById(R.id.etEmail);
        etPassword = (EditText) v.findViewById(R.id.etPassword);

        tvHeading.setShadowLayer(4, 4, 4, getResources().getColor(R.color.colorGrey));
        tvTitle.setShadowLayer(2, 2, 2, getResources().getColor(R.color.colorGrey));
        tvSignUp.setShadowLayer(2, 2, 2, getResources().getColor(R.color.colorGrey));

        handler = new Handler();

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUpFragment fragment = new SignUpFragment();
                ((MainActivity) getActivity()).navigate(fragment, BACKSTACK_TAG);
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkFieldsCompleted();
            }
        });

        return v;
    }

    public void checkFieldsCompleted() {
        if (etEmail.getText() != null && etPassword.getText() != null) {
            login();
        } else {
            //todo fields incomplete
        }
    }

    public void login() {
        ((MainActivity) getActivity()).showBlocker();

        if (((MainActivity) getActivity()).getConnectionHelper().isNetworkAvailable()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final UserResponse response = ((MainActivity) getActivity()).getConnectionHelper().loginUser(
                            String.valueOf(etEmail.getText()), String.valueOf(etPassword.getText()));

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (response != null) {
                                if (response.isSuccess()) {
                                    ((MainActivity) getActivity()).getDatabase().insert(response.getData(), new DatabaseThread.OnDatabaseRequestComplete() {
                                        @Override
                                        public void onRequestComplete(Object returnValue) {
                                            ((MainActivity) getActivity()).hideBlocker();

                                            //todo save id to shared preference in MainActivity
                                            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
                                            sp.edit().putInt(MainActivity.LOGIN_ID, response.getData().getId()).apply();
                                            ((MainActivity) getActivity()).setCurrentUser(response.getData());

                                            passToHome();
                                        }
                                    });
                                } else {
                                    //todo not successful login
                                }
                            } else {
                                //todo no response
                            }
                        }
                    });
                }
            }).start();
        } else {
            //todo connection unavailable
        }
    }

    private void passToHome() {
        HomeFragment fragment = new HomeFragment();
        ((MainActivity) getActivity()).navigate(fragment, BACKSTACK_TAG);
    }

}
