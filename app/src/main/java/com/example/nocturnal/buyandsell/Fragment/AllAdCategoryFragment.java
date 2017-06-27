package com.example.nocturnal.buyandsell.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nocturnal.buyandsell.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllAdCategoryFragment extends Fragment {

    private CardView serviceCV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_all_ad_category, container, false);

        serviceCV = (CardView) v.findViewById(R.id.service);
        serviceCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                SellItem sellItem = new SellItem();
                ft.replace(R.id.addCategoryFragmentHolder, sellItem);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        });



        return  v;
    }

}
