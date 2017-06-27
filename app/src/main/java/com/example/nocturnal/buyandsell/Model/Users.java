package com.example.nocturnal.buyandsell.Model;

/**
 * Created by Nocturnal on 26-May-17.
 */

public class Users {

    private String username;
    private String email;
    private String password;
    private int id;
    private String user_full_name;
    private String user_location;
    private String user_image_url;
    private String user_phone;
    private String user_type;

    public Users(int id, String user_full_name, String user_location, String user_image_url, String user_phone) {
        this.id = id;
        this.user_full_name = user_full_name;
        this.user_location = user_location;
        this.user_image_url = user_image_url;
        this.user_phone = user_phone;
    }

    public Users(String username, String email, String password, int id) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.id = id;
    }


    public Users(String username, String email, String password, int id, String user_full_name, String user_location, String user_image_url, String user_phone, String user_type) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.id = id;
        this.user_full_name = user_full_name;
        this.user_location = user_location;
        this.user_image_url = user_image_url;
        this.user_phone = user_phone;
        this.user_type = user_type;
    }

    public Users(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Users() {
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_full_name() {
        return user_full_name;
    }

    public void setUser_full_name(String user_full_name) {
        this.user_full_name = user_full_name;
    }

    public String getUser_location() {
        return user_location;
    }

    public void setUser_location(String user_location) {
        this.user_location = user_location;
    }

    public String getUser_image_url() {
        return user_image_url;
    }

    public void setUser_image_url(String user_image_url) {
        this.user_image_url = user_image_url;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}
