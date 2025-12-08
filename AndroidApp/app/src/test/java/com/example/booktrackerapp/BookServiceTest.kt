package com.example.booktrackerapp

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.junit.Assert.*  // ← ADD THIS IMPORT

class BookServiceTest {

    private lateinit var mockRepository: BookRepository
    private lateinit var bookService: BookService

    @Before
    fun setup() {
        // Initialize mocks before each test - Like Lab 07 Page 27
        mockRepository = Mockito.mock(BookRepository::class.java)
        bookService = BookService(bookRepository = mockRepository)
    }

    @Test
    fun testGetBookInfoWhenBookExists() {
        // when().thenReturn() - Like Lab 07 Page 30
        Mockito.`when`(mockRepository.isBookExists("Android Programming"))
            .thenReturn(true)

        val result = bookService.getBookInfo("Android Programming")

        assertEquals("Book 'Android Programming' is available", result)

        // verify() - Like Lab 07 Page 30
        verify(mockRepository).isBookExists("Android Programming")
    }

    @Test
    fun testGetBookInfoWhenBookNotExists() {
        Mockito.`when`(mockRepository.isBookExists("Unknown Book"))
            .thenReturn(false)

        val result = bookService.getBookInfo("Unknown Book")

        assertEquals("Book 'Unknown Book' not found", result)
        verify(mockRepository).isBookExists("Unknown Book")
    }

    @Test
    fun testGetLibraryStats() {
        Mockito.`when`(mockRepository.getBookCount())
            .thenReturn(20)

        val result = bookService.getLibraryStats()

        assertEquals("Total books: 20", result)
        verify(mockRepository).getBookCount()
    }

    @Test
    fun testGetBookDetailsWhenBookExists() {
        Mockito.`when`(mockRepository.isBookExists("Kotlin Guide"))
            .thenReturn(true)
        Mockito.`when`(mockRepository.getBookPages("Kotlin Guide"))
            .thenReturn(300)

        val result = bookService.getBookDetails("Kotlin Guide")

        assertEquals("Book 'Kotlin Guide' has 300 pages", result)
        verify(mockRepository).isBookExists("Kotlin Guide")
        verify(mockRepository).getBookPages("Kotlin Guide")
    }

    @Test
    fun testGetBookDetailsWhenBookNotExists() {
        Mockito.`when`(mockRepository.isBookExists("Unknown Book"))
            .thenReturn(false)

        val result = bookService.getBookDetails("Unknown Book")

        assertEquals("Book 'Unknown Book' not found", result)
        verify(mockRepository).isBookExists("Unknown Book")
    }
}