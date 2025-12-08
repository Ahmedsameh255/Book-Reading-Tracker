package com.example.booktrackerapp

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "reading_sessions",
    foreignKeys = [
        ForeignKey(
            entity = Book::class,
            parentColumns = ["id"],
            childColumns = ["bookId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ReadingSession(
    @PrimaryKey(autoGenerate = true)val id: Int = 0,
    val bookId: Int,
    val minutesRead: Int,
    val pagesRead: Int,
    val timestamp: Long = System.currentTimeMillis()
)