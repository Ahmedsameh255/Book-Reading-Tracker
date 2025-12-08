import 'package:flutter/material.dart';

void main() {
  runApp(SimpleBookStatsApp());
}

class SimpleBookStatsApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Book Stats',
      home: SimpleBookStatsScreen(),
    );
  }
}

class SimpleBookStatsScreen extends StatefulWidget {
  @override
  _SimpleBookStatsScreenState createState() => _SimpleBookStatsScreenState();
}

class _SimpleBookStatsScreenState extends State<SimpleBookStatsScreen> {
  int bookCount = 0;
  
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Book Stats - Flutter'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text(
              'Book Reading Statistics',
              style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
            ),
            SizedBox(height: 30),
            Card(
              child: Padding(
                padding: EdgeInsets.all(20),
                child: Column(
                  children: [
                    Icon(Icons.book, size: 60, color: Colors.blue),
                    SizedBox(height: 20),
                    Text('Books Read:', style: TextStyle(fontSize: 18)),
                    Text('$bookCount', style: TextStyle(fontSize: 40, fontWeight: FontWeight.bold)),
                  ],
                ),
              ),
            ),
            SizedBox(height: 30),
            ElevatedButton(
              onPressed: () {
                setState(() {
                  bookCount++;
                });
              },
              child: Text('Add Book'),
            ),
            SizedBox(height: 20),
            Text(
              'Launched from Android via Intent!',
              style: TextStyle(color: Colors.grey, fontStyle: FontStyle.italic),
            ),
          ],
        ),
      ),
    );
  }
}