package com.servedonline.servedonline_android.Fragments.Login;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.servedonline.servedonline_android.Database.DatabaseThread;
import com.servedonline.servedonline_android.Entity.User;
import com.servedonline.servedonline_android.Fragments.Home.HomeFragment;
import com.servedonline.servedonline_android.MainActivity;
import com.servedonline.servedonline_android.R;

import static com.servedonline.servedonline_android.MainActivity.LOGIN_ID;

public class LogoFragment extends Fragment {

    public static final String BACKSTACK_TAG = "_logoFragment";

    private Handler handler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_logo, container, false);

        handler = new Handler();

        int loginId = ((MainActivity) getActivity()).sp.getInt(LOGIN_ID, 0);
        if (loginId != 0) {

            ((MainActivity) getActivity()).getDatabase().getUser(loginId, new DatabaseThread.OnDatabaseRequestComplete<User>() {
                @Override
                public void onRequestComplete(final User returnValue) {

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ((MainActivity) getActivity()).setCurrentUser(returnValue);

                            HomeFragment homeFragment = new HomeFragment();

                            ((MainActivity) getActivity()).navigate(homeFragment, BACKSTACK_TAG);
                        }
                    }, 2000);

                }
            });


        } else {

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LoginFragment loginFragment = new LoginFragment();

                    ((MainActivity) getActivity()).navigate(loginFragment, BACKSTACK_TAG);
                }
            }, 2000);

        }

        return v;
    }
}
