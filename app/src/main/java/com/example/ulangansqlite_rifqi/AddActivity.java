package com.example.ulangansqlite_rifqi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class AddActivity extends AppCompatActivity {
    private TextInputLayout edtJudul;
    private TextInputLayout edtDesk;
    private Button btnAdd;
    private DatabaseHelper db;
    private Notes notes;
    private String judul;
    private String desk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        edtJudul = findViewById(R.id.inputlayoutjudul);
        edtDesk = findViewById(R.id.inputlayoutdeskripsi);
        btnAdd = findViewById(R.id.btnAdd);
        db = new DatabaseHelper(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                judul = edtJudul.getEditText().getText().toString();
                desk = edtDesk.getEditText().getText().toString();

                if (judul.isEmpty() || desk.isEmpty()) {
                    Snackbar.make(view, "Data diatas masih kosong", Snackbar.LENGTH_SHORT).show();
                } else {
                    notes = new Notes();
                    notes.setJudul(judul);
                    notes.setDeskripsi(desk);
                    db.insert(notes);

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("status", "add");
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

}
