package com.example.readerclient.logic.model

data class UserResponse (val code: String, val data: UserData, val msg: String)

data class UserData(val admin: Int, val borrowCount: Int, val major: String, val password: String, val sno: String, val username: String)

data class User(val sno: String, val password: String)