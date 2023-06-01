package com.example.image_api;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class ImageListViewModel extends ViewModel {

    private MutableLiveData<List<Image>> images;
    private Retrofit retrofit;

    public LiveData<List<Image>> getImages() {
        if (images == null) {
            images = new MutableLiveData<>();
            loadImages();
        }
        return images;
    }

    private void loadImages() {
        // Create an OkHttpClient with logging interceptor
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(loggingInterceptor);

        // Create Gson instance for JSON parsing
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        // Create Retrofit instance
        retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();

        // Create API service
        ApiService apiService = retrofit.create(ApiService.class);

        // Make API request to fetch images
        Call<List<Image>> call = apiService.getImages();
        call.enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(Call<List<Image>> call, Response<List<Image>> response) {
                if (response.isSuccessful()) {
                    List<Image> imageList = response.body();
                    images.setValue(imageList);
                }
            }

            @Override
            public void onFailure(Call<List<Image>> call, Throwable t) {
                // Handle failure
            }
        });
    }
}
