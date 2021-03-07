package com.example.readerclient.logic

import android.util.Log
import androidx.lifecycle.liveData
import com.example.readerclient.logic.model.*
import com.example.readerclient.logic.network.ReaderClientNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.RuntimeException

object Repository {
    fun loginValidate(sno: String, password: String) = liveData(Dispatchers.IO){
        //var userResponse = UserResponse("", UserData(0, 0 ,"", "", "", ""), "")
        val result = try{
            val userResponse = ReaderClientNetwork.loginValidate(sno, password)
            if(userResponse.code == "0") {
                Result.success(userResponse)
            }else{
                Result.failure(RuntimeException("Response status is ${userResponse.code}, msg: ${userResponse.msg}"))
            }
        }catch(e: Exception){
            Result.failure<List<String>>(e)
        }
        //Log.d("userResponse code is:", result.toString())
        //Log.d("userResponse code is:", userResponse.toString())
        emit(result)
    }

    fun searchBooks(token: String, book: Book) = liveData(Dispatchers.IO){
        val result = try{
            val bookResponse = ReaderClientNetwork.searchBooks(token, book)
            if(bookResponse.code == "0") {
                val books = bookResponse
                Result.success(books)
            }else{
                Result.failure(RuntimeException("Response id is ${bookResponse.code}, ${bookResponse.msg}"))
            }
        }catch(e: Exception){
            Result.failure<List<String>>(e)
        }
        emit(result)
    }

    fun searchBorrowHistory(token: String, sno: Sno) = liveData(Dispatchers.IO){
        val result = try{
            val borrowResponse = ReaderClientNetwork.searchBorrowHistory(token, sno)
            if(borrowResponse.code == "0") {
                Result.success(borrowResponse)
            }else{
                Result.failure(RuntimeException("Response id is ${borrowResponse.code}, ${borrowResponse.msg}"))
            }
        }catch(e: Exception){
            Result.failure<List<String>>(e)
        }
        emit(result)
    }


    fun hotBookQuery(token: String, sno: Sno) = liveData(Dispatchers.IO){
        val result = try{
            val response = ReaderClientNetwork.hotBookQuery(token, sno)
            if(response.code == "0") {
                Result.success(response)
            }else{
                Result.failure(RuntimeException("Response id is ${response.code}, ${response.msg}"))
            }
        }catch(e: Exception){
            Result.failure<List<String>>(e)
        }
        emit(result)
    }

    fun debtQuery(token: String, sno: Sno) = liveData(Dispatchers.IO){
        val result = try{
            val response = ReaderClientNetwork.debtQuery(token, sno)
            if(response.code == "0") {
                Result.success(response)
            }else{
                Result.failure(RuntimeException("Response id is ${response.code}, ${response.msg}"))
            }
        }catch(e: Exception){
            Result.failure<List<String>>(e)
        }
        emit(result)
    }

    fun cardQuery(token: String, sno: Sno) = liveData(Dispatchers.IO){
        val result = try{
            val response = ReaderClientNetwork.cardQuery(token, sno)
            if(response.code == "0") {
                Result.success(response)
            }else{
                Result.failure(RuntimeException("Response id is ${response.code}, ${response.msg}"))
            }
        }catch(e: Exception){
            Result.failure<List<String>>(e)
        }
        emit(result)
    }

    fun lossCard(token: String, card: Card) = liveData(Dispatchers.IO){
        val result = try{
            val response = ReaderClientNetwork.lossCard(token, card)
            if(response.code == "0") {
                Result.success(response)
            }else{
                Result.failure(RuntimeException("Response id is ${response.code}, ${response.msg}"))
            }
        }catch(e: Exception){
            Result.failure<List<String>>(e)
        }
        emit(result)
    }

    fun updatePassword(token: String, password: Password) = liveData(Dispatchers.IO){
        val result = try{
            val response = ReaderClientNetwork.updatePassword(token, password)
            if(response.code == "0") {
                Result.success(response)
            }else{
                Result.failure(RuntimeException("Response id is ${response.code}, ${response.msg}"))
            }
        }catch(e: Exception){
            Result.failure<List<String>>(e)
        }
        emit(result)
    }

    fun reserveBook(token: String, reserve: Reserve) = liveData(Dispatchers.IO){
        val result = try{
            val response = ReaderClientNetwork.reserveBook(token, reserve)
            if(response.code == "0") {
                Result.success(response)
            }else{
                Result.failure(RuntimeException("Response id is ${response.code}, ${response.msg}"))
            }
        }catch(e: Exception){
            Result.failure<List<String>>(e)
        }
        emit(result)
    }

    fun renewBook(token: String, renew: Renew) = liveData(Dispatchers.IO){
        val result = try{
            val response = ReaderClientNetwork.renewBook(token, renew)
            if(response.code != null) {
                Result.success(response)
            }else{
                Result.failure(RuntimeException("Response id is ${response.code}, ${response.msg}"))
            }
        }catch(e: Exception){
            Result.failure<List<String>>(e)
        }
        emit(result)
    }
    /*fun searchCard(token: String, user: UserN) = liveData(Dispatchers.IO){
        val result = try{
            val cardResponse = ReaderClientNetwork.searchCard(token, user)
            if(cardResponse.code == "0") {
                val card = cardResponse
                Result.success(card)
            }else{
                Result.failure(RuntimeException("Response id is ${cardResponse.code}, ${cardResponse.msg}"))
            }
        }catch(e: Exception){
            Result.failure<List<String>>(e)
        }
        emit(result)
    }*/

}