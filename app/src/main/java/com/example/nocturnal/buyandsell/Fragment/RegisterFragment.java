package com.example.nocturnal.buyandsell.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nocturnal.buyandsell.Model.Users;
import com.example.nocturnal.buyandsell.R;
import com.example.nocturnal.buyandsell.Service.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private TextView logInFromRegPageTV;
    private EditText usernameET,emailET,passwordET,getPasswordAgainET;
    private Button signUpBTN;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_register, container, false);

        usernameET = (EditText) v.findViewById(R.id.reg_username);
        emailET = (EditText) v.findViewById(R.id.reg_email);
        passwordET = (EditText) v.findViewById(R.id.reg_password);
        getPasswordAgainET = (EditText) v.findViewById(R.id.reg_password_again);
        signUpBTN = (Button) v.findViewById(R.id.signUp);

        logInFromRegPageTV = (TextView) v.findViewById(R.id.logInFromRegPage);
        logInFromRegPageTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                LogInFragment logInFragment =new LogInFragment();
                ft.replace(R.id.fragmentContainer,logInFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        });

        signUpBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://axionsoft.net/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                String username = usernameET.getText().toString();
                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();
                String confirmPass = getPasswordAgainET.getText().toString();
                Users users = new Users(username,email,password);

                APIService service = retrofit.create(APIService.class);
                Call<Users> registerCall = service.registerUser(users.getUsername(),users.getEmail(),users.getPassword());

                registerCall.enqueue(new Callback<Users>() {
                    @Override
                    public void onResponse(Call<Users> call, Response<Users> response) {
                        /*Users userdata = response.body();
                        Toast.makeText(getContext(), userdata.getEmail(), Toast.LENGTH_SHORT).show();*/
                    }

                    @Override
                    public void onFailure(Call<Users> call, Throwable t) {

                    }
                    
                });



            }
        });



        return v;
    }

}
