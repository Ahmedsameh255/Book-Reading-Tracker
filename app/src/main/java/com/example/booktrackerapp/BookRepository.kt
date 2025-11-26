package com.example.booktrackerapp

class BookRepository {

    fun isBookExists(bookTitle: String): Boolean {
        val existingBooks = listOf("Android Programming", "Kotlin Guide", "Java Basics")
        return bookTitle in existingBooks
    }

    fun getBookCount(): Int {
        return 15
    }

    fun getBookPages(bookTitle: String): Int {
        return when (bookTitle) {
            "Android Programming" -> 500
            "Kotlin Guide" -> 300
            "Java Basics" -> 400
            else -> 0
        }
    }
}