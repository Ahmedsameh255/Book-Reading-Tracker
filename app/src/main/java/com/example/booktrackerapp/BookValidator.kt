package com.example.booktrackerapp

object BookValidator {

    fun validateBookInput(title: String, pages: Int, author: String): Boolean {
        return title.isNotBlank() && pages > 0 && author.isNotBlank()
    }

    fun validatePageUpdate(currentPages: Int, pagesToAdd: Int, totalPages: Int): Boolean {
        return pagesToAdd > 0 && currentPages + pagesToAdd <= totalPages
    }

    fun validateBookSearch(query: String): Boolean {
        return query.length >= 2
    }
}