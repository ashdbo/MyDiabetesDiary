package com.example.mydiabetesdiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.Calendar;

public class NoteActivity extends AppCompatActivity {
    private EditText noteEdit;
    private EditText bgEdit;
    private EditText carbsEdit;
    private EditText bolusEdit;
    private EditText titleEdit;
    private TextView noteText;
    private TextView bgText;
    private TextView carbsText;
    private TextView bolusText;
    private TextView dateText;
    private TextView timeText;
    private TextView bgUnit;
    private TextView carbsUnit;
    private TextView bolusUnit;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_note);
        noteEdit = findViewById(R.id.note_edit);
        noteEdit.setTypeface(null, Typeface.NORMAL);
        noteText = findViewById(R.id.note_text);
        noteText.setTypeface(null, Typeface.BOLD);

        bgEdit = findViewById(R.id.bg_edit);
        bgEdit.setTypeface(null, Typeface.NORMAL);
        bgText = findViewById(R.id.bg_text);
        bgText.setTypeface(null, Typeface.BOLD);
        bgUnit = findViewById(R.id.BG_unit);
        bgUnit.setTypeface(null, Typeface.ITALIC);

        carbsEdit = findViewById(R.id.carbs_edit);
        carbsEdit.setTypeface(null, Typeface.NORMAL);
        carbsText = findViewById(R.id.carbs_text);
        carbsText.setTypeface(null, Typeface.BOLD);
        carbsUnit = findViewById(R.id.Carbs_unit);
        carbsUnit.setTypeface(null, Typeface.ITALIC);

        bolusEdit = findViewById(R.id.bolus_edit);
        bolusEdit.setTypeface(null, Typeface.NORMAL);
        bolusText = findViewById(R.id.bolus_text);
        bolusText.setTypeface(null, Typeface.BOLD);
        bolusUnit = findViewById(R.id.bolus_unit);
        bolusUnit.setTypeface(null, Typeface.ITALIC);

        titleEdit = findViewById(R.id.title_edit);
        titleEdit.setTypeface(null, Typeface.NORMAL);


        dateText = findViewById(R.id.date_text);
        dateText.setTypeface(null, Typeface.NORMAL);

        timeText = findViewById(R.id.time_text);
        timeText.setTypeface(null,Typeface.NORMAL);

        id = getIntent().getIntExtra("id", 0);

        String contents = getIntent().getStringExtra("contents");
        noteEdit.setText(contents);

        String bg = getIntent().getStringExtra("blood glucose");
        bgEdit.setText(bg);

        String carbs = getIntent().getStringExtra("carbs");
        carbsEdit.setText(carbs);

        String bolus = getIntent().getStringExtra("bolus");
        bolusEdit.setText(bolus);

        String title = getIntent().getStringExtra("title");
        titleEdit.setText(title);

        String date = getIntent().getStringExtra("date");
        dateText.setText(date);
        dateText.setTypeface(null, Typeface.BOLD);

        String time = getIntent().getStringExtra("time");
        timeText.setText(time);
        timeText.setTypeface(null, Typeface.BOLD);


    }
    @Override
    protected void onPause(){
        super.onPause();
        MainActivity.database.noteDao().saveTitle(titleEdit.getText().toString(), id);
        MainActivity.database.noteDao().saveContent(noteEdit.getText().toString(), id);
        MainActivity.database.noteDao().saveBG(bgEdit.getText().toString(),id);
        MainActivity.database.noteDao().saveCarbs(carbsEdit.getText().toString(), id);
        MainActivity.database.noteDao().saveBolus(bolusEdit.getText().toString(), id);

    }

    public void editCarbs(View view) {
        carbsEdit.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(carbsEdit, InputMethodManager.SHOW_IMPLICIT);
    }

    public void editBG(View view) {
        bgEdit.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(bgEdit, InputMethodManager.SHOW_IMPLICIT);
    }

    public void editBolus(View view) {
        bolusEdit.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(bolusEdit, InputMethodManager.SHOW_IMPLICIT);
    }
}