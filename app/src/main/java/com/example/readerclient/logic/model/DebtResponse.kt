package com.example.readerclient.logic.model

data class DebtResponse (val code: String, val data: List<DebtData>, val msg: String)

data class DebtData(val bookId: String, val bookTitle: String,  val sno: String,
                      val isDischarge: String, val username: String, val debtMoney: String, val debtReason: String)
