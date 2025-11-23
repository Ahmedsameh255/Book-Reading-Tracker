package com.example.booktrackerapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Book::class, ReadingSession::class], version = 1)
abstract class BookDatabase : RoomDatabase(){

    abstract fun bookDao() : BookDao
    abstract fun readingSessionDao() : ReadingSessionDao

    companion object{
        @Volatile
        private var INSTANCE: BookDatabase? = null

        fun getInstance(context: Context): BookDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookDatabase::class.java,
                    "book_tracker_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}