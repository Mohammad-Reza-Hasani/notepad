package com.unix.notepad.database

import android.content.ContentValues
import android.content.Context
import com.unix.notepad.model.Note
import kotlin.Int
import kotlin.Long
import kotlin.arrayOf

class NoteDBAdapter(context: Context?) : NoteDatabase(context) {

    fun insertNote(note: Note): Long {
        val db = writableDatabase
        val contentValues = ContentValues()
        contentValues.put("title", note.title)
        contentValues.put("description", note.description)
        contentValues.put("dateNote", note.date)
        contentValues.put("timeNote", note.time)
        return db.insert("tbl_note", null, contentValues)
    }


    fun showAllNote(): List<Note> {
        val noteList: MutableList<Note> = ArrayList<Note>()
        val db = readableDatabase
        val cursor = db.rawQuery("Select * From tbl_note", null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val title = cursor.getString(1)
            val description = cursor.getString(2)
            val date = cursor.getString(3)
            val time = cursor.getString(4)
            val note = Note(id, title, description, date, time)
            noteList.add(note)
        }
        cursor.close()
        return noteList
    }


    fun deleteNote(id: Int) {
        val db = writableDatabase
        db.delete("tbl_note", "id = $id", null)
    }


    fun getNote(id: Int): Note? {
        val db = readableDatabase
        val cursor = db.query(
            "tbl_note", arrayOf("id", "title", "description", "dateNote", "timeNote"),
            "id=?", arrayOf(id.toString()),
            null, null, null
        )
        cursor?.moveToFirst()
        val note = Note(cursor.getInt(0), cursor.getString(1),
            cursor.getString(2), cursor.getString(3),
            cursor.getString(4))
        cursor.close()
        return note
    }


    fun updateNote(note: Note): Int {
        val db = writableDatabase
        val contentValues = ContentValues()
        contentValues.put("id", note.id)
        contentValues.put("title", note.title)
        contentValues.put("description", note.description)
        contentValues.put("dateNote", note.date)
        contentValues.put("timeNote", note.time)
        return db.update("tbl_note", contentValues, "id=?", arrayOf(note.id.toString()))
    }


}