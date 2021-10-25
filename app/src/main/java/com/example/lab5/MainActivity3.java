package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity3 extends AppCompatActivity {

    int noteId  = -1;
    TextView noteView;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        noteView = (TextView) findViewById(R.id.newNote);


        Intent intent = getIntent();
        this.noteId = intent.getIntExtra("noteId", -1);
//



        if (this.noteId != -1){
            Note note = MainActivity2.notes.get(this.noteId);
            String noteContent = note.getContent();
            noteView.setText(noteContent);

        }



    }


    public void save(View view) {
        noteView = (TextView) findViewById(R.id.newNote);

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE,null);
        dbHelper = new DBHelper(sqLiteDatabase);

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5", Context.MODE_PRIVATE);

        String username = sharedPreferences.getString("username", "");

        String title;

        String content = (String) noteView.getText();

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        String date = dateFormat.format(new Date());

        if(noteId == -1){
            title = "NOTE_" + (MainActivity2.notes.size() + 1);
            dbHelper.saveNotes(username, title, content, date);
        }
        else {
            title = "NOTE_" + (noteId + 1);
            dbHelper.updateNote(title, date, content, username);
        }
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);

    }
}