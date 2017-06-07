package com.example.nocturnal.buyandsell.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nocturnal.buyandsell.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RentProperty extends Fragment {


    public RentProperty() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rent_property, container, false);
    }

}
