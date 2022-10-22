package com.unix.notepad.activities

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.unix.notepad.database.NoteDBAdapter
import com.unix.notepad.databinding.ActivityAddNoteBinding
import com.unix.notepad.model.Note
import java.util.*


class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    lateinit var noteDBAdapter: NoteDBAdapter

    private lateinit var date: String
    private lateinit var time: String


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)


        noteDBAdapter = NoteDBAdapter(applicationContext)

        binding.imgBackAddNote.setOnClickListener(View.OnClickListener {
            finish()
        })

        val calendar = Calendar.getInstance()

        val bundle = intent.extras
        var isUpdate = bundle?.getBoolean("isUpdate")
        if (bundle != null) {
            binding.edtTitleAddNote.setText(bundle.getString("title"))
            binding.edtDescriptionAddNote.setText(bundle.getString("description"))
            binding.btnDateAddNote.text = bundle.getString("dateNote")
            binding.btnTimeAddNote.text = bundle.getString("timeNote")
            Log.i("TAG", "onCreate: ")
        } else {
            binding.btnDateAddNote.text = "Date"
            binding.btnTimeAddNote.text = "Time"
            Log.i("TAG", "onCreate: ")
        }

        binding.btnSaveAddNote.setOnClickListener(View.OnClickListener {


            if (isUpdate == true) {


                //      binding.edtTitleAddNote.text = getString(R.string.title)

                //   binding.edtTitleAddNote.text.toString()= "mmdkfm"


                bundle?.let {


                    val note: Note? = noteDBAdapter.getNote(bundle.getInt("id"))
                    note!!.title = binding.edtTitleAddNote.text.toString()
                    note.description = binding.edtDescriptionAddNote.text.toString()
                    note.date = bundle.getString("dateNote").toString()
                    note.time = bundle.getString("timeNote").toString()

                    noteDBAdapter.updateNote(note)

                    Log.i("AddNoteActivity", "result: " + noteDBAdapter.updateNote(note))

                    isUpdate = false
                }
                Snackbar.make(it, "Update Note Successfully", Snackbar.LENGTH_SHORT).show()


            } else {

                addNote(it)
            }


        })


        binding.btnDateAddNote.setOnClickListener(View.OnClickListener {

            val year = calendar[Calendar.YEAR]
            val month = calendar[Calendar.MONTH]
            val day = calendar[Calendar.DAY_OF_MONTH]

            val datePickerDialog = DatePickerDialog(
                this@AddNoteActivity,
                { datePicker, i, i1, i2 ->
                    date = "$i/$i1/$i2"
                    binding.btnDateAddNote.text = date
                }, year, month, day
            )
            datePickerDialog.show()


        })


        binding.btnTimeAddNote.setOnClickListener(View.OnClickListener {


            val dialog = TimePickerDialog(
                this@AddNoteActivity,
                { timePicker, i, i1 ->
                    time = "$i:$i1"
                    binding.btnTimeAddNote.text = time
                }, calendar[Calendar.HOUR_OF_DAY], calendar[Calendar.MINUTE], true
            )

            dialog.show()

        })


    }


    private fun addNote(view: View) {

        val note = Note(
            null,
            binding.edtTitleAddNote.text.toString(),
            binding.edtDescriptionAddNote.text.toString(),
            date,
            time
        )

        val result = noteDBAdapter.insertNote(note)

        if (result > 0) {
            Snackbar.make(view, "Added Note Successfully", Snackbar.LENGTH_SHORT).show()
            //Toast.makeText(AddNoteActivity.this, "Added Note Successfully", Toast.LENGTH_LONG).show();
        } else {
            Snackbar.make(view, "Error in Adding New Note", Snackbar.LENGTH_LONG).show()
            // Toast.makeText(AddNoteActivity.this, "Error in Adding New Note", Toast.LENGTH_SHORT).show();
        }

    }


}