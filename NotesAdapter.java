package com.example.mydiabetesdiary;

import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {
    public static class NoteViewHolder extends RecyclerView.ViewHolder{
    LinearLayout containerView;
    TextView textView;


        NoteViewHolder(View view){
            super(view);
            containerView = view.findViewById(R.id.note_rows);
            textView = view.findViewById(R.id.note_row_text);

            containerView.setOnClickListener(new View.OnClickListener() {
                @Override
                //click listener for viewing an already made note
                public void onClick(View v) {
                    final Note current = (Note) containerView.getTag();
                    Intent intent = new Intent(v.getContext(),NoteActivity.class);
                    intent.putExtra("id", current.id);
                    intent.putExtra("contents", current.contents);
                    intent.putExtra("title", current.title);
                    intent.putExtra("carbs", current.carbs);
                    intent.putExtra("blood glucose", current.BG);
                    intent.putExtra("bolus", current.bolus);
                    intent.putExtra("date", current.date);
                    intent.putExtra("time", current.time);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    private List<Note> notes = new ArrayList<>();

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_rows, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note current = notes.get(position);
        holder.textView.setText(current.title);
        holder.containerView.setTag(current);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void reload(){
        notes = MainActivity.database.noteDao().getAll();
        notifyDataSetChanged();
    }
}

