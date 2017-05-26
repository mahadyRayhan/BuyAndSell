package com.example.nocturnal.buyandsell.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nocturnal.buyandsell.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LogInFragment extends Fragment {

    LinearLayout registerPanelLL,loginPanelLL;
    TextView signUpNowTV,logInNowTV;

    public LogInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_log_in, container, false);

        registerPanelLL = (LinearLayout) v.findViewById(R.id.registerPanal);
        loginPanelLL = (LinearLayout) v.findViewById(R.id.loginPanal);
        signUpNowTV = (TextView) v.findViewById(R.id.signUpNow);
        logInNowTV = (TextView) v.findViewById(R.id.logInNow);
        registerPanelLL.setVisibility(View.GONE);

        signUpNowTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPanelLL.setVisibility(View.GONE);
                registerPanelLL.setVisibility(View.VISIBLE);
            }
        });

        logInNowTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPanelLL.setVisibility(View.VISIBLE);
                registerPanelLL.setVisibility(View.GONE);
            }
        });
        return v;
    }


}
