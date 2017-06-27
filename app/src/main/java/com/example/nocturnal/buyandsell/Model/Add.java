package com.example.nocturnal.buyandsell.Model;

/**
 * Created by Nocturnal on 02-Jun-17.
 */

public class Add {
    private int addId;
    private int userId;
    private String add_Location;
    private String add_description;
    private String add_product_model_no;
    private String add_price;
    private String add_product_condition;
    private String add_brand;
    private String add_select_feature;
    private String add_authenticity;
    private String add_negotiable;
    private String add_category;

    private String fisrt_image_url;
    private String second_image_url;
    private String third_image_url;
    private String forth_image_url;
    private String fifth_image_url;


    public Add() {
    }

    public Add(String add_Location, String add_description,
               String add_product_model_no, String add_price, String add_product_condition,
               String add_brand, String add_select_feature, String add_authenticity,
               String add_negotiable) {
        this.add_Location = add_Location;
        this.add_description = add_description;
        this.add_product_model_no = add_product_model_no;
        this.add_price = add_price;
        this.add_product_condition = add_product_condition;
        this.add_brand = add_brand;
        this.add_select_feature = add_select_feature;
        this.add_authenticity = add_authenticity;
        this.add_negotiable = add_negotiable;
    }

    public Add(int addId, int userId, String add_Location,
               String add_description, String add_product_model_no,
               String add_price, String add_product_condition,
               String add_brand, String add_select_feature,
               String add_authenticity, String add_negotiable,
               String add_category, String fisrt_image_url,
               String second_image_url, String third_image_url,
               String forth_image_url, String fifth_image_url) {
        this.addId = addId;
        this.userId = userId;
        this.add_Location = add_Location;
        this.add_description = add_description;
        this.add_product_model_no = add_product_model_no;
        this.add_price = add_price;
        this.add_product_condition = add_product_condition;
        this.add_brand = add_brand;
        this.add_select_feature = add_select_feature;
        this.add_authenticity = add_authenticity;
        this.add_negotiable = add_negotiable;
        this.add_category = add_category;
        this.fisrt_image_url = fisrt_image_url;
        this.second_image_url = second_image_url;
        this.third_image_url = third_image_url;
        this.forth_image_url = forth_image_url;
        this.fifth_image_url = fifth_image_url;
    }

    public Add(String add_Location, String add_product_model_no, String add_price, String add_authenticity, String fisrt_image_url) {
        this.add_Location = add_Location;
        this.add_product_model_no = add_product_model_no;
        this.add_price = add_price;
        this.add_authenticity = add_authenticity;
        this.fisrt_image_url = fisrt_image_url;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAddId() {
        return addId;
    }

    public void setAddId(int addId) {
        this.addId = addId;
    }

    public String getAdd_Location() {
        return add_Location;
    }

    public void setAdd_Location(String add_Location) {
        this.add_Location = add_Location;
    }

    public String getAdd_description() {
        return add_description;
    }

    public void setAdd_description(String add_description) {
        this.add_description = add_description;
    }

    public String getAdd_product_model_no() {
        return add_product_model_no;
    }

    public void setAdd_product_model_no(String add_product_model_no) {
        this.add_product_model_no = add_product_model_no;
    }

    public String getAdd_price() {
        return add_price;
    }

    public void setAdd_price(String add_price) {
        this.add_price = add_price;
    }

    public String getAdd_product_condition() {
        return add_product_condition;
    }

    public void setAdd_product_condition(String add_product_condition) {
        this.add_product_condition = add_product_condition;
    }

    public String getAdd_brand() {
        return add_brand;
    }

    public void setAdd_brand(String add_brand) {
        this.add_brand = add_brand;
    }

    public String getAdd_select_feature() {
        return add_select_feature;
    }

    public void setAdd_select_feature(String add_select_feature) {
        this.add_select_feature = add_select_feature;
    }

    public String getAdd_authenticity() {
        return add_authenticity;
    }

    public void setAdd_authenticity(String add_authenticity) {
        this.add_authenticity = add_authenticity;
    }

    public String getAdd_negotiable() {
        return add_negotiable;
    }

    public void setAdd_negotiable(String add_negotiable) {
        this.add_negotiable = add_negotiable;
    }

    public String getFisrt_image_url() {
        return fisrt_image_url;
    }

    public void setFisrt_image_url(String fisrt_image_url) {
        this.fisrt_image_url = fisrt_image_url;
    }

    public String getSecond_image_url() {
        return second_image_url;
    }

    public void setSecond_image_url(String second_image_url) {
        this.second_image_url = second_image_url;
    }

    public String getThird_image_url() {
        return third_image_url;
    }

    public void setThird_image_url(String third_image_url) {
        this.third_image_url = third_image_url;
    }

    public String getForth_image_url() {
        return forth_image_url;
    }

    public void setForth_image_url(String forth_image_url) {
        this.forth_image_url = forth_image_url;
    }

    public String getFifth_image_url() {
        return fifth_image_url;
    }

    public void setFifth_image_url(String fifth_image_url) {
        this.fifth_image_url = fifth_image_url;
    }

    public String getAdd_category() {
        return add_category;
    }

    public void setAdd_category(String add_category) {
        this.add_category = add_category;
    }


}
