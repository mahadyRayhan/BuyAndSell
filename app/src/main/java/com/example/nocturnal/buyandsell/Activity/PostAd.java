package com.example.nocturnal.buyandsell.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nocturnal.buyandsell.Model.Add;
import com.example.nocturnal.buyandsell.Model.ServerResponse;
import com.example.nocturnal.buyandsell.R;
import com.example.nocturnal.buyandsell.Service.APIService;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostAd extends AppCompatActivity {

    ProgressDialog progressDialog;

    AlertDialog b;

    Animation slideUp, slideDown;
    private CardView serviceCV, categoryCV, sell_itemCV,rent_property_mainCV,rent_propertyCV,
            electronicsCV,allElectronicsCatCV;
    
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

        myCustomAlert = new AlertDialog.Builder(this);
        mediaPathArraylist = new ArrayList<String>();
        uploadedImageUrl = new ArrayList<String>();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Posting Your Ad.....");

        //region initialition
        serviceCV = (CardView) findViewById(R.id.service);
        categoryCV = (CardView) findViewById(R.id.category);
        sell_itemCV = (CardView) findViewById(R.id.sell_item);
        sell_itemCV.setVisibility(View.GONE);
        rent_property_mainCV = (CardView) findViewById(R.id.rent_property_main);
        /*rent_propertyCV = (CardView) findViewById(R.id.rent_property);
        rent_propertyCV.setVisibility(View.GONE);*/
        electronicsCV = (CardView) findViewById(R.id.electronics);
        allElectronicsCatCV = (CardView) findViewById(R.id.allElectronicsCat);
        allElectronicsCatCV.setVisibility(View.GONE);
        mobile_phoneTV = (TextView) findViewById(R.id.mobile_phone);

        //endregion

        //region animation
        slideUp = AnimationUtils.loadAnimation(this,
                R.anim.slide_up);

        slideDown = AnimationUtils.loadAnimation(this,
                R.anim.slide_down);

        slideDown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (animation == slideUp) {
                    Toast.makeText(PostAd.this, "Animation Stopped",
                            Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(PostAd.this, "Animation Stopped",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        serviceCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryCV.setVisibility(View.VISIBLE);
                categoryCV.startAnimation(slideUp);
                categoryCV.setVisibility(View.GONE);
                sell_itemCV.setVisibility(View.VISIBLE);
                sell_itemCV.startAnimation(slideDown);
            }
        });

        electronicsCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sell_itemCV.setVisibility(View.VISIBLE);
                sell_itemCV.startAnimation(slideUp);
                sell_itemCV.setVisibility(View.GONE);
                allElectronicsCatCV.setVisibility(View.VISIBLE);
                allElectronicsCatCV.startAnimation(slideDown);
            }
        });


        //endregion


        mobile_phoneTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCustomAlert.setTitle(mobile_phoneTV.getText().toString());

                createCustomDialoge();
            }

        });
    }

    private void createCustomDialoge() {
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialoge, null);
        myCustomAlert.setView(dialogView);

        //region custom dialoge initialization

        post_add_locationET = (EditText) dialogView.findViewById(R.id.post_add_location);
        post_ad_descriptionET = (EditText) dialogView.findViewById(R.id.post_ad_description);
        post_ad_product_model_noET = (EditText) dialogView.findViewById(R.id.post_ad_product_model_no);
        post_ad_priceET = (EditText) dialogView.findViewById(R.id.post_ad_price);

        post_ad_product_conditionSP = (Spinner) dialogView.findViewById(R.id.post_ad_product_condition);
        post_ad_brandSP = (Spinner) dialogView.findViewById(R.id.post_ad_brand);
        post_ad_select_featureET = (EditText) dialogView.findViewById(R.id.post_ad_select_feature);
        post_ad_image_holderLL = (LinearLayout) dialogView.findViewById(R.id.post_ad_image_holder);
        post_ad_authenticitySP = (Spinner) dialogView.findViewById(R.id.post_ad_authenticity);

        post_ad_negotiableCB = (CheckBox) dialogView.findViewById(R.id.post_ad_negotiable);

        post_ad_photo_selectBTN = (Button) dialogView.findViewById(R.id.post_ad_photo_select);
        post_ad_post_adBTN = (Button) dialogView.findViewById(R.id.post_ad_post_ad);

        //endregion


        post_ad_photo_selectBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 0);
            }
        });

        post_ad_product_conditionSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                addCondition = post_ad_product_conditionSP.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        post_ad_brandSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                addBrand = post_ad_brandSP.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        post_ad_authenticitySP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                addAuthenticity = post_ad_authenticitySP.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        post_ad_negotiableCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean status = ((CheckBox)v).isChecked();
                switch (v.getId()){
                    case R.id.post_ad_negotiable:
                        if(status){
                            addNegotiable = post_ad_negotiableCB.getText().toString();
                        }
                }
            }
        });

        post_ad_post_adBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postAddintoServer();
            }
        });

        b = myCustomAlert.create();
        b.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            // When an Image is picked
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {

                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = this.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                // Set the Image in ImageView for Previewing the Media

                ImageView ii= new ImageView(this);
                ii.setLayoutParams(new android.view.ViewGroup.LayoutParams(160,160));
                ii.setMaxHeight(150);
                ii.setMaxWidth(150);
                ii.setScaleType(ImageView.ScaleType.FIT_XY);
                ii.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                mediaPathArraylist.add(mediaPath);
                post_ad_image_holderLL.addView(ii);

                cursor.close();

            } else {
                Toast.makeText(this, "You haven't picked Image/Video", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

    private void postAddintoServer() {
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://axionsoft.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService service = retrofit.create(APIService.class);

        //region upload images
        for (int i = 0; i< mediaPathArraylist.size();i++){

            File file = new File(mediaPathArraylist.get(i));
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"),file);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file",file.getName(),requestBody);
            RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

            String imageName = file.getName();
            String baseUrl = "http://axionsoft.net/Android/uploads/";
            String imageFullUrl = baseUrl + imageName;
            uploadedImageUrl.add(imageFullUrl);

            Call<ServerResponse> serverResponseCall = service.uploadFile(fileToUpload, filename);

            ServerResponse serverResponse = new ServerResponse();

            serverResponseCall.enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                    ServerResponse serverResponse = response.body();
                    if (serverResponse != null) {
                        if (serverResponse.getSuccess()) {
                            Toast.makeText(PostAd.this, serverResponse.getMessage(),Toast.LENGTH_SHORT).show();
                            mediaPathArraylist.clear();
                            uploadedImageUrl.clear();
                            progressDialog.dismiss();
                            b.dismiss();
                        } else {
                            Toast.makeText(PostAd.this, serverResponse.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        assert serverResponse != null;
                        Log.v("Response", serverResponse.toString());
                    }
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {

                }
            });
        }

        //endregion

        //region upload info
        String addLocation = post_add_locationET.getText().toString();
        String addDescription = post_ad_descriptionET.getText().toString();
        String addProductModelNo = post_ad_product_model_noET.getText().toString();
        String addPrice = post_ad_priceET.getText().toString();
        String addFeature = post_ad_select_featureET.getText().toString();

        String firstImageUrl ="",secondImageUrl ="" ,thirdImageUrl = "",forthImageUrl ="", fifthImageUrl ="";
        if (uploadedImageUrl.size()== 0){
            firstImageUrl = "";
            secondImageUrl = "";
            thirdImageUrl = "";
            forthImageUrl = "";
            fifthImageUrl = "";
        }else if(uploadedImageUrl.size()== 1){
            firstImageUrl = uploadedImageUrl.get(0);
        }else if(uploadedImageUrl.size()== 2){
            firstImageUrl = uploadedImageUrl.get(0);
            secondImageUrl = uploadedImageUrl.get(1);
        }else if(uploadedImageUrl.size()== 3){
            firstImageUrl = uploadedImageUrl.get(0);
            secondImageUrl = uploadedImageUrl.get(1);
            thirdImageUrl = uploadedImageUrl.get(2);
        }else if(uploadedImageUrl.size()== 4){
            firstImageUrl = uploadedImageUrl.get(0);
            secondImageUrl = uploadedImageUrl.get(1);
            thirdImageUrl = uploadedImageUrl.get(2);
            forthImageUrl = uploadedImageUrl.get(3);
        }else if(uploadedImageUrl.size()== 5){
            firstImageUrl = uploadedImageUrl.get(0);
            secondImageUrl = uploadedImageUrl.get(1);
            thirdImageUrl = uploadedImageUrl.get(2);
            forthImageUrl = uploadedImageUrl.get(3);
            fifthImageUrl = uploadedImageUrl.get(4);
        }

        Call<Add> addCall = service.postAd(addLocation,addDescription,addProductModelNo,
                addPrice,addCondition,addBrand,addFeature,addAuthenticity,addNegotiable,
                firstImageUrl,secondImageUrl,thirdImageUrl,forthImageUrl,fifthImageUrl);

        addCall.enqueue(new Callback<Add>() {
            @Override
            public void onResponse(Call<Add> call, Response<Add> response) {

            }

            @Override
            public void onFailure(Call<Add> call, Throwable t) {

            }
        });
        //endregion

    }
}
