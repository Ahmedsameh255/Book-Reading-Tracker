package com.example.booktrackerapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.booktrackerapp.ui.theme.BookTrackerAppTheme

class BookDetailActivity : ComponentActivity() {
    private val db by lazy { BookDatabase.getInstance(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bookId = intent.getIntExtra("BOOK_ID", 0)

        setContent {
            BookTrackerAppTheme {
                if (bookId > 0) {
                    BookDetailScreen(activity = this, db = db, bookId = bookId)
                } else {
                    Text("Error: Book ID not found")
                }
            }
        }
    }
}

@Composable
fun BookDetailScreen(activity: ComponentActivity, db: BookDatabase, bookId: Int) {
    val context = LocalContext.current
    var book by remember { mutableStateOf<Book?>(null) }
    var pagesToAdd by remember { mutableStateOf("") }
    var showDeleteConfirm by remember { mutableStateOf(false) }

    LaunchedEffect(bookId) {
        val bookWithSessions = withContext(Dispatchers.IO) {
            db.bookDao().getBookById(bookId)
        }
        book = bookWithSessions?.book
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            TextButton(onClick = { activity.finish() }) {
                Text("← Back to Library")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        book?.let { currentBook ->
            Text(
                text = currentBook.title,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "by ${currentBook.author}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Reading Progress",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    val progress = if (currentBook.totalPages > 0) {
                        currentBook.currentPage.toFloat() / currentBook.totalPages
                    } else 0f

                    LinearProgressIndicator(
                        progress = progress,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(12.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "${(progress * 100).toInt()}% (${currentBook.currentPage}/${currentBook.totalPages} pages)",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Update Progress",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = pagesToAdd,
                        onValueChange = {
                            if (it.all { char -> char.isDigit() } || it.isEmpty()) {
                                pagesToAdd = it
                            }
                        },
                        label = { Text("Pages read") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            val pages = pagesToAdd.toIntOrNull()
                            when {
                                pages == null -> {
                                    Toast.makeText(context, "Enter valid number", Toast.LENGTH_SHORT).show()
                                }
                                pages <= 0 -> {
                                    Toast.makeText(context, "Pages must be positive", Toast.LENGTH_SHORT).show()
                                }
                                currentBook.currentPage + pages > currentBook.totalPages -> {
                                    Toast.makeText(context, "Pages exceed book length", Toast.LENGTH_SHORT).show()
                                }
                                else -> {
                                    activity.lifecycleScope.launch(Dispatchers.IO) {
                                        // UPDATE Operation
                                        val updatedBook = currentBook.copy(
                                            currentPage = currentBook.currentPage + pages
                                        )
                                        db.bookDao().updateBook(updatedBook)

                                        val newSession = ReadingSession(
                                            bookId = currentBook.id,
                                            pagesRead = pages,
                                            minutesRead = 0
                                        )
                                        db.readingSessionDao().insertSession(newSession)

                                        withContext(Dispatchers.Main) {
                                            book = updatedBook
                                            pagesToAdd = ""
                                            Toast.makeText(context, "Progress updated!", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = pagesToAdd.isNotBlank()
                    ) {
                        Text("Update Progress")
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            if (showDeleteConfirm) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Delete this book?",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(onClick = { showDeleteConfirm = false }) {
                                Text("Cancel")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = {
                                    activity.lifecycleScope.launch(Dispatchers.IO) {
                                        db.bookDao().deleteBook(currentBook)
                                        withContext(Dispatchers.Main) {
                                            Toast.makeText(context, "Book deleted!", Toast.LENGTH_SHORT).show()
                                            activity.finish()
                                        }
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.error
                                )
                            ) {
                                Text("Delete")
                            }
                        }
                    }
                }
            } else {
                Button(
                    onClick = { showDeleteConfirm = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Delete Book")
                }
            }
        } ?: run {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}