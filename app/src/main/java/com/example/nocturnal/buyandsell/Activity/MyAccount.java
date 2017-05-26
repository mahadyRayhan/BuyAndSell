package com.example.nocturnal.buyandsell.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.nocturnal.buyandsell.Fragment.LogInFragment;
import com.example.nocturnal.buyandsell.Fragment.RegisterFragment;
import com.example.nocturnal.buyandsell.R;

public class MyAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        LogInFragment logInFragment = new LogInFragment();
        ft.add(R.id.fragmentContainer,logInFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    public void signUpWithEmailBtn(View view) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        RegisterFragment registerFragment =new RegisterFragment();
        ft.replace(R.id.fragmentContainer,registerFragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
}
