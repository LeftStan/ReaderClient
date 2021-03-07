package com.example.readerclient.logic.model

data class BorrowResponse (val code: String, val data: List<BorrowData>, val msg: String)

data class BorrowData(val bookId: String, val bookTitle: String,  val bookAuthor: String,
                      val bookType: String, val borrowOutdateTime: String, val borrowDate: String, val isReturn: String)

data class TQuery(val token: String, val sno: Sno)

data class Sno(val sno: String)