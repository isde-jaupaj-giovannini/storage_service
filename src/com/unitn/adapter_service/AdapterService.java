package com.unitn.adapter_service;

import com.unitn.adapter_service.data.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * Created by demiurgo on 1/30/16.
 */


public interface AdapterService {


    @POST("adapter_service/todo")
    Call<Project> createProject(@Body Project project);

    @GET("adapter_service/todo/{id}")
    Call<List<Task>> getTaskList(@Path("id") long projectId);

    @GET("adapter_service/todo/done/{id}")
    Call<List<Task>> getDoneTaskList(@Path("id") long projectId);

    @POST("adapter_service/todo/task")
    Call<Task> createTask(@Body Task task);

    @PUT("adapter_service/todo")
    Call<Task> updateTask(@Body Task task);

    //Xkcd
    @GET("adapter_service/xkcd/random")
    Call<XkcdComic> randomComic();

    //Quotes
    @GET("adapter_service/quotes/famous")
    Call<Quote> famousQuote();

    @GET("adapter_service/quotes/movie")
    Call<Quote> movieQuote();

    @POST("adapter_service/chart")
    Call<Chart> getChart(@Body List<Integer> data);

}
