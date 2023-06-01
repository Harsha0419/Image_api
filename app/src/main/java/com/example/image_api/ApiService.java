package com.example.image_api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("photos")
    Call<List<Image>> getImages();
}
