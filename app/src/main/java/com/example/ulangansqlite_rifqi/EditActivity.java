package com.example.ulangansqlite_rifqi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class EditActivity extends AppCompatActivity {
    private TextInputLayout edtJudul;
    private TextInputLayout edtDesk;
    private Button btnEdit;
    private DatabaseHelper db;
    private Bundle intentData;
    private Notes notes;
    private String judul;
    private String desk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        edtJudul = findViewById(R.id.inputlayoutjudul);
        edtDesk = findViewById(R.id.inputlayoutdeskripsi);
        btnEdit = findViewById(R.id.btnUpdate);
        db = new DatabaseHelper(this);

        intentData = getIntent().getExtras();
        edtJudul.getEditText().setText(intentData.getString("judul"));
        edtDesk.getEditText().setText(intentData.getString("desk"));

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                judul = edtJudul.getEditText().getText().toString();
                desk = edtDesk.getEditText().getText().toString();

                if (judul.isEmpty() || desk.isEmpty()) {
                    Snackbar.make(view, "Data diatas kosong", Snackbar.LENGTH_SHORT).show();
                } else {
                    notes = new Notes();
                    notes.setId(intentData.getInt("id"));
                    notes.setJudul(judul);
                    notes.setDeskripsi(desk);
                    db.update(notes);

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("status", "edit");
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.deletebutton, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.btnHapus) {
            db = new DatabaseHelper(this);
            db.delete(intentData.getInt("id"));

            Intent intent = new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("status", "delete");
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
