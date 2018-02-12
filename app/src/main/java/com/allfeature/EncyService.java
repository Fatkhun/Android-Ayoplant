package com.allfeature;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Fatkhun on 12/01/2018.
 */

public interface EncyService {
    // Get data from ency endpoint.
    @GET("/encyclopedia/index")

    // Method to call endpoint above.
    Call<String> getEncyclopedia();
}
