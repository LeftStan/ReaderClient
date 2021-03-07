package com.example.readerclient.logic.model

data class BookResponse (val code: String, val data: List<BookData>, val msg: String)

data class BookData(val belongSno: String, val bookAuthor: String, val bookId: String, val bookIsbn: String,
                    val bookPress: String, val bookTitle: String, val bookType: String, val borrowAble: String,
                    val borrowerID: String, val isBorrowed: String, val isOrdered: String, val ordererID: String,
                    val submissionData: String, val username: String)

data class Book( val bookAuthor: String, val bookId: String, val bookIsbn: String,
                val bookPress: String, val bookTitle: String, val bookType: String)

data class TBook(val token: String, val book: Book)
