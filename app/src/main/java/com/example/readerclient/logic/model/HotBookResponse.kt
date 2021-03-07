package com.example.readerclient.logic.model

data class HotBookResponse (val code: String, val data: List<HotBookData>, val msg: String)

data class HotBookData(val bookTitle: String, val borrowCount: String)