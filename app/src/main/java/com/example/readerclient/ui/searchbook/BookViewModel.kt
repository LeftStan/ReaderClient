package com.example.readerclient.ui.searchbook

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.readerclient.logic.Repository
import com.example.readerclient.logic.model.*

class BookViewModel: ViewModel() {
    private val searchLiveData = MutableLiveData<TBook>()
    private val reserveLiveData = MutableLiveData<RQuery>()

    val bookList = ArrayList<BookData>()
    //使用switchMap方法观察
    val bookLiveData = Transformations.switchMap(searchLiveData){
            tbook -> Repository.searchBooks(tbook.token, tbook.book)
    }
    val reserveResLiveData = Transformations.switchMap(reserveLiveData){
            RQuery -> Repository.reserveBook(RQuery.token, RQuery.reserve)
    }

    //调用时只是传值
    fun searchBooks(token: String, book: Book){
        searchLiveData.value = TBook(token, book)
    }

    fun reserveBook(token: String, reserve: Reserve){
        reserveLiveData.value = RQuery(token, reserve)
    }
/*
    fun getBooks(): LiveData<Result<List<Any>>> {
        return bookLiveData
    }*/

}