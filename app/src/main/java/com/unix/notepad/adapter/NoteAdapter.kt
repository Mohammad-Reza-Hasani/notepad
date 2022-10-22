package com.unix.notepad.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.unix.notepad.R
import com.unix.notepad.activities.AddNoteActivity
import com.unix.notepad.database.NoteDBAdapter
import com.unix.notepad.model.Note

class NoteAdapter(activity: Activity, noteList: List<Note>) :
    RecyclerView.Adapter<NoteAdapter.NoteVH>() {

    private var noteDbAdapter = NoteDBAdapter(activity)
    var noteList: ArrayList<Note> = noteList as ArrayList<Note>
    private var inflater: LayoutInflater = LayoutInflater.from(activity)
    var activity: Activity = activity


    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteVH {
        // TODO("Not yet implemented")

        val view = inflater.inflate(R.layout.note_row, null)
        return NoteVH(view)
    }

    override fun onBindViewHolder(holder: NoteVH, position: Int) {
        //   TODO("Not yet implemented")
        var note: Note = noteList[position]
        holder.txt_title.text = note.title
        holder.txt_description.text = note.description

        holder.img_delete.setOnClickListener(View.OnClickListener {

            note.id?.let { it1 -> noteDbAdapter.deleteNote(it1) }
            Log.i("NoteAdapter", "onBindViewHolder: $position")
            noteList.remove(note)
            notifyItemRemoved(position)


        })


        holder.img_share.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "Text/Plain"
            intent.putExtra(Intent.EXTRA_SUBJECT, note.title)
            intent.putExtra(Intent.EXTRA_TEXT, note.description)
            activity.startActivity(intent)
        })


        holder.img_edit.setOnClickListener(View.OnClickListener {

            val intent = Intent(activity, AddNoteActivity::class.java)
            intent.putExtra("id", note.id)
            intent.putExtra("title", note.title)
            intent.putExtra("description", note.description)
            intent.putExtra("dateNote", note.date)
            intent.putExtra("timeNote", note.time)
            intent.putExtra("isUpdate", true)
            activity.startActivity(intent)
        })

    }

    override fun getItemCount(): Int {
        // TODO("Not yet implemented")
        return noteList.size
    }


    class NoteVH(itemView: View) : RecyclerView.ViewHolder(itemView) {


        var txt_title: AppCompatTextView = itemView.findViewById(R.id.txt_title)
        var txt_description: AppCompatTextView = itemView.findViewById(R.id.txt_description)
        var img_delete: AppCompatImageView = itemView.findViewById(R.id.img_delete)
        var img_edit: AppCompatImageView = itemView.findViewById(R.id.img_edit)
        var img_share: AppCompatImageView = itemView.findViewById(R.id.img_share)

    }


}