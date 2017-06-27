package com.example.nocturnal.buyandsell.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.nocturnal.buyandsell.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SellItem extends Fragment {

    CardView electronicsCV,sell_itemCV,back_to_all_categoryCV;

    Animation slideUp, slideDown;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_sell_item, container, false);

        slideUp = AnimationUtils.loadAnimation(getContext(),R.anim.slide_up);
        slideDown = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down);
        slideDown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (animation == slideUp) {
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        slideUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (animation == slideUp) {

                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        electronicsCV= (CardView) v.findViewById(R.id.electronics);
        sell_itemCV= (CardView) v.findViewById(R.id.sell_item);
        back_to_all_categoryCV= (CardView) v.findViewById(R.id.back_to_all_category);

        back_to_all_categoryCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                AllAdCategoryFragment allAdCategoryFragment = new AllAdCategoryFragment();
                ft.replace(R.id.addCategoryFragmentHolder, allAdCategoryFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        });

        electronicsCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                AllElectronicsCat allElectronicsCat = new AllElectronicsCat();
                ft.replace(R.id.addCategoryFragmentHolder, allElectronicsCat);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        });

        sell_itemCV.setVisibility(View.VISIBLE);
        sell_itemCV.startAnimation(slideDown);

        return v;
    }

}
