package com.example.booktrackerapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update


@Dao
interface BookDao {
    @Insert
    suspend fun insertBook(book: Book)

    @Query("SELECT * FROM books ORDER BY title ASC")
    suspend fun getAllBooks(): List<Book>

    @Transaction
    @Query("SELECT * FROM books WHERE id = :bookId")
    suspend fun getBookById(bookId: Int): BookWithReadingSessions?

    @Update
    suspend fun updateBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)
}