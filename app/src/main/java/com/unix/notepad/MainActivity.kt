package com.unix.notepad

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unix.notepad.activities.AddNoteActivity
import com.unix.notepad.adapter.NoteAdapter
import com.unix.notepad.database.NoteDBAdapter
import com.unix.notepad.database.NoteDatabase
import com.unix.notepad.databinding.ActivityMainBinding
import com.unix.notepad.model.Note


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var noteDatabase: NoteDatabase
    lateinit var noteDBAdapter: NoteDBAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        noteDatabase = NoteDatabase(applicationContext)
        noteDBAdapter = NoteDBAdapter(applicationContext)


        binding.fabAddMain.setOnClickListener(View.OnClickListener {
         //   var intent = Intent(this,AddNoteActivity.class)
            val intent = Intent(this@MainActivity, AddNoteActivity::class.java)
            startActivity(intent)

        })

    }


    override fun onResume() {
        super.onResume()


        val noteList: List<Note> = noteDBAdapter.showAllNote()
        val noteAdapter = NoteAdapter(this@MainActivity, noteList)
        val manager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, true)
        Log.i("TAG", "onResume: ")


        binding.recyclerNotes.adapter = noteAdapter
        binding.recyclerNotes.layoutManager = manager



    }




}