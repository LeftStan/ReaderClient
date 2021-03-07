package com.example.readerclient.logic.model

data class LossCardResponse (val code: String, val data: List<String>, val msg: String)

data class Card(val cardId: String, val isPause: String, val isLoss: String, val isCancel: String)

data class LossQuery(val token: String, val card: Card)