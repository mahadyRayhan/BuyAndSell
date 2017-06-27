package com.example.nocturnal.buyandsell.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class LogInFragment extends Fragment {

    private LinearLayout registerPanelLL,loginPanelLL;
    private TextView signUpNowTV,logInNowTV;
    private Button loginBtn;
    private EditText logInEmailET,loginPasswordET;

    private SharedPreferences mypreferences;
    private SharedPreferences.Editor editor;

    private int userId;
    private String pass;

    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_log_in, container, false);

        mypreferences  = getActivity().getSharedPreferences("userInfo",MODE_PRIVATE);
        editor = mypreferences.edit();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Logging in your account.....");

        //region view initial
        registerPanelLL = (LinearLayout) v.findViewById(R.id.registerPanal);
        loginPanelLL = (LinearLayout) v.findViewById(R.id.loginPanal);
        signUpNowTV = (TextView) v.findViewById(R.id.signUpNow);
        logInNowTV = (TextView) v.findViewById(R.id.logInNow);
        loginBtn = (Button) v.findViewById(R.id.login);
        logInEmailET = (EditText) v.findViewById(R.id.logInEmail);
        loginPasswordET = (EditText) v.findViewById(R.id.loginPassword);
        registerPanelLL.setVisibility(View.GONE);
        //endregion

        //region regular button on click handeller
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
        //endregion


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://axionsoft.net/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                String email = logInEmailET.getText().toString();
                pass = loginPasswordET.getText().toString();

                APIService service = retrofit.create(APIService.class);
                Call<ArrayList<Users>> arrayListCall = service.getLoginInfo(email);

                arrayListCall.enqueue(new Callback<ArrayList<Users>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Users>> call, Response<ArrayList<Users>> response) {
                        ArrayList<Users> usersData = response.body();
                        userId = usersData.get(0).getId();
                        String userImage = usersData.get(0).getUser_image_url();
                        String location = usersData.get(0).getUser_location();
                        String phone = usersData.get(0).getUser_phone();
                        String password = usersData.get(0).getPassword().toString();
                        String userFullName = usersData.get(0).getUser_full_name().toString();
                        String email = usersData.get(0).getEmail().toString();

                        if (password.equals(pass)){

                            editor.putInt("userId",userId);
                            editor.putString("userImage",userImage);
                            editor.putString("location",location);
                            editor.putString("phone",phone);
                            editor.putString("userFullName",userFullName);
                            editor.putString("email",email);
                            editor.commit();
                            progressDialog.dismiss();

                            Intent intent = new Intent(getContext(), MyAccount.class);
                            intent.putExtra("id",userId);
                            startActivity(intent);
                        }else {
                            Toast.makeText(getContext(), usersData+"", Toast.LENGTH_SHORT).show();
                        }
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
