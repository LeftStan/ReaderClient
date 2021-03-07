package com.example.readerclient.logic.model

data class CardResponse(val code: String, val data: List<CardData>, val msg: String)

data class CardData(val cardId: String, val belongSno: String, val isPause: String, val isLoss: String, val isCancel: String)

//data class UserN(val sno: String, val username: String)