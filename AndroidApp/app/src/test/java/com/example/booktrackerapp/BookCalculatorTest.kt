package com.example.booktrackerapp

import org.junit.Assert.*
import org.junit.Test

class BookCalculatorTest {
    @Test
    fun testCalculateProgress() {
        val result = BookCalculator.calculateProgress(50, 200)
        assertEquals(25, result)
    }

    @Test
    fun testCalculateRemainingPages() {
        val result = BookCalculator.calculateRemainingPages(50, 200)
        assertEquals(150, result)
    }

    @Test
    fun testCalculateProgressWithZeroTotalPages() {
        val result = BookCalculator.calculateProgress(10, 0)
        assertEquals(0, result)
    }

    @Test
    fun testCalculateProgressWithFullCompletion() {
        val result = BookCalculator.calculateProgress(300, 300)
        assertEquals(100, result)
    }
}