package com.example.readerclient.ui.reservation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.readerclient.logic.Repository
import com.example.readerclient.logic.model.*
import java.io.Serializable

class BookViewModel: ViewModel() {
    private val searchLiveData = MutableLiveData<TBook>()

    val bookList = ArrayList<BookData>()
    //使用switchMap方法观察
    val bookLiveData = Transformations.switchMap(searchLiveData){
            tbook -> Repository.searchBooks(tbook.token, tbook.book)
    }

    //调用时只是传值
    fun searchBooks(token: String, book: Book){
        searchLiveData.value = TBook(token, book)
    }

}