package com.example.nocturnal.buyandsell.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nocturnal.buyandsell.Fragment.ListFragment;
import com.example.nocturnal.buyandsell.Fragment.LogInFragment;
import com.example.nocturnal.buyandsell.Fragment.RegisterFragment;
import com.example.nocturnal.buyandsell.R;

public class StartingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private TextView testTV,signUpNowTV,logInFromRegPageTV;
    private Button signUpWithEmailBtn;
    private LinearLayout loginPanelLL;

    private EditText usernameET,emailET,passwordET,getPasswordAgainET;

    private SharedPreferences mypreferences;
    private SharedPreferences.Editor editor;

    private static final String REGISTER_URL = "http://localhost/Android/register.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);

        mypreferences  = getSharedPreferences("userInfo",MODE_PRIVATE);
        editor = mypreferences.edit();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ListFragment listFragment = new ListFragment();
        ft.add(R.id.fragmentContainer,listFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.starting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent(this, StartingActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            LogInFragment logInFragment =new LogInFragment();
            ft.replace(R.id.fragmentContainer,logInFragment);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        } else if (id == R.id.nav_slideshow) {
            String location = mypreferences.getString("location","");
            String phone = mypreferences.getString("phone","");
            String email = mypreferences.getString("email","");
            int userId = mypreferences.getInt("userId",0);
            if (location.isEmpty() || phone.isEmpty() || email.isEmpty()){
                Toast.makeText(this, "Please log in & complete your account to post add", Toast.LENGTH_SHORT).show();
            }else {
                Intent intent = new Intent(this, PostAd.class);
                startActivity(intent);
            }
        } else if (id == R.id.nav_manage) {
            editor.putString("userImage","");
            editor.putString("location","");
            editor.putString("phone","");
            editor.putString("userFullName","");
            editor.putString("email","");
            editor.commit();
            finish();
            startActivity(getIntent());
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
