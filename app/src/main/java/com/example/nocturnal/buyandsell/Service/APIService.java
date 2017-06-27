package com.example.nocturnal.buyandsell.Service;

import com.example.nocturnal.buyandsell.Model.Add;
import com.example.nocturnal.buyandsell.Model.ServerResponse;
import com.example.nocturnal.buyandsell.Model.Users;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Nocturnal on 26-May-17.
 */

public interface APIService {

    @FormUrlEncoded
    @POST("Android/registerUser.php")
    Call<Users> registerUser(@Field("username") String username,@Field("email") String email,@Field("password") String password);

    @GET("Android/loginUser.php")
    Call<ArrayList<Users>>getLoginInfo(@Query("email") String email);

    @Multipart
    @POST("Android/uploadImage.php")
    Call<ServerResponse> uploadFile(@Part MultipartBody.Part file, @Part("file") RequestBody name);

    @FormUrlEncoded
    @POST("Android/updateUserInfo.php")
    Call<Users> updateUser(@Field("userId") int userId,
                           @Field("userFullName") String userFullName,
                           @Field("userLocation") String userLocation,
                           @Field("imageFullUrl") String imageFullUrl,
                           @Field("userPhone") String userPhone);

    @FormUrlEncoded
    @POST("Android/postAdd.php")
    Call<Add> postAd(@Field("addLocation") String addLocation,
                     @Field("addDescription") String addDescription,
                     @Field("addProductModelNo") String addProductModelNo,
                     @Field("addPrice") String addPrice,
                     @Field("addCondition") String addCondition,
                     @Field("addBrand") String addBrand,
                     @Field("addFeature") String addFeature,
                     @Field("addAuthenticity") String addAuthenticity,
                     @Field("addNegotiable") String addNegotiable,
                     @Field("firstImageUrl") String firstImageUrl,
                     @Field("secondImageUrl") String secondImageUrl,
                     @Field("thirdImageUrl") String thirdImageUrl,
                     @Field("forthImageUrl") String forthImageUrl,
                     @Field("fifthImageUrl") String fifthImageUrl,
                     @Field("userId") int userId,
                     @Field("category") String category
                     );

    @GET("Android/getAllAd.php")
    Call<ArrayList<Add>>getAllAdinInfo();

    @GET("Android/searchAdByLocation.php")
    Call<ArrayList<Add>>getAddInfoByLocation(@Query("selectLocationForSearch") String selectLocationForSearch);

    @GET("Android/searchAdByCategory.php")
    Call<ArrayList<Add>>getAddInfoByCategory(@Query("selectCategoryForSearch") String selectCategoryForSearch);


    @GET("Android/getAdOwnerInfo.php")
    Call<ArrayList<Users>>getAdOwnerInfo(@Query("userID") int userID);

    @GET("Android/generalSearch.php")
    Call<ArrayList<Add>>getAddInfoByGeneralSearch(@Query("generalSearch") String generalSearch);

}
