package com.example.booktrackerapp

object BookCalculator {

    fun calculateProgress(currentPage: Int, totalPages: Int): Int {
        if (totalPages <= 0) return 0  // ← FIX: Handle zero total pages
        return ((currentPage.toFloat() / totalPages) * 100).toInt()
    }

    fun calculateRemainingPages(currentPage: Int, totalPages: Int): Int {
        return totalPages - currentPage
    }
}