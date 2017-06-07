package com.example.nocturnal.buyandsell.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {



    private ArrayList<Add>adds;
    private RecyclerView recyclerView;
    private AddAdapter addAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list, container, false);

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.post_ad);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PostAd.class);
                startActivity(intent);
            }
        });
        adds = new ArrayList<Add>();

        recyclerView = (RecyclerView) v.findViewById(R.id.AddList);
        createAdds();

        return v;
    }

    private void createAdds() {

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

                for (int i =0;i < response.body().size() ; i++){
                    String firstImage = response.body().get(i).getFisrt_image_url().toString();
                    String productModel = response.body().get(i).getAdd_product_model_no().toString();
                    String auth = response.body().get(i).getAdd_authenticity().toString();
                    String place = response.body().get(i).getAdd_Location().toString();
                    String price = response.body().get(i).getAdd_price().toString();
                    Add singleAd = new Add(place,productModel,price,auth,firstImage);
                    adds.add(singleAd);
                    Toast.makeText(getContext(), firstImage+productModel+auth+place+price, Toast.LENGTH_SHORT).show();
                }

                addAdapter = new AddAdapter(getContext(),adds);
                LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(llm);
                recyclerView.setAdapter(addAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Add>> call, Throwable t) {

            }
        });

    }

}
