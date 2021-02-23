package com.example.ulangansqlite_rifqi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.NoteViewHolder> {
    List<Notes> noteList;

    public RecyclerviewAdapter(List<Notes> noteList) {
        this.noteList = noteList;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.note_row_item, parent, false);
        NoteViewHolder noteViewHolder = new NoteViewHolder(view);

        return noteViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.txtdate.setText(noteList.get(position).getDate());
        holder.txtJudul.setText(noteList.get(position).getJudul());
        holder.txtDeskripsi.setText(noteList.get(position).getDeskripsi());
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView txtdate;
        TextView txtJudul;
        TextView txtDeskripsi;
        private CardView ListNote;
        private Context context;
        private List<Notes> noteList;
        private int id;
        private String judul;
        private String desk;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();

            txtdate = itemView.findViewById(R.id.txtdate);
            txtJudul = itemView.findViewById(R.id.title);
            txtDeskripsi = itemView.findViewById(R.id.description);
            ListNote = itemView.findViewById(R.id.cardlist);

            ListNote.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    alertAction(context, getAdapterPosition());

                    return false;
                }
            });
        }

        private void alertAction(Context context, int position) {
            String[] option = {"Edit", "Delete"};
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            DatabaseHelper db = new DatabaseHelper(context);
            noteList = db.selectNoteData();

            builder.setTitle("Choose option");
            builder.setItems(option, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (i == 0) {
                        id = noteList.get(position).getId();
                        judul = noteList.get(position).getJudul();
                        desk = noteList.get(position).getDeskripsi();

                        Intent intent = new Intent(context, EditActivity.class);
                        intent.putExtra("id", id);
                        intent.putExtra("judul", judul);
                        intent.putExtra("desk", desk);

                        context.startActivity(intent);

                    } else {
                        DatabaseHelper db = new DatabaseHelper(context);
                        db.delete(noteList.get(position).getId());

                        noteList = db.selectNoteData();
                        MainActivity.setupRecyclerView(context, noteList, MainActivity.recyclerView);

                    }
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

    }

}
