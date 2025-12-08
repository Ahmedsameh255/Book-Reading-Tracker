package com.example.booktrackerapp

import androidx.room.Embedded
import androidx.room.Relation

data class BookWithReadingSessions(
    @Embedded val book: Book,
    @Relation(
        parentColumn = "id",
        entityColumn = "bookId"
    )
    val sessions: List<ReadingSession>
)