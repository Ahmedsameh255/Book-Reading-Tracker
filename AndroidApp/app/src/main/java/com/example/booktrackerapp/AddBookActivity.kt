package com.example.booktrackerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.booktrackerapp.ui.theme.BookTrackerAppTheme

class AddBookActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookTrackerAppTheme {
                AddBookScreen(
                    onBookSaved = { finish() },
                    onCancel = { finish() }
                )
            }
        }
    }
}

@Composable
fun AddBookScreen(
    onBookSaved: () -> Unit,
    onCancel: () -> Unit
) {
    val context = LocalContext.current
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var totalPages by remember { mutableStateOf("") }

    val db = remember { BookDatabase.getInstance(context) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Add New Book",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Book Title") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = author,
            onValueChange = { author = it },
            label = { Text("Author") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = totalPages,
            onValueChange = {
                if (it.all { char -> char.isDigit() } || it.isEmpty()) {
                    totalPages = it
                }
            },
            label = { Text("Total Pages") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onCancel) {
                Text("Cancel")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    if (title.isNotBlank() && author.isNotBlank() && totalPages.isNotBlank()) {
                        val book = Book(
                            title = title.trim(),
                            author = author.trim(),
                            totalPages = totalPages.toInt(),
                            currentPage = 0
                        )

                        (context as? ComponentActivity)?.lifecycleScope?.launch(Dispatchers.IO) {
                            db.bookDao().insertBook(book)
                            withContext(Dispatchers.Main) {
                                onBookSaved()
                            }
                        }
                    }
                },
                enabled = title.isNotBlank() && author.isNotBlank() && totalPages.isNotBlank()
            ) {
                Text("Save Book")
            }
        }
    }
}