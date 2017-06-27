package com.example.nocturnal.buyandsell.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.nocturnal.buyandsell.Fragment.AllAdCategoryFragment;
import com.example.nocturnal.buyandsell.R;

import java.util.ArrayList;

public class PostAd extends AppCompatActivity {

    ProgressDialog progressDialog;

    AlertDialog b;

    Animation slideUp, slideDown;
    private CardView serviceCV, categoryCV, sell_itemCV,rent_property_mainCV,rent_propertyCV,
            electronicsCV,allElectronicsCatCV,show_all_categoryCV;

    private EditText post_add_locationET,post_ad_descriptionET,post_ad_product_model_noET,post_ad_priceET,post_ad_select_featureET;

    private TextView mobile_phoneTV;

    private Spinner post_ad_product_conditionSP,post_ad_brandSP,post_ad_authenticitySP;

    private LinearLayout post_ad_image_holderLL;

    private CheckBox post_ad_negotiableCB;

    private Button post_ad_photo_selectBTN,post_ad_post_adBTN;

    private AlertDialog.Builder myCustomAlert;

    String addCondition,addBrand,addAuthenticity,addNegotiable;

    private String mediaPath;

    ArrayList<String> mediaPathArraylist;
    ArrayList<String> uploadedImageUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_ad);


        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        AllAdCategoryFragment allAdCategoryFragment = new AllAdCategoryFragment();
        ft.add(R.id.addCategoryFragmentHolder,allAdCategoryFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }


}
