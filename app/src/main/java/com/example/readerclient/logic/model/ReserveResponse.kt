package com.example.readerclient.logic.model

data class ReserveResponse (val code: String, val data: List<String>, val msg: String)

data class Reserve(val bookId: String, val sno: String)

data class RQuery(val token: String, val reserve: Reserve)

