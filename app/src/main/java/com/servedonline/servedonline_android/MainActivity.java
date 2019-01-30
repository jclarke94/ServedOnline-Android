package com.servedonline.servedonline_android;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.ImageView;

import com.servedonline.servedonline_android.Application.ServedApplication;
import com.servedonline.servedonline_android.Database.Database;
import com.servedonline.servedonline_android.Fragments.Login.LoginFragment;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {

    private ImageView navBar;

    private boolean allowBackPress = true;

    private ArrayList<OnBackPressedListener> onBackPressedListeners = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }
        }

        LoginFragment loginFragment = new LoginFragment();

        loginFragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, loginFragment).commit();

    }

    @Override
    public void onBackPressed() {
        onBackPressed(false);
    }

    public void onBackPressed(boolean override) {
        if (allowBackPress || override) {
            super.onBackPressed();
        }

        ArrayList<Integer> removeIndexes = new ArrayList<>();
        for (int i = 0; i < onBackPressedListeners.size(); i++) {
            try {
                onBackPressedListeners.get(i).onBackPressed();
            } catch (Exception e) {
                removeIndexes.add(i);
                e.printStackTrace();
            }
        }

        for (int i = onBackPressedListeners.size() - 1; i >= 0; i--) {
            onBackPressedListeners.remove((int) removeIndexes.get(i));
        }
    }

    public void navigate(Fragment fragment, String backstackTag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        if(backstackTag != null) {
            fragmentTransaction.addToBackStack(backstackTag);
        }

        fragmentTransaction.commit();
    }

    public Database getDatabase() {
        return ((ServedApplication) getApplication()).getDatabase();
    }

    public static abstract class OnBackPressedListener {
        public abstract void onBackPressed();
    }
}
