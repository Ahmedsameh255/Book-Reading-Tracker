package com.example.booktrackerapp

class BookService(private val bookRepository: BookRepository) {

    fun getBookInfo(bookTitle: String): String {
        val exists = bookRepository.isBookExists(bookTitle)
        return if (exists) {
            "Book '$bookTitle' is available"
        } else {
            "Book '$bookTitle' not found"
        }
    }

    fun getLibraryStats(): String {
        val count = bookRepository.getBookCount()
        return "Total books: $count"
    }

    fun getBookDetails(bookTitle: String): String {
        val exists = bookRepository.isBookExists(bookTitle)
        val pages = bookRepository.getBookPages(bookTitle)
        return if (exists) {
            "Book '$bookTitle' has $pages pages"
        } else {
            "Book '$bookTitle' not found"
        }
    }
}