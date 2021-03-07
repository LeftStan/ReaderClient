package com.example.readerclient.logic.model

data class PasswordResponse(val code: String, val data: List<String>, val msg: String)

data class PQuery(val token: String, val password: Password)

data class Password(val password: String)

