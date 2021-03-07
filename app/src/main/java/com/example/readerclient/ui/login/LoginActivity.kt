package com.example.readerclient.ui.login

import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.readerclient.MainActivity
import com.example.readerclient.R
import com.example.readerclient.logic.model.UserResponse
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val viewModel by lazy{ ViewModelProviders.of(this).get(UserViewModel::class.java)}
        val prefs = getSharedPreferences("data", Context.MODE_PRIVATE)
        var today = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        var lastday = prefs.getInt("lastday", today)
        var attempt = prefs.getInt("attempt", 0)
        accountEdit.setText(prefs.getString("account", ""))
        //Toast.makeText(this, "${today}   ${lastday}", Toast.LENGTH_SHORT).show()
        //判断今日密码输入次数
        if(today == lastday)
        {
            if( attempt >= 100) {
                Toast.makeText(this, "已达今天最大尝试次数，请再重试", Toast.LENGTH_SHORT).show()
                this.finish()
            }
        }else{
            attempt = 0
            lastday = today
        }
        var flag = 1
        //登录按钮

        loginBtn.setOnClickListener(){

            val account = accountEdit.text.toString()
            val password = passwordEdit.text.toString()
            val editor = prefs.edit()
            val intent = Intent(this, MainActivity::class.java)
            var userResponse : UserResponse

            editor.putInt("lastday", lastday)
            viewModel.loginValidate(account, password)
            viewModel.userLiveData.observe(this, Observer{
                    result ->
                if(result.getOrNull() != null){
                    userResponse = result.getOrNull() as UserResponse
                    Log.d("testing",result.toString())
                    Log.d("testing",userResponse.toString())
                    editor.putString("account", account)
                    if(userResponse.code.toInt() == 0){
                        editor.putString("token", userResponse.msg)
                        val user = userResponse.data
                        if(password == user.password) {
                            editor.putString("name", user.username)
                            editor.putString("sno", user.sno)
                            editor.putString("major", user.major)
                            editor.apply()
                            this.finish()
                            startActivity(intent)
                        }
                    }
                    else{
                        Toast.makeText(this, userResponse.msg, Toast.LENGTH_SHORT).show()
                    }
                }
                /*else if(flag == 1){
                    attempt += 1
                    editor.putInt("attempt", attempt)
                    if (attempt >= 100) {
                        editor.apply()
                        Toast.makeText(this, "已达今天最大尝试次数，请再重试", Toast.LENGTH_SHORT).show()
                        this.finish()
                    } else {
                        Toast.makeText(
                            this,
                            "错误的账号或密码，请重试，剩余尝试次数${3 - attempt}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }*/

            })

        }
    }


}

