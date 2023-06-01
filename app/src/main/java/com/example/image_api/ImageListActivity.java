package com.example.image_api;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ImageListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageAdapter adapter;
    private ImageListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ImageAdapter();
        recyclerView.setAdapter(adapter);


        ViewModelProvider.Factory factory = new ViewModelFactory();
        viewModel = new ViewModelProvider(this, factory).get(ImageListViewModel.class);
        viewModel.getImages().observe(this, new Observer<List<Image>>() {
            @Override
            public void onChanged(List<Image> images) {
                adapter.setImages(images);
            }
        });
    }
}
