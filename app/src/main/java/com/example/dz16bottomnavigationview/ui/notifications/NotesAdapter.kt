package com.example.dz16bottomnavigationview.ui.notifications

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dz16bottomnavigationview.R
import com.example.dz16bottomnavigationview.domain.notes.Note

class NotesAdapter(private val context: Context, private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>(){

    val notes = mutableListOf<Note>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Note>) {
        notes.clear()
        notes.addAll(newList)
        notifyDataSetChanged()
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTV : TextView = itemView.findViewById(R.id.title_tv)
        private val contentTV: TextView = itemView.findViewById(R.id.content_tv)
        private val timestampTV: TextView = itemView.findViewById(R.id.timestamp_tv)

        fun bind(note: Note) {
            titleTV.text = note.title
            contentTV.text = note.content
            timestampTV.text = note.timestamp
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val viewHolder = NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false))
        viewHolder.itemView.setOnClickListener {
            onItemClickListener.onClick(notes[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = notes[position]
        holder.bind(currentNote)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    interface OnItemClickListener {
        fun onClick(item: Note)
    }

}