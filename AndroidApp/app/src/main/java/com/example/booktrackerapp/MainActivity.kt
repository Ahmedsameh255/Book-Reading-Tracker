package com.example.booktrackerapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.booktrackerapp.ui.theme.BookTrackerAppTheme

class MainActivity : ComponentActivity() {
    private val refreshLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        refreshBooks()
    }

    private var refreshBooks: () -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookTrackerAppTheme {
                MainBookScreen(refreshLauncher) { refreshCallback ->
                    refreshBooks = refreshCallback
                }
            }
        }
    }
}

@Composable
fun MainBookScreen(
    refreshLauncher: androidx.activity.result.ActivityResultLauncher<Intent>,
    onRefreshCallback: (() -> Unit) -> Unit
) {
    val context = LocalContext.current
    var books by remember { mutableStateOf<List<Book>>(emptyList()) }
    var refreshKey by remember { mutableStateOf(0) }

    val db = remember { BookDatabase.getInstance(context) }

    LaunchedEffect(refreshKey) {
        books = withContext(Dispatchers.IO) {
            db.bookDao().getAllBooks()
        }
    }

    LaunchedEffect(Unit) {
        onRefreshCallback {
            refreshKey++
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "My Reading Library",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (books.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "No books in your library",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    "Add your first book using the button below!",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(books) { book ->
                    BookItem(
                        book = book,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable {
                                val intent = Intent(context, BookDetailActivity::class.java).apply {
                                    putExtra("BOOK_ID", book.id)
                                }
                                refreshLauncher.launch(intent)
                            }
                    )
                }
            }
        }

        Button(
            onClick = {
                val intent = Intent(context, AddBookActivity::class.java)
                refreshLauncher.launch(intent)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Add Book",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add New Book")
            }
        }
    }
}

@Composable
fun BookItem(book: Book, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = book.title,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "by ${book.author}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            val progress = if (book.totalPages > 0) {
                book.currentPage.toFloat() / book.totalPages
            } else 0f

            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${book.currentPage} / ${book.totalPages} pages",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}