package com.example.noteapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.noteapp.Adapter.NoteListAdapter;
import com.example.noteapp.Database.RoomBD;
import com.example.noteapp.Interface.NotesClickListener;
import com.example.noteapp.Model.Notes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements  PopupMenu.OnMenuItemClickListener{

    RecyclerView recyclerView;
    NoteListAdapter noteListAdapter;
    RoomBD database;
    List<Notes> notes = new ArrayList<>();

    Notes selectedNotes;

    FloatingActionButton addBtnNote;


    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rvNote);
        addBtnNote = findViewById(R.id.addBtnNote);
        searchView = findViewById(R.id.searchView);

        database = RoomBD.getInstance(this);
        notes = database.MainDAO().getAll();

        updateRecycle(notes);


        addBtnNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, NotesTakeActivity.class);
                startActivityForResult(i,101);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    private void filter(String newText) {
        List<Notes> filterList = new ArrayList<>();
        for (Notes signalNote: notes) {
            if (signalNote.getTitle().toLowerCase().contains(newText.toLowerCase()) || signalNote.getNotes().toLowerCase().contains(newText.toLowerCase()) ){
                filterList.add(signalNote);
            }
        }

        noteListAdapter.filterList(filterList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == Activity.RESULT_OK) {
                Notes new_notes = (Notes) data.getSerializableExtra("note");
                database.MainDAO().insert(new_notes);
                notes.clear();
                notes.addAll(database.MainDAO().getAll());
                noteListAdapter.notifyDataSetChanged();
            }
        } else if (requestCode == 102) {
            if (resultCode == Activity.RESULT_OK) {
                Notes new_notes = (Notes) data.getSerializableExtra("note");
                database.MainDAO().update(new_notes.getID(), new_notes.getTitle(), new_notes.getNotes());
                notes.clear();
                notes.addAll(database.MainDAO().getAll());
                noteListAdapter.notifyDataSetChanged();
            }
        }
    }

    private void updateRecycle(List<Notes>  notes) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        noteListAdapter = new NoteListAdapter(MainActivity.this, notes, notesClickListener);
        recyclerView.setAdapter(noteListAdapter);
    }

    private final NotesClickListener notesClickListener = new NotesClickListener() {

        @Override
        public void onClick(Notes notes) {
            Intent i = new Intent(MainActivity.this, NotesTakeActivity.class);
            i.putExtra("old_notes", notes);
            startActivityForResult(i, 102);
        }

        @Override
        public void onLong(Notes notes, CardView cardView) {
            selectedNotes = new Notes();
            selectedNotes = notes;
            showPop(cardView);
        }
    };

    private void showPop(CardView cardView) {

        PopupMenu popupMenu = new PopupMenu(this, cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.pin) {
            if (selectedNotes.getPinned()) {
                database.MainDAO().Pin(selectedNotes.getID(), false);
                Toast.makeText(this, "UnPinned", Toast.LENGTH_SHORT).show();
            } else {
                database.MainDAO().Pin(selectedNotes.getID(), true);
                Toast.makeText(this, "Pinned", Toast.LENGTH_SHORT).show();
            }

            notes.clear();
            notes.addAll(database.MainDAO().getAll());
            noteListAdapter.notifyDataSetChanged();
            return true;
        } else if (item.getItemId() == R.id.delete) {
            database.MainDAO().delete(selectedNotes);
            notes.remove(selectedNotes);
            noteListAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Note is delete", Toast.LENGTH_SHORT).show();
        }

        return true;
    }
}