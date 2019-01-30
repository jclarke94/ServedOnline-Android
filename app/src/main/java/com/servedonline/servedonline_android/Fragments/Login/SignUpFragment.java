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

public class SignUpFragment extends Fragment {

    public static final String BACKSTACK_TAG = "signUpFragment";

    TextView tvHeading, tvTitle;

    Button btnSignUp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sign_up, container, false);

        tvHeading = (TextView) v.findViewById(R.id.tvServedTitle);
        tvTitle = (TextView) v.findViewById(R.id.tvSigningUpTitle);
        btnSignUp = (Button) v.findViewById(R.id.btnSigningUpCompleted);

        tvHeading.setShadowLayer(4, 4, 4, getResources().getColor(R.color.colorGrey));
        tvTitle.setShadowLayer(2, 2, 2, getResources().getColor(R.color.colorGrey));

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment fragment = new HomeFragment();
                ((MainActivity) getActivity()).navigate(fragment, BACKSTACK_TAG);
            }
        });

        return v;
    }


}
