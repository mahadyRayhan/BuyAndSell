package com.example.nocturnal.buyandsell.Fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nocturnal.buyandsell.R;
import com.squareup.picasso.Picasso;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccountFragmnt extends Fragment {

    private ImageView user_account_imageIV;

    private TextView user_account_full_nameTV,user_account_emailTV,user_account_locationTV,user_account_phoneTV;

    private SharedPreferences mypreferences;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_my_account_fragmnt, container, false);
        user_account_imageIV = (ImageView) v.findViewById(R.id.user_account_image);
        user_account_full_nameTV = (TextView) v.findViewById(R.id.user_account_full_name);
        user_account_emailTV = (TextView) v.findViewById(R.id.user_account_email);
        user_account_locationTV = (TextView) v.findViewById(R.id.user_account_location);
        user_account_phoneTV = (TextView) v.findViewById(R.id.user_account_phone);

        mypreferences  = getActivity().getSharedPreferences("userInfo",MODE_PRIVATE);

        String name = mypreferences.getString("userFullName","");
        String location = mypreferences.getString("location","");
        String phone = mypreferences.getString("phone","");
        String userImage = mypreferences.getString("userImage","");
        String email = mypreferences.getString("email","");
        int userId = mypreferences.getInt("userId",0);

        Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();

        if(!userImage.isEmpty()){
            Picasso.with(getContext())
                    .load(userImage)
                    .placeholder(R.drawable.dummy) //this is optional the image to display while the url image is downloading
                    .error(R.drawable.dummy)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                    .into(user_account_imageIV);
        }

        user_account_full_nameTV.setText(name);
        user_account_locationTV.setText(location);
        user_account_phoneTV.setText(phone);
        user_account_emailTV.setText(email);



        return v;
    }

}
