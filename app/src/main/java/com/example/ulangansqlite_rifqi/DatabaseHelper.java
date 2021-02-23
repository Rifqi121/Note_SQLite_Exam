package com.example.ulangansqlite_rifqi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "NoteInfo";
    private static final String TABLE_NAME = "tbl_note";
    private static final String KEY_ID = "_id";
    private static final String KEY_DATE = "date";
    private static final String KEY_TITLE = "judul";
    private static final String KEY_DESC = "deskripsi";

    public DatabaseHelper(@Nullable Context context) { super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createNoteTable = "CREATE TABLE " + TABLE_NAME + " (" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_DATE + " TEXT, " + KEY_TITLE + " TEXT, " + KEY_DESC + " TEXT" + ")";
        sqLiteDatabase.execSQL(createNoteTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    public void insert(Notes note) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());

        values.put(KEY_DATE, date);
        values.put(KEY_TITLE, note.getJudul());
        values.put(KEY_DESC, note.getDeskripsi());

        db.insert(TABLE_NAME, null, values);
    }

    public List<Notes> selectNoteData() {
        ArrayList<Notes> notes = new ArrayList<Notes>();

        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {KEY_ID, KEY_DATE, KEY_TITLE, KEY_DESC};

        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String date = cursor.getString(1);
            String judul = cursor.getString(2);
            String desk = cursor.getString(3);

            Notes note = new Notes();
            note.setId(id);
            note.setDate(date);
            note.setJudul(judul);
            note.setDeskripsi(desk);

            notes.add(note);
        }
        return notes;
    }

    public void update(Notes notes) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());

        values.put(KEY_DATE, date);
        values.put(KEY_TITLE, notes.getJudul());
        values.put(KEY_DESC, notes.getDeskripsi());

        String whereClause = KEY_ID + " = '" + notes.getId() + "'";

        db.update(TABLE_NAME, values, whereClause, null);
    }

    public void delete(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = KEY_ID + " = '" + id + "'";

        db.delete(TABLE_NAME, whereClause, null);
    }

}
