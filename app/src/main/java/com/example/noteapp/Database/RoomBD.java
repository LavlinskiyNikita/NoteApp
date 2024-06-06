package com.example.noteapp.Database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.noteapp.Model.Notes;

@Database(entities = Notes.class, version = 1)
public abstract class RoomBD extends RoomDatabase {
    private static RoomBD database;
    private static String DATABASE_NAME = "NoteApp";

    public synchronized static RoomBD getInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(), RoomBD.class, DATABASE_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }

        return database;
    }

    public abstract MainDAO MainDAO();
}
