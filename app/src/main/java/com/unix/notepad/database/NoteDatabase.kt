package com.unix.notepad.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

open class NoteDatabase(
    context: Context?,
) : SQLiteOpenHelper(context, "Note.db", null, 1) {


    override fun onCreate(db: SQLiteDatabase?) {
       // TODO("Not yet implemented")

        val query =
            "CREATE TABLE tbl_note(id INTEGER PRIMARY KEY AUTOINCREMENT ,title varchar(100)," + "description Text, dateNote varchar(50), timeNote varchar(50) )"

        db!!.execSQL(query)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }


}