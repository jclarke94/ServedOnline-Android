package com.servedonline.servedonline_android.Fragments.Login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.servedonline.servedonline_android.Fragments.Home.HomeFragment;
import com.servedonline.servedonline_android.MainActivity;
import com.servedonline.servedonline_android.R;

public class LoginFragment extends Fragment {

    public static final String BACKSTACK_TAG = "loginFragment";

    public TextView tvHeading, tvTitle, tvSignUp;

    public Button btnDone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        tvHeading = (TextView) v.findViewById(R.id.tvServedTitle);
        tvTitle = (TextView) v.findViewById(R.id.tvLoginTitle);
        tvSignUp = (TextView) v.findViewById(R.id.tvSignUp);
        btnDone = (Button) v.findViewById(R.id.btnDone);

        tvHeading.setShadowLayer(4, 4, 4, getResources().getColor(R.color.colorGrey));
        tvTitle.setShadowLayer(2, 2, 2, getResources().getColor(R.color.colorGrey));
        tvSignUp.setShadowLayer(2, 2, 2, getResources().getColor(R.color.colorGrey));

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
                HomeFragment fragment = new HomeFragment();
                ((MainActivity) getActivity()).navigate(fragment, BACKSTACK_TAG);
            }
        });

        return v;
    }



}
