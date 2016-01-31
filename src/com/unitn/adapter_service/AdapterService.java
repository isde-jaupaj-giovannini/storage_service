package com.unitn.adapter_service;

import com.google.gson.GsonBuilder;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.*;

import java.util.List;

/**
 * Created by demiurgo on 1/30/16.
 */


public interface AdapterService {
    String BASE_URL = "http://192.168.0.2:5700/";


    @POST("adapter_service/todo")
    Call<Project> createProject(@Body Project project);

    @GET("adapter_service/todo/{id}")
    Call<List<Task>> getTaskList(@Path("id") long projectId);

    @POST("adapter_service/todo/task")
    Call<Task> createTask(@Body Task task);

    @PUT("adapter_service/todo/{id}")
    Call<Task> updateTask(@Path("id") long taskId, @Body Task task);



    static AdapterService createAdapterService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();

        return retrofit.create(AdapterService.class);
    }
}
