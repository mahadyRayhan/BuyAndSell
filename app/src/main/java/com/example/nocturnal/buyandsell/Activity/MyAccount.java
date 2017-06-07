package com.example.nocturnal.buyandsell.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.nocturnal.buyandsell.Fragment.EditMyAccountFragment;
import com.example.nocturnal.buyandsell.Fragment.MyAccountFragmnt;
import com.example.nocturnal.buyandsell.R;

public class MyAccount extends AppCompatActivity {

    MenuItem save_menu_item,edit_menu_item;

    ProgressDialog progressDialog;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Completiong your account.....");

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        MyAccountFragmnt myAccountFragmnt = new MyAccountFragmnt();
        ft.add(R.id.myaccount_fragmentContainer,myAccountFragmnt);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

        Intent intent = getIntent();

        userId  = intent.getIntExtra("id",0);
        Toast.makeText(this, userId+"", Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_account_menu,menu);
        save_menu_item = menu.findItem(R.id.save_account);
        edit_menu_item = menu.findItem(R.id.edit_account);
        save_menu_item.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.edit_account:
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                EditMyAccountFragment editMyAccountFragment =new EditMyAccountFragment();
                Bundle b = new Bundle();
                b.putInt("id",userId);
                editMyAccountFragment.setArguments(b);
                ft.replace(R.id.myaccount_fragmentContainer,editMyAccountFragment);
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
                save_menu_item.setVisible(true);
                edit_menu_item.setVisible(false);
                break;
            case R.id.save_account:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
