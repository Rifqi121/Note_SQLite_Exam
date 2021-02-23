package com.example.ulangansqlite_rifqi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton addbutton;
    static RecyclerView recyclerView;
    private RecyclerviewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Notes> noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        setupRecyclerView();

        addbutton = findViewById(R.id.addbutton);

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                startActivity(intent);
            }
        });

    }

    static void setupRecyclerView(Context context, List<Notes> noteList, RecyclerView recyclerView) {
        DatabaseHelper db = new DatabaseHelper(context);
        noteList = db.selectNoteData();

        RecyclerviewAdapter adapter = new RecyclerviewAdapter(noteList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setupRecyclerView() {
        DatabaseHelper db = new DatabaseHelper(this);
        noteList = db.selectNoteData();

        adapter = new RecyclerviewAdapter(noteList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}