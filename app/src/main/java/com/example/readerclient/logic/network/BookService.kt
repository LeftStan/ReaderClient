package com.example.readerclient.logic.network


import com.example.readerclient.logic.model.*
import retrofit2.Call
import retrofit2.http.*

interface BookService {
    @POST("user/login")
    fun loginValidate(@Body user: User ): Call<UserResponse>

    @POST("user/listjson")
    fun searchUsers(@Header("Authorization") token: String): Call<SearchResponse>

    @POST("book/bookQuery")
    fun searchBooks(@Header("Authorization") token: String, @Body book: Book):Call<BookResponse>

    @POST("query/historyQuery")
    fun searchBorrowHistory(@Header("Authorization") token: String, @Body sno: Sno):Call<BorrowResponse>

    @POST("query/hotBookQuery")
    fun hotBookQuery(@Header("Authorization") token: String, @Body sno: Sno):Call<HotBookResponse>

    @POST("query/debtQuery")
    fun debtQuery(@Header("Authorization") token: String, @Body sno: Sno):Call<DebtResponse>

    @POST("query/cardQuery")
    fun cardQuery(@Header("Authorization") token: String, @Body sno: Sno):Call<CardResponse>

    @POST("user/updatePassword")
    fun updatePassword(@Header("Authorization") token: String, @Body password: Password):Call<PasswordResponse>

    @POST("user/lossBorrowCard")
    fun lossCard(@Header("Authorization") token: String, @Body card: Card):Call<LossCardResponse>

    @POST("book/bookReserve")
    fun reserveBook(@Header("Authorization") token: String, @Body reserve: Reserve):Call<ReserveResponse>

    @POST("book/bookRenew")
    fun renewBook(@Header("Authorization") token: String, @Body renew: Renew):Call<RenewResponse>
}