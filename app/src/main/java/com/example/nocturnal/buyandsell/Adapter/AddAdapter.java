package com.example.nocturnal.buyandsell.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nocturnal.buyandsell.Activity.ShowFullAdInfo;
import com.example.nocturnal.buyandsell.Model.Add;
import com.example.nocturnal.buyandsell.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Nocturnal on 05-Jun-17.
 */

public class AddAdapter extends RecyclerView.Adapter<AddAdapter.AddViewHolder> {
    private Context context;
    private ArrayList<Add> addList;

    public AddAdapter(Context context, ArrayList<Add> addArrayList) {
        this.context = context;
        this.addList = addArrayList;
    }

    @Override
    public AddViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_ricyleview_single_row,parent,false);
        return new AddViewHolder(v,context,addList);
    }

    @Override
    public void onBindViewHolder(AddViewHolder holder, int position) {

        Picasso.with(context)
                .load(addList.get(position).getFisrt_image_url())
                .placeholder(R.drawable.dummy) //this is optional the image to display while the url image is downloading
                .error(R.drawable.dummy)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                .into(holder.adImage);
        holder.adProductNameTV.setText(addList.get(position).getAdd_product_model_no()+" "+addList.get(position).getAdd_authenticity());
        holder.adPlaceAndProductTypeTV.setText(addList.get(position).getAdd_Location()+", "+addList.get(position).getAdd_category());
        holder.productPriceTV.setText("Tk "+addList.get(position).getAdd_price());

    }

    @Override
    public int getItemCount() {
        return addList.size();
    }

    public class AddViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ArrayList<Add> arrayList = new ArrayList<Add>();
        Context context;

        private ImageView adImage;
        private TextView adProductNameTV,adPlaceAndProductTypeTV,productPriceTV;

        public AddViewHolder(View itemView , Context context , ArrayList<Add> addArrayList) {
            super(itemView);
            this.arrayList = addArrayList;
            this.context = context;
            itemView.setOnClickListener(this);
            adImage = (ImageView) itemView.findViewById(R.id.addImage);
            adProductNameTV = (TextView) itemView.findViewById(R.id.adProductName);
            adPlaceAndProductTypeTV = (TextView) itemView.findViewById(R.id.adPlaceAndProductType);
            productPriceTV = (TextView) itemView.findViewById(R.id.productPrice);


        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Add add = this.arrayList.get(position);
            Intent intent = new Intent(this.context,ShowFullAdInfo.class);

            String modelNo = add.getAdd_product_model_no();
            String price = add.getAdd_price();
            String negotiable = add.getAdd_negotiable();
            int userID = add.getUserId();
            String detail  = add.getAdd_description();
            String location = add.getAdd_Location();
            String condition = add.getAdd_product_condition();
            String category = add.getAdd_category();
            String brand = add.getAdd_brand();
            String time = add.getAdd_price();


            String firstImage = add.getFisrt_image_url();
            String secondImage = add.getSecond_image_url();
            String thirdImage = add.getThird_image_url();
            String forthImage = add.getForth_image_url();
            String fifthImage = add.getFifth_image_url();

            intent.putExtra("modelNo",modelNo);
            intent.putExtra("price",price);
            intent.putExtra("negotiable",negotiable);
            intent.putExtra("userID",userID);
            intent.putExtra("detail",detail);
            intent.putExtra("location",location);
            intent.putExtra("condition",condition);
            intent.putExtra("category",category);
            intent.putExtra("brand",brand);
            intent.putExtra("time",time);

            intent.putExtra("firstImage",firstImage);
            if (secondImage.isEmpty()){
                intent.putExtra("secondImage","empty");
            }else {
                intent.putExtra("secondImage",secondImage);
            }
            if (thirdImage.isEmpty()){
                intent.putExtra("thirdImage","empty");
            }else {
                intent.putExtra("thirdImage",thirdImage);
            }
            if (forthImage.isEmpty()){
                intent.putExtra("forthImage","empty");
            }else {
                intent.putExtra("forthImage",forthImage);
            }
            if (fifthImage.isEmpty()){
                intent.putExtra("fifthImage","empty");
            }else {
                intent.putExtra("fifthImage",fifthImage);
            }

            this.context.startActivity(intent);
        }
    }
}
