package com.servedonline.servedonline_android.Fragments.NewRecipe;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
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

public class completeFragment extends Fragment {

    private Handler handler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_complete, container, false);

        handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                HomeFragment homeFragment = new HomeFragment();

                ((MainActivity) getActivity()).voidBackstackAndNavigate(HomeFragment.BACKSTACK_TAG, homeFragment, HomeFragment.BACKSTACK_TAG);
            }
        }, 1000);


        v.setAlpha(0f);
        v.animate().setStartDelay(100).setDuration(200).alpha(1f);

        return v;

    }
}
