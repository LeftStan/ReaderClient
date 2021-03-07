package com.example.readerclient.ui.debtquery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.readerclient.logic.Repository
import com.example.readerclient.logic.model.*

class DebtViewModel: ViewModel() {
    private val searchLiveData = MutableLiveData<TQuery>()

    val resultList = ArrayList<DebtData>()
    //使用switchMap方法观察
    val debtLiveData = Transformations.switchMap(searchLiveData){
            TQuery -> Repository.debtQuery(TQuery.token, TQuery.sno)
    }

    //调用时只是传值
    fun debtQuery(token: String, sno: String){
        searchLiveData.value = TQuery(token, Sno(sno))
    }
}