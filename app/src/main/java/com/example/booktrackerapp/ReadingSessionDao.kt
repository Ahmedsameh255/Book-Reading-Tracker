package com.example.booktrackerapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface ReadingSessionDao {
    @Insert
    suspend fun insertSession(session: ReadingSession)

    @Query("SELECT * FROM reading_sessions WHERE bookId = :bookId ORDER BY timestamp DESC")
    suspend fun getSessionForBook(bookId: Int): List<ReadingSession>

    @Delete
    suspend fun deleteSession(session: ReadingSession)
}