package com.allfeature;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Fatkhun on 15/01/2018.
 */

public interface PlantService {
    // Get data from ency endpoint.
    @GET("/plant/index")

    // Method to call endpoint above.
    Call<String> getPlant();
}
