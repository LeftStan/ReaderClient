package com.example.readerclient.ui.searchborrow

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.readerclient.logic.Repository
import com.example.readerclient.logic.model.*

class BorrowHistoryViewModel: ViewModel() {
    private val searchLiveData = MutableLiveData<TQuery>()
    private val renewLiveData = MutableLiveData<ReQuery>()

    val borrowList = ArrayList<BorrowData>()
    //使用switchMap方法观察
    val borrowLiveData = Transformations.switchMap(searchLiveData){
            TQuery -> Repository.searchBorrowHistory(TQuery.token, TQuery.sno)
    }
    val renewBookLiveData = Transformations.switchMap(renewLiveData){
            ReQuery -> Repository.renewBook(ReQuery.token, ReQuery.renew)
    }

    //调用时只是传值
    fun searchBorrowHistory(token: String, sno: String){
        searchLiveData.value = TQuery(token, Sno(sno))
    }

    fun renewBook(token: String, renew: Renew){
        renewLiveData.value = ReQuery(token, renew)
    }

}