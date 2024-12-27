package com.example.dz16bottomnavigationview.domain.notes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(tableName = "notes_table")
data class Note(
    @ColumnInfo("title") val title: String,
    @ColumnInfo("content") var content: String,
    @ColumnInfo("timestamp") var timestamp: String = getDate(),
    @ColumnInfo("isDone") var isDone: Boolean = false){

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    companion object {
        private val dateFormatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMAN)
        private fun getDate(): String = dateFormatter.format(Date())
    }

}