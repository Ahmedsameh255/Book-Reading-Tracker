package com.example.booktrackerapp

import org.junit.Test
import org.junit.Assert.*

class BookValidatorTest {

    @Test
    fun testValidateBookInputWithValidData() {
        val result = BookValidator.validateBookInput("Android Book", 200, "John Doe")
        assertTrue(result)
    }

    @Test
    fun testValidateBookInputWithEmptyTitle() {
        val result = BookValidator.validateBookInput("", 200, "John Doe")
        assertFalse(result)
    }

    @Test
    fun testValidateBookInputWithZeroPages() {
        val result = BookValidator.validateBookInput("Android Book", 0, "John Doe")
        assertFalse(result)
    }

    @Test
    fun testValidateBookInputWithEmptyAuthor() {
        val result = BookValidator.validateBookInput("Android Book", 200, "")
        assertFalse(result)
    }

    @Test
    fun testValidateBookInputWithNegativePages() {
        val result = BookValidator.validateBookInput("Android Book", -50, "John Doe")
        assertFalse(result)
    }

    @Test
    fun testValidatePageUpdateWithValidUpdate() {
        val result = BookValidator.validatePageUpdate(50, 30, 100)
        assertTrue(result)
    }

    @Test
    fun testValidatePageUpdateWithExceedingPages() {
        val result = BookValidator.validatePageUpdate(90, 20, 100)
        assertFalse(result)
    }

    @Test
    fun testValidatePageUpdateWithNegativePages() {
        val result = BookValidator.validatePageUpdate(50, -10, 100)
        assertFalse(result)
    }

    @Test
    fun testValidatePageUpdateWithZeroPages() {
        val result = BookValidator.validatePageUpdate(50, 0, 100)
        assertFalse(result)
    }

    @Test
    fun testValidateBookSearchWithValidQuery() {
        val result = BookValidator.validateBookSearch("Android")
        assertTrue(result)
    }

    @Test
    fun testValidateBookSearchWithShortQuery() {
        val result = BookValidator.validateBookSearch("A")
        assertFalse(result)
    }

    @Test
    fun testValidateBookSearchWithEmptyQuery() {
        val result = BookValidator.validateBookSearch("")
        assertFalse(result)
    }
}