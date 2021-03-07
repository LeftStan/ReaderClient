package com.example.readerclient.logic.network

import com.example.readerclient.logic.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


object ReaderClientNetwork {
    private val BookService = ServiceCreator.create(BookService::class.java)

    suspend fun loginValidate(sno: String, password: String) = BookService.loginValidate(User(sno, password)).await()
    //suspend fun searchUsers(token: String) = BookService.searchUsers(token).await()
    suspend fun searchBooks(token: String, book: Book) = BookService.searchBooks(token, book).await()
    suspend fun searchBorrowHistory(token: String, sno: Sno) = BookService.searchBorrowHistory(token, sno).await()
    suspend fun hotBookQuery(token: String, sno: Sno) = BookService.hotBookQuery(token, sno).await()
    suspend fun debtQuery(token: String, sno: Sno) = BookService.debtQuery(token, sno).await()
    suspend fun cardQuery(token: String, sno: Sno) = BookService.cardQuery(token, sno).await()
    suspend fun lossCard(token: String, card: Card) = BookService.lossCard(token, card).await()
    suspend fun updatePassword(token: String, password: Password) = BookService.updatePassword(token, password).await()
    suspend fun reserveBook(token: String, reserve: Reserve) = BookService.reserveBook(token, reserve).await()
    suspend fun renewBook(token: String, renew: Renew) = BookService.renewBook(token, renew).await()



    private suspend fun <T> Call<T>.await(): T{
        return suspendCoroutine {
                continuation -> enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body = response.body()

                if(body != null) continuation.resume(body)
                else continuation.resumeWithException(
                    RuntimeException("response body is null")
                )
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                continuation.resumeWithException(t)
            }
        })
        }
    }

}