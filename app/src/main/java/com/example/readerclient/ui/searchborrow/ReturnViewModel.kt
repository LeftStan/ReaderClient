package com.example.readerclient.ui.searchborrow

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.readerclient.logic.Repository
import com.example.readerclient.logic.model.BorrowData
import com.example.readerclient.logic.model.Sno
import com.example.readerclient.logic.model.TQuery

class ReturnViewModel: ViewModel() {
    private val searchLiveData = MutableLiveData<TQuery>()

    val returnList = ArrayList<BorrowData>()
    //使用switchMap方法观察
    val returnLiveData = Transformations.switchMap(searchLiveData){
            TQuery -> Repository.searchBorrowHistory(TQuery.token, TQuery.sno)
    }

    //调用时只是传值
    fun searchBorrowHistory(token: String, sno: String){
        searchLiveData.value = TQuery(token, Sno(sno))
    }
}