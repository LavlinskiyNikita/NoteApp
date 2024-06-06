package com.example.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.noteapp.Model.Notes;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotesTakeActivity extends AppCompatActivity {

    EditText titleEdt, noteEdt;
    ImageView saveBtn;
    Notes notes;


    boolean isOldNotes = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_take);

        saveBtn = findViewById(R.id.saveBtn);
        titleEdt = findViewById(R.id.titleEdt);
        noteEdt = findViewById(R.id.noteEdt);

        notes = new Notes();
        try {
           notes = (Notes) getIntent().getSerializableExtra("old_notes");
           titleEdt.setText(notes.getTitle());
           noteEdt.setText(notes.getNotes());
           isOldNotes = true;
        } catch (Exception e) {
            e.printStackTrace();
        }


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!isOldNotes) {
                    notes = new Notes();
                }


                String title = titleEdt.getText().toString();
                String description = noteEdt.getText().toString();

                if (description.isEmpty()) {
                    Toast.makeText(NotesTakeActivity.this, "пожалуйста, ввидите текст", Toast.LENGTH_SHORT).show();
                    return;
                }

                SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm a");
                Date date = new Date();

                notes.setTitle(title);
                notes.setNotes(description);
                notes.setDate(format.format(date));

                Intent i = new Intent();
                i.putExtra("note", notes);
                setResult(Activity.RESULT_OK, i);
                finish();
            }
        });
    }
}