package com.example.nocturnal.buyandsell.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nocturnal.buyandsell.Activity.MyAccount;
import com.example.nocturnal.buyandsell.Model.Users;
import com.example.nocturnal.buyandsell.R;
import com.example.nocturnal.buyandsell.Service.APIService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class LogInFragment extends Fragment {

    LinearLayout registerPanelLL,loginPanelLL;
    TextView signUpNowTV,logInNowTV;
    Button loginBtn;
    EditText logInEmailET,loginPasswordET;

    int userId;

    public LogInFragment() {
        // Required empty public constructor
    }

    public interface userIdListener{
        void getUserId(int id);
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
        loginBtn = (Button) v.findViewById(R.id.login);
        logInEmailET = (EditText) v.findViewById(R.id.logInEmail);
        loginPasswordET = (EditText) v.findViewById(R.id.loginPassword);
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

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://axionsoft.net/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                String email = logInEmailET.getText().toString();
                loginPasswordET.getText().toString();

                APIService service = retrofit.create(APIService.class);
                Call<ArrayList<Users>> arrayListCall = service.getLoginInfo(email);

                arrayListCall.enqueue(new Callback<ArrayList<Users>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Users>> call, Response<ArrayList<Users>> response) {
                        ArrayList<Users> usersData = response.body();
                        userId = usersData.get(0).getId();
                        Toast.makeText(getContext(), usersData.get(0).getId()+"", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getContext(), usersData.get(0).getUsername()+"", Toast.LENGTH_SHORT).show();


                        Intent intent = new Intent(getContext(), MyAccount.class);
                        intent.putExtra("id",userId);
                        startActivity(intent);

                    }

                    @Override
                    public void onFailure(Call<ArrayList<Users>> call, Throwable t) {

                    }
                });
            }
        });
        return v;
    }


}
