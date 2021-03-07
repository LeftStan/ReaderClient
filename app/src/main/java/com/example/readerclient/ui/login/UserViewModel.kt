package com.example.readerclient.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.readerclient.logic.Repository
import com.example.readerclient.logic.model.User

class UserViewModel: ViewModel() {
    private val loginLiveData = MutableLiveData<User>()

    val userLiveData = Transformations.switchMap(loginLiveData){
        user -> Repository.loginValidate(user.sno, user.password)
    }


    fun loginValidate(sno: String, password: String){
        loginLiveData.value = User(sno, password)
    }

}