package com.example.nocturnal.buyandsell.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nocturnal.buyandsell.Model.Users;
import com.example.nocturnal.buyandsell.R;
import com.example.nocturnal.buyandsell.Service.APIService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowFullAdInfo extends AppCompatActivity {

    private TextView detaile_ad_product_nameTV, detaile_ad_product_priceTV, detaile_ad_product_negotiableTV,
            detaile_ad_product_owner_full_nameTV, detaile_ad_product_detail_discriptionTV, detaile_ad_product_locationTV,
            detaile_ad_product_conditionTV, detaile_ad_product_brandTV, detaile_ad_product_categoryTV, detaile_ad_product_post_timeTV;

    private ImageView detaile_ad_product_first_imageIV, detaile_ad_product_second_imageIV, detaile_ad_product_trird_imageIV,
            detaile_ad_product_forth_imageIV, detaile_ad_product_fifth_imageIV;

    String phone, email, userFullName, locationName;

    private Button call_ad_ownerBTN, sms_ad_ownerBTN, email_ad_ownerBTN;

    private String subject = "Abount your product in Buy and Sell";

    private ArrayList<String>emailList;

    String message = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_full_ad_info);


        emailList = new ArrayList<>();


        //region get intent
        Intent intent = getIntent();
        String modelNo = intent.getStringExtra("modelNo").toString();
        String price = intent.getStringExtra("price").toString();
        String negotiable = intent.getStringExtra("negotiable").toString();
        final int userID = intent.getIntExtra("userID", 0);
        String detail = intent.getStringExtra("detail").toString();
        final String location = intent.getStringExtra("location").toString();
        String condition = intent.getStringExtra("condition").toString();
        String category = intent.getStringExtra("category").toString();
        String brand = intent.getStringExtra("brand").toString();
        intent.getStringExtra("time").toString();

        String firstImage = intent.getStringExtra("firstImage").toString();
        String secondImage = intent.getStringExtra("secondImage").toString();
        String thirdImage = intent.getStringExtra("thirdImage").toString();
        String forthImage = intent.getStringExtra("forthImage").toString();
        String fifthImage = intent.getStringExtra("fifthImage").toString();
        //endregion

        //region view init
        detaile_ad_product_nameTV = (TextView) findViewById(R.id.detaile_ad_product_name);
        detaile_ad_product_priceTV = (TextView) findViewById(R.id.detaile_ad_product_price);
        detaile_ad_product_negotiableTV = (TextView) findViewById(R.id.detaile_ad_product_negotiable);
        detaile_ad_product_owner_full_nameTV = (TextView) findViewById(R.id.detaile_ad_product_owner_full_name);
        detaile_ad_product_detail_discriptionTV = (TextView) findViewById(R.id.detaile_ad_product_detail_discription);
        detaile_ad_product_locationTV = (TextView) findViewById(R.id.detaile_ad_product_location);
        detaile_ad_product_conditionTV = (TextView) findViewById(R.id.detaile_ad_product_condition);
        detaile_ad_product_brandTV = (TextView) findViewById(R.id.detaile_ad_product_brand);
        detaile_ad_product_categoryTV = (TextView) findViewById(R.id.detaile_ad_product_category);
        detaile_ad_product_post_timeTV = (TextView) findViewById(R.id.detaile_ad_product_post_time);

        detaile_ad_product_first_imageIV = (ImageView) findViewById(R.id.detaile_ad_product_first_image);
        detaile_ad_product_second_imageIV = (ImageView) findViewById(R.id.detaile_ad_product_second_image);
        detaile_ad_product_trird_imageIV = (ImageView) findViewById(R.id.detaile_ad_product_trird_image);
        detaile_ad_product_forth_imageIV = (ImageView) findViewById(R.id.detaile_ad_product_forth_image);
        detaile_ad_product_fifth_imageIV = (ImageView) findViewById(R.id.detaile_ad_product_fifth_image);

        call_ad_ownerBTN = (Button) findViewById(R.id.call_ad_owner);
        sms_ad_ownerBTN = (Button) findViewById(R.id.sms_ad_owner);
        email_ad_ownerBTN = (Button) findViewById(R.id.email_ad_owner);
        //endregion

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://axionsoft.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);
        Call<ArrayList<Users>> arrayListCall = service.getAdOwnerInfo(userID);

        arrayListCall.enqueue(new Callback<ArrayList<Users>>() {
            @Override
            public void onResponse(Call<ArrayList<Users>> call, Response<ArrayList<Users>> response) {
                ArrayList<Users> usersData = response.body();
                email = usersData.get(0).getEmail();
                phone = usersData.get(0).getUser_phone();
                userFullName = usersData.get(0).getUser_full_name();
                locationName = usersData.get(0).getUser_location();

                detaile_ad_product_owner_full_nameTV.setText(userFullName);

                call_ad_ownerBTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + phone));
                        if (ActivityCompat.checkSelfPermission(ShowFullAdInfo.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(callIntent);
                    }
                });
                emailList.add(email);
                email_ad_ownerBTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        composeEmail( emailList,subject);
                    }
                });

                sms_ad_ownerBTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent callIntent = new Intent(Intent.ACTION_SENDTO);
                        callIntent.setData(Uri.parse("sms:" + phone));
                        startActivity(callIntent);
                    }
                });
            }

            @Override
            public void onFailure(Call<ArrayList<Users>> call, Throwable t) {

            }
        });



        //region set value and pic
        detaile_ad_product_nameTV.setText(modelNo);
        detaile_ad_product_priceTV.setText("Tk "+price);
        detaile_ad_product_negotiableTV.setText(negotiable);
        detaile_ad_product_detail_discriptionTV.setText(detail);
        detaile_ad_product_locationTV.setText(location);
        detaile_ad_product_conditionTV.setText(condition);
        detaile_ad_product_brandTV.setText(brand);
        detaile_ad_product_categoryTV.setText(category);
        detaile_ad_product_post_timeTV.setText("time");


        Picasso.with(this)
                .load(firstImage)
                .placeholder(R.drawable.dummy) //this is optional the image to display while the url image is downloading
                .error(R.drawable.dummy)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                .into(detaile_ad_product_first_imageIV);

        if (secondImage.equals("empty")){
            detaile_ad_product_second_imageIV.setVisibility(View.GONE);
        }else {
            Picasso.with(this)
                    .load(secondImage)
                    .placeholder(R.drawable.dummy) //this is optional the image to display while the url image is downloading
                    .error(R.drawable.dummy)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                    .into(detaile_ad_product_second_imageIV);
        }

        if (thirdImage.equals("empty")){
            detaile_ad_product_trird_imageIV.setVisibility(View.GONE);
        }else {
            Picasso.with(this)
                    .load(thirdImage)
                    .placeholder(R.drawable.dummy) //this is optional the image to display while the url image is downloading
                    .error(R.drawable.dummy)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                    .into(detaile_ad_product_trird_imageIV);
        }

        if (forthImage.equals("empty")){
            detaile_ad_product_forth_imageIV.setVisibility(View.GONE);
        }else {
            Picasso.with(this)
                    .load(forthImage)
                    .placeholder(R.drawable.dummy) //this is optional the image to display while the url image is downloading
                    .error(R.drawable.dummy)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                    .into(detaile_ad_product_forth_imageIV);
        }
        if (fifthImage.equals("empty")){
            detaile_ad_product_fifth_imageIV.setVisibility(View.GONE);
        }else {
            Picasso.with(this)
                    .load(fifthImage)
                    .placeholder(R.drawable.dummy) //this is optional the image to display while the url image is downloading
                    .error(R.drawable.dummy)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                    .into(detaile_ad_product_fifth_imageIV);
        }

        //endregion

    }

    public void composeEmail(ArrayList<String> emailList, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void composeMmsMessage(String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.putExtra("sms_body", message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
