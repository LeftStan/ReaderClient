package com.example.readerclient.logic.model

data class RenewResponse (val code: String, val data: List<String>, val msg: String)

data class Renew(val bookId: String, val sno: String)

data class ReQuery(val token: String, val renew: Renew)