package com.unitn.adapter_service;

import com.google.gson.GsonBuilder;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by erinda on 2/3/16.
 */
public class AdapterServiceImpl {
    static String BASE_URL = "https://nameless-basin-58575.herokuapp.com/";

    public static AdapterService createAdapterService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();

        return retrofit.create(AdapterService.class);
    }
}
