package com.example.nocturnal.buyandsell.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nocturnal on 28-May-17.
 */

public class ServerResponse {

    boolean success;
    @SerializedName("message")
    String message;

    public String getMessage() {
        return message;
    }

    public boolean getSuccess() {
        return success;
    }
}
