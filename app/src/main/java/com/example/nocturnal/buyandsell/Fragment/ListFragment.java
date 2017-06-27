package com.example.nocturnal.buyandsell.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.nocturnal.buyandsell.Activity.PostAd;
import com.example.nocturnal.buyandsell.Adapter.AddAdapter;
import com.example.nocturnal.buyandsell.Model.Add;
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
public class ListFragment extends Fragment {

    private ArrayList<Add>adds;
    private RecyclerView recyclerView;
    private AddAdapter addAdapter;
    private ImageButton refreshAllAdIBTN,searchbar_buttonIBTN;

    private SharedPreferences mypreferences;

    private ProgressDialog progressDialog;
    AlertDialog b;

    private Button searchByLocationBTN,searchByCategoryBTN;

    private AlertDialog.Builder searchByLocationDialoge;

    private AlertDialog.Builder searchByCategoryDialoge;

    private TextView locationMirpurTV,locationDhanmondiTV,locationGulsanTV,locationBonaniTV,locationUttoraTV,numberOfAddTV;

    private TextView MobilePhonesTV,ComputerTV,TVsTV,CamerasTV,VideoGamesTV;

    private String selectLocationForSearch,selectCategoryForSearch;

    private SearchView searchView;

    int counter = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list, container, false);

        mypreferences  = getActivity().getSharedPreferences("userInfo",MODE_PRIVATE);

        searchByLocationDialoge = new AlertDialog.Builder(getContext());
        searchByCategoryDialoge = new AlertDialog.Builder(getContext());

        searchView = (SearchView) v.findViewById(R.id.searchBar);
        recyclerView = (RecyclerView) v.findViewById(R.id.AddList);
        refreshAllAdIBTN = (ImageButton) v.findViewById(R.id.refreshAllAd);
        searchbar_buttonIBTN = (ImageButton) v.findViewById(R.id.searchbar_button);
        numberOfAddTV = (TextView) v.findViewById(R.id.numberOfAdd);
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.post_ad);

        String location = mypreferences.getString("location","");
        String phone = mypreferences.getString("phone","");
        String email = mypreferences.getString("email","");
        int userId = mypreferences.getInt("userId",0);
        if (location.isEmpty() || phone.isEmpty() || email.isEmpty()){
            fab.setVisibility(View.GONE);
        }
        searchbar_buttonIBTN.setVisibility(View.GONE);

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                if (counter%2 == 1){
                    refreshAllAdIBTN.setVisibility(View.GONE);
                    searchbar_buttonIBTN.setVisibility(View.VISIBLE);
                }else {
                    refreshAllAdIBTN.setVisibility(View.VISIBLE);
                    searchbar_buttonIBTN.setVisibility(View.GONE);
                }
            }
        });

        searchbar_buttonIBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String generalSearch = searchView.getQuery().toString();
                adds = new ArrayList<Add>();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://axionsoft.net/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                APIService service = retrofit.create(APIService.class);
                Call<ArrayList<Add>> allAdInfo = service.getAddInfoByGeneralSearch(generalSearch);

                allAdInfo.enqueue(new Callback<ArrayList<Add>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Add>> call, Response<ArrayList<Add>> response) {

                        if(response.body().size() == 0){
                            numberOfAddTV.setText(response.body().size()+" Ads Found");
                        }else {
                            numberOfAddTV.setText(response.body().size()+" Ads Found");
                            for (int i =0;i < response.body().size() ; i++){
                                String firstImage = response.body().get(i).getFisrt_image_url().toString();
                                String secondImage = response.body().get(i).getSecond_image_url().toString();
                                String thirdImage = response.body().get(i).getThird_image_url().toString();
                                String forthmage = response.body().get(i).getForth_image_url().toString();
                                String fifthImage = response.body().get(i).getFifth_image_url().toString();

                                int adId = response.body().get(i).getAddId();
                                String productModel = response.body().get(i).getAdd_product_model_no().toString();
                                String price = response.body().get(i).getAdd_price().toString();
                                String negotiable = response.body().get(i).getAdd_negotiable().toString();
                                int userid = response.body().get(i).getUserId();
                                String description = response.body().get(i).getAdd_description().toString();
                                String place = response.body().get(i).getAdd_Location().toString();
                                String auth = response.body().get(i).getAdd_authenticity().toString();
                                String condition = response.body().get(i).getAdd_product_condition().toString();
                                String brand = response.body().get(i).getAdd_brand().toString();
                                String category = response.body().get(i).getAdd_category().toString();
                                String feature = response.body().get(i).getAdd_select_feature().toString();
                                String time = response.body().get(i).getAdd_category().toString();

                                Add singleAd = new Add(adId,userid,place, description,productModel,price,condition,
                                        brand,feature,auth,negotiable,category,firstImage,secondImage,thirdImage,forthmage,fifthImage);
                                adds.add(singleAd);

                            }
                        }

                        addAdapter = new AddAdapter(getContext(),adds);
                        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(llm);
                        recyclerView.setAdapter(addAdapter);
                        progressDialog.dismiss();

                    }
                    @Override
                    public void onFailure(Call<ArrayList<Add>> call, Throwable t) {

                    }
                });
            }
        });

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Logging All Ads.....");

        searchByLocationBTN = (Button) v.findViewById(R.id.searchByLocation);
        searchByCategoryBTN = (Button) v.findViewById(R.id.searchByCategory);

        String testLoc = mypreferences.getString("location","");
        if (testLoc.isEmpty()){
            searchByLocationBTN.setText("All Location");
        }else {
            searchByLocationBTN.setText(mypreferences.getString("location",""));
        }

        searchByLocationBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchByLocationDialoge.setTitle("Choose Your Area...");
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View searchDialogView = inflater.inflate(R.layout.select_location, null);
                searchByLocationDialoge.setView(searchDialogView);

                locationMirpurTV = (TextView) searchDialogView.findViewById(R.id.locationMirpur);
                locationMirpurTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectLocationForSearch = locationMirpurTV.getText().toString();
                        searchByLocationBTN.setText(selectLocationForSearch);
                        createAddsByLocation(selectLocationForSearch);
                    }
                });
                locationDhanmondiTV = (TextView) searchDialogView.findViewById(R.id.locationDhanmondi);
                locationDhanmondiTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectLocationForSearch = locationDhanmondiTV.getText().toString();
                        searchByLocationBTN.setText(selectLocationForSearch);
                        createAddsByLocation(selectLocationForSearch);
                    }
                });
                locationGulsanTV = (TextView) searchDialogView.findViewById(R.id.locationGulsan);
                locationGulsanTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectLocationForSearch = locationGulsanTV.getText().toString();
                        searchByLocationBTN.setText(selectLocationForSearch);
                        createAddsByLocation(selectLocationForSearch);
                    }
                });
                locationBonaniTV = (TextView) searchDialogView.findViewById(R.id.locationBonani);
                locationBonaniTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectLocationForSearch = locationBonaniTV.getText().toString();
                        searchByLocationBTN.setText(selectLocationForSearch);
                        createAddsByLocation(selectLocationForSearch);
                    }
                });
                locationUttoraTV = (TextView) searchDialogView.findViewById(R.id.locationUttora);
                locationUttoraTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectLocationForSearch = locationUttoraTV.getText().toString();
                        searchByLocationBTN.setText(selectLocationForSearch);
                        createAddsByLocation(selectLocationForSearch);
                    }
                });

                b = searchByLocationDialoge.create();
                b.show();
            }
        });

        searchByCategoryBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchByCategoryDialoge.setTitle("Choose Your Category...");
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View searchDialogView = inflater.inflate(R.layout.select_category, null);
                searchByCategoryDialoge.setView(searchDialogView);

                MobilePhonesTV = (TextView) searchDialogView.findViewById(R.id.MobilePhones);
                MobilePhonesTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectCategoryForSearch = MobilePhonesTV.getText().toString();
                        //Toast.makeText(getContext(), MobilePhonesTV.getText().toString(), Toast.LENGTH_SHORT).show();
                        createAddsByCategory(selectCategoryForSearch);
                    }
                });
                ComputerTV = (TextView) searchDialogView.findViewById(R.id.Computer);
                ComputerTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectCategoryForSearch = ComputerTV.getText().toString();
                        createAddsByCategory(selectCategoryForSearch);
                    }
                });
                TVsTV = (TextView) searchDialogView.findViewById(R.id.TVs);
                TVsTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectCategoryForSearch = TVsTV.getText().toString();
                        createAddsByCategory(selectCategoryForSearch);
                    }
                });
                CamerasTV = (TextView) searchDialogView.findViewById(R.id.Cameras);
                CamerasTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectCategoryForSearch = CamerasTV.getText().toString();
                        createAddsByCategory(selectCategoryForSearch);
                    }
                });
                VideoGamesTV = (TextView) searchDialogView.findViewById(R.id.VideoGames);
                VideoGamesTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectCategoryForSearch = VideoGamesTV.getText().toString();
                        createAddsByCategory(selectCategoryForSearch);
                    }
                });

                b = searchByCategoryDialoge.create();
                b.show();
            }
        });

        //region fab on click
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), PostAd.class);
                startActivity(intent);

                /*Date date = new Date();
                date.getTime();
                DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getContext());
                dateFormat.format(date.getTime()).toString();
                Toast.makeText(getContext(), dateFormat.format(date.getTime()).toString(), Toast.LENGTH_SHORT).show();*/

            }
        });
        //endregion

        adds = new ArrayList<Add>();
        refreshAllAdIBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAdds();
            }
        });
        createAdds();
        return v;
    }

    private void createAddsByLocation(String selectLocationForSearch) {
        progressDialog.show();
        adds = new ArrayList<Add>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://axionsoft.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);
        Call<ArrayList<Add>> allAdInfo = service.getAddInfoByLocation(selectLocationForSearch);

        allAdInfo.enqueue(new Callback<ArrayList<Add>>() {
            @Override
            public void onResponse(Call<ArrayList<Add>> call, Response<ArrayList<Add>> response) {

                if(response.body().size() == 0){
                    numberOfAddTV.setText(response.body().size()+" Ads Found");
                }else {
                    numberOfAddTV.setText(response.body().size()+" Ads Found");
                    for (int i =0;i < response.body().size() ; i++){
                        String firstImage = response.body().get(i).getFisrt_image_url().toString();
                        String secondImage = response.body().get(i).getSecond_image_url().toString();
                        String thirdImage = response.body().get(i).getThird_image_url().toString();
                        String forthmage = response.body().get(i).getForth_image_url().toString();
                        String fifthImage = response.body().get(i).getFifth_image_url().toString();

                        int adId = response.body().get(i).getAddId();
                        String productModel = response.body().get(i).getAdd_product_model_no().toString();
                        String price = response.body().get(i).getAdd_price().toString();
                        String negotiable = response.body().get(i).getAdd_negotiable().toString();
                        int userid = response.body().get(i).getUserId();
                        String description = response.body().get(i).getAdd_description().toString();
                        String place = response.body().get(i).getAdd_Location().toString();
                        String auth = response.body().get(i).getAdd_authenticity().toString();
                        String condition = response.body().get(i).getAdd_product_condition().toString();
                        String brand = response.body().get(i).getAdd_brand().toString();
                        String category = response.body().get(i).getAdd_category().toString();
                        String feature = response.body().get(i).getAdd_select_feature().toString();
                        String time = response.body().get(i).getAdd_category().toString();


                        //Add singleAd = new Add(place,productModel,price,auth,firstImage);
                        Add singleAd = new Add(adId,userid,place, description,productModel,price,condition,
                                brand,feature,auth,negotiable,category,firstImage,secondImage,thirdImage,forthmage,fifthImage);
                        adds.add(singleAd);
                        b.dismiss();
                    }
                }

                addAdapter = new AddAdapter(getContext(),adds);
                LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(llm);
                recyclerView.setAdapter(addAdapter);
                progressDialog.dismiss();
                b.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<Add>> call, Throwable t) {

            }
        });
    }

    private void createAddsByCategory(String selectCategoryForSearch) {
        progressDialog.show();
        adds = new ArrayList<Add>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://axionsoft.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);
        Call<ArrayList<Add>> allAdInfo = service.getAddInfoByCategory(selectCategoryForSearch);

        allAdInfo.enqueue(new Callback<ArrayList<Add>>() {
            @Override
            public void onResponse(Call<ArrayList<Add>> call, Response<ArrayList<Add>> response) {

                if(response.body().size() == 0){
                    numberOfAddTV.setText(response.body().size()+" Ads Found");
                }else {
                    numberOfAddTV.setText(response.body().size()+" Ads Found");
                    for (int i =0;i < response.body().size() ; i++){
                        String firstImage = response.body().get(i).getFisrt_image_url().toString();
                        String secondImage = response.body().get(i).getSecond_image_url().toString();
                        String thirdImage = response.body().get(i).getThird_image_url().toString();
                        String forthmage = response.body().get(i).getForth_image_url().toString();
                        String fifthImage = response.body().get(i).getFifth_image_url().toString();

                        int adId = response.body().get(i).getAddId();
                        String productModel = response.body().get(i).getAdd_product_model_no().toString();
                        String price = response.body().get(i).getAdd_price().toString();
                        String negotiable = response.body().get(i).getAdd_negotiable().toString();
                        int userid = response.body().get(i).getUserId();
                        String description = response.body().get(i).getAdd_description().toString();
                        String place = response.body().get(i).getAdd_Location().toString();
                        String auth = response.body().get(i).getAdd_authenticity().toString();
                        String condition = response.body().get(i).getAdd_product_condition().toString();
                        String brand = response.body().get(i).getAdd_brand().toString();
                        String category = response.body().get(i).getAdd_category().toString();
                        String feature = response.body().get(i).getAdd_select_feature().toString();
                        String time = response.body().get(i).getAdd_category().toString();

                        Add singleAd = new Add(adId,userid,place, description,productModel,price,condition,
                                brand,feature,auth,negotiable,category,firstImage,secondImage,thirdImage,forthmage,fifthImage);
                        adds.add(singleAd);
                        b.dismiss();
                    }
                }

                addAdapter = new AddAdapter(getContext(),adds);
                LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(llm);
                recyclerView.setAdapter(addAdapter);
                progressDialog.dismiss();
                b.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<Add>> call, Throwable t) {

            }
        });
    }

    private void createAdds() {
        progressDialog.show();
        adds = new ArrayList<Add>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://axionsoft.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);
        Call<ArrayList<Add>> allAdInfo = service.getAllAdinInfo();

        allAdInfo.enqueue(new Callback<ArrayList<Add>>() {
            @Override
            public void onResponse(Call<ArrayList<Add>> call, Response<ArrayList<Add>> response) {

                numberOfAddTV.setText(response.body().size()+" Ads Found");
                for (int i =0;i < response.body().size() ; i++){
                    String firstImage = response.body().get(i).getFisrt_image_url().toString();
                    String secondImage = response.body().get(i).getSecond_image_url().toString();
                    String thirdImage = response.body().get(i).getThird_image_url().toString();
                    String forthmage = response.body().get(i).getForth_image_url().toString();
                    String fifthImage = response.body().get(i).getFifth_image_url().toString();

                    int adId = response.body().get(i).getAddId();
                    String productModel = response.body().get(i).getAdd_product_model_no().toString();
                    String price = response.body().get(i).getAdd_price().toString();
                    String negotiable = response.body().get(i).getAdd_negotiable().toString();
                    int userid = response.body().get(i).getUserId();
                    String description = response.body().get(i).getAdd_description().toString();
                    String place = response.body().get(i).getAdd_Location().toString();
                    String auth = response.body().get(i).getAdd_authenticity().toString();
                    String condition = response.body().get(i).getAdd_product_condition().toString();
                    String brand = response.body().get(i).getAdd_brand().toString();
                    String category = response.body().get(i).getAdd_category().toString();
                    String feature = response.body().get(i).getAdd_select_feature().toString();
                    String time = response.body().get(i).getAdd_category().toString();


                    //Add singleAd = new Add(place,productModel,price,auth,firstImage);
                    Add singleAd = new Add(adId,userid,place, description,productModel,price,condition,
                            brand,feature,auth,negotiable,category,firstImage,secondImage,thirdImage,forthmage,fifthImage);
                    adds.add(singleAd);
                }

                addAdapter = new AddAdapter(getContext(),adds);
                LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(llm);
                recyclerView.setAdapter(addAdapter);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<Add>> call, Throwable t) {

            }
        });

    }

}
