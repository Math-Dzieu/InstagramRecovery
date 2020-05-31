package com.example.instagramrecovery.presentation.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramrecovery.R;
import com.example.instagramrecovery.presentation.controller.DataActivityController;
import com.example.instagramrecovery.presentation.model.Data;
import com.google.gson.GsonBuilder;

import java.util.List;


public class DataActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public ImageButton btn_refresh;
    public DataActivityController dataActivityController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        btn_refresh = (ImageButton) findViewById(R.id.RefreshButton);

        dataActivityController = new DataActivityController(this, getBaseContext().getSharedPreferences("userData", MODE_PRIVATE), new GsonBuilder().setLenient().create());
        dataActivityController.onStart();

        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataActivityController.onItemClick();
            }
        });

    }

    public void showDataList(List<Data> dataList) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ListAdapter(dataList, new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Data item) {
                dataActivityController.navigateToDetails(item);
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

}