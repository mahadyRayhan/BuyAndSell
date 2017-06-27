package com.example.nocturnal.buyandsell.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nocturnal.buyandsell.Model.ServerResponse;
import com.example.nocturnal.buyandsell.Model.Users;
import com.example.nocturnal.buyandsell.R;
import com.example.nocturnal.buyandsell.Service.APIService;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditMyAccountFragment extends Fragment {

    private ImageView edit_profile_imageEV;
    private EditText edit_full_nameET,edit_areaET,edit_phoneET,user_reggester_emailET;
    private Button edit_location_from_gpsBTN;

    ProgressDialog progressDialog;

    private String mediaPath;
    String[] mediaColumns = { MediaStore.Video.Media._ID };

    MenuItem save_menu_item,edit_menu_item;
    private int userId;

    private SharedPreferences mypreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_my_account, container, false);
        mypreferences  = getActivity().getSharedPreferences("userInfo",MODE_PRIVATE);
        setHasOptionsMenu(true);

        edit_profile_imageEV = (ImageView) v.findViewById(R.id.edit_profile_image);
        edit_full_nameET = (EditText) v.findViewById(R.id.edit_full_name);
        edit_areaET = (EditText) v.findViewById(R.id.edit_area);
        user_reggester_emailET = (EditText) v.findViewById(R.id.user_reggester_email);
        edit_phoneET = (EditText) v.findViewById(R.id.edit_phone);
        edit_location_from_gpsBTN = (Button) v.findViewById(R.id.edit_location_from_gps);

        String email = mypreferences.getString("email","");
        userId = mypreferences.getInt("userId",0);

        user_reggester_emailET.setText(email);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Completiong your account.....");

        edit_profile_imageEV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 0);
            }
        });

        return  v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            // When an Image is picked
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {

                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};


                Cursor cursor = getContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                // Set the Image in ImageView for Previewing the Media
                edit_profile_imageEV.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();

            } else {
                Toast.makeText(getContext(), "You haven't picked Image/Video", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.my_account_menu_save,menu);
        edit_menu_item = menu.findItem(R.id.edit_account);
        edit_menu_item.setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_account:
                uploadFile();
                return true;
        }

        return false;
    }

    private void uploadFile() {
        progressDialog.show();
        File file = new File(mediaPath);

        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"),file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file",file.getName(),requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://axionsoft.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String imageName = file.getName();
        String baseUrl = "http://axionsoft.net/Android/uploads/";
        String imageFullUrl = baseUrl + imageName;
        Toast.makeText(getContext(), imageFullUrl, Toast.LENGTH_SHORT).show();
        String userFullName = edit_full_nameET.getText().toString();
        String userLocation = edit_areaET.getText().toString();
        String userPhone = edit_phoneET.getText().toString();

        APIService service = retrofit.create(APIService.class);
        Call<ServerResponse> serverResponseCall = service.uploadFile(fileToUpload, filename);

        ServerResponse serverResponse = new ServerResponse();

        serverResponseCall.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse = response.body();
                if (serverResponse != null) {
                    if (serverResponse.getSuccess()) {
                        Toast.makeText(getContext(), serverResponse.getMessage(),Toast.LENGTH_SHORT).show();
                        
                    } else {
                        Toast.makeText(getContext(), serverResponse.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                } else {
                    assert serverResponse != null;
                    Log.v("Response", serverResponse.toString());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });

        Users useraccountFullinfo = new Users(userId,userFullName,userLocation,imageFullUrl,userPhone);
        Call<Users> registerUpdateCall = service.updateUser(useraccountFullinfo.getId(),
                useraccountFullinfo.getUser_full_name(),
                useraccountFullinfo.getUser_location(),
                useraccountFullinfo.getUser_image_url(),
                useraccountFullinfo.getUser_phone());

        registerUpdateCall.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {

            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });
    }
}
