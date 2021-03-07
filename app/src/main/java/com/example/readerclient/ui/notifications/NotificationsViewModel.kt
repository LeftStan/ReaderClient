package com.example.readerclient.ui.notifications

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.readerclient.logic.Repository
import com.example.readerclient.logic.model.*

class NotificationsViewModel : ViewModel() {

   /* private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }*/
    private val searchLiveData = MutableLiveData<TQuery>()
    private val updateLiveData = MutableLiveData<PQuery>()
    private val lossLiveData = MutableLiveData<LossQuery>()

    val resultList = ArrayList<CardData>()
    //使用switchMap方法观察
    val cardLiveData = Transformations.switchMap(searchLiveData){
            TQuery -> Repository.cardQuery(TQuery.token, TQuery.sno)
    }

    val passwordLiveData = Transformations.switchMap(updateLiveData){
            PQuery -> Repository.updatePassword(PQuery.token, PQuery.password)
    }

    val cardLossLiveData = Transformations.switchMap(lossLiveData){
            LossQuery -> Repository.lossCard(LossQuery.token, LossQuery.card)
    }


    //调用时只是传值
    fun cardQuery(token: String, sno: String){
        searchLiveData.value = TQuery(token, Sno(sno))
    }

    fun updatePassword(token: String, password: String){
        updateLiveData.value = PQuery(token, Password(password))
    }

    fun lossCard(token: String, card: Card){
        lossLiveData.value = LossQuery(token, card)
    }

    private val _name: MutableLiveData<String> = MutableLiveData<String>().apply{
        value = "Doom fist"
    }
    /*private val _sno: MutableLiveData<String> = MutableLiveData<String>().apply{
        value = "18251104199"
    }
    private val _major: MutableLiveData<String> = MutableLiveData<String>().apply{
        value = "电子竞技"
    }
    private val _cardId: MutableLiveData<String> = MutableLiveData<String>().apply{
        value = "4325110123"
    }*/

    //val text: LiveData<String> = _text
    val name: LiveData<String> = _name
    /*val sno: LiveData<String> = _sno
    val major: LiveData<String> = _major
    val cardId: LiveData<String> = _cardId*/





}