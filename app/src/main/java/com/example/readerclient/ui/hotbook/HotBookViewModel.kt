package com.example.readerclient.ui.hotbook

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.readerclient.logic.Repository
import com.example.readerclient.logic.model.BorrowData
import com.example.readerclient.logic.model.HotBookData
import com.example.readerclient.logic.model.Sno
import com.example.readerclient.logic.model.TQuery

class HotBookViewModel: ViewModel() {
    private val searchLiveData = MutableLiveData<TQuery>()

    val resultList = ArrayList<HotBookData>()
    //使用switchMap方法观察
    val hotBookLiveData = Transformations.switchMap(searchLiveData){
            TQuery -> Repository.hotBookQuery(TQuery.token, TQuery.sno)
    }

    //调用时只是传值
    fun hotBookQuery(token: String, sno: String){
        searchLiveData.value = TQuery(token, Sno(sno))
    }
}