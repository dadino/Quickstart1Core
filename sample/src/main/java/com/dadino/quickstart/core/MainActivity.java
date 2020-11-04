package com.dadino.quickstart.core;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dadino.quickstart.R;
import com.dadino.quickstart.core.widgets.RecyclerLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerLayout<RateListAdapter, LinearLayoutManager> recyclerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> recyclerLayout.getAdapter().setItems(new ArrayList<>()));

        recyclerLayout = findViewById(R.id.sample_list);
        recyclerLayout.setAdapter(new RateListAdapter());
        recyclerLayout.setLayoutManager(new LinearLayoutManager(this));
        recyclerLayout.setEmptyText("Empty Text");
        recyclerLayout.setOnRefreshListener(() -> recyclerLayout.postDelayed(() -> {
            final ArrayList<Sample> samples = new ArrayList<>(30);
            for (int i = 0; i < 30; i++) {
                samples.add(new Sample(i));
            }
            recyclerLayout.getAdapter().setItems(samples);
            recyclerLayout.setListLoading(false);
        }, 1000));
    }
}
