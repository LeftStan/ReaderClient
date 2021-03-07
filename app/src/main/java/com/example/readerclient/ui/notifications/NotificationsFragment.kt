package com.example.readerclient.ui.notifications

import android.app.AlertDialog
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.readerclient.R
import com.example.readerclient.ReaderClientApplication
import com.example.readerclient.logic.model.Card
import com.example.readerclient.logic.model.CardResponse
import com.example.readerclient.logic.model.LossCardResponse
import com.example.readerclient.logic.model.PasswordResponse
import com.example.readerclient.ui.debtquery.DebtQuery
import com.example.readerclient.ui.login.LoginActivity
import com.example.readerclient.ui.searchbook.BookViewModel
import com.example.readerclient.ui.searchborrow.ReturnBook
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_notifications.*
import org.w3c.dom.Text
import java.lang.Exception
import kotlin.concurrent.thread

class NotificationsFragment() : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        //获取隔壁传来的数据
        val prefs = activity?.getSharedPreferences("data", Context.MODE_PRIVATE)
        val editor = prefs?.edit()
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
//
        val cardLossBtn : Button = root.findViewById(R.id.CardLossBtn)
        val payFindBtn : Button = root.findViewById(R.id.payFineBtn)
        val findHistoryBtn: Button = root.findViewById(R.id.findHistoryBtn)
        val changePassBtn: Button = root.findViewById(R.id.changePassBtn)
        val logOutBtn: Button = root.findViewById(R.id.logOutBtn)
        val textName: TextView = root.findViewById(R.id.textName)
        val textSno: TextView = root.findViewById(R.id.textSno)
        val textMajor: TextView = root.findViewById(R.id.textMajor)
        val textCardId: TextView = root.findViewById(R.id.textCardId)

        val pass: EditText = root.findViewById(R.id.Password)
        //viewModel.loginValidate(account, password)
        //            viewModel.userLiveData.observe(this, Observer{
        val token = prefs?.getString("token", "").toString()
        val sno = prefs?.getString("sno", "").toString()


        fun card_query(){
            notificationsViewModel.cardQuery(token, sno)
            notificationsViewModel.cardLiveData.observe(viewLifecycleOwner, Observer{
                    result ->
                if(result.getOrNull() != null){
                    val response = result.getOrNull() as CardResponse
                    if(response.code == "0"){
                        val res = response.data

                    }else{
                        result.exceptionOrNull()?.printStackTrace()
                    }
                    var cardId = "0"

                    for(i in response.data){
                        if(i.isCancel == "0"){
                            cardId = i.cardId
                            editor?.putString("cardId", cardId)
                            editor?.putString("isLoss", i.isLoss)
                            editor?.putString("isCancel", i.isCancel)
                            editor?.putString("isPause", i.isPause)
                            break
                        }

                    }
                    textCardId.text = cardId
                    if(prefs?.getString("isLoss", "").toString()=="1"){
                        textCardId.append("（借阅证已挂失）")
                        textCardId.setTextColor(Color.parseColor("#AC3232"))
                    }


                    editor?.apply()

                }
            })
        }

        card_query()

        textName.text = prefs?.getString("name", "")
        textSno.text = prefs?.getString("sno", "")
        textMajor.text = prefs?.getString("major", "")


        /*notificationsViewModel.name.observe(viewLifecycleOwner, Observer{
            textName.text = it
        })

        notificationsViewModel.sno.observe(viewLifecycleOwner, Observer{
            //textSno.text = it
            textSno.text = it
        })

        notificationsViewModel.major.observe(viewLifecycleOwner, Observer{
            textMajor.text = it
        })

        notificationsViewModel.cardId.observe(viewLifecycleOwner, Observer{
            textCardId.text = it
        })*/

        cardLossBtn.setOnClickListener(){
            AlertDialog.Builder(activity).apply{
                setTitle("挂失借阅证")
                setMessage("确定挂失借阅证？")
                setPositiveButton("确定"){
                        dialog, which ->
                    val card = Card( prefs?.getString("cardId", "").toString(),  prefs?.getString("isPause", "").toString(),
                        "1",  prefs?.getString("isCancel", "").toString())
                    notificationsViewModel.lossCard(token, card)
                    notificationsViewModel.cardLossLiveData.observe(viewLifecycleOwner, Observer{
                            result ->
                        Log.d("testing", result.toString())
                        if(result.getOrNull() != null){
                            val response = result.getOrNull() as LossCardResponse
                            if(response.code == "0"){
                                Toast.makeText(context, response.msg, Toast.LENGTH_SHORT).show()
                            }else{
                                result.exceptionOrNull()?.printStackTrace()
                            }
                        }
                        card_query()
                    })


                }
                setNegativeButton("取消"){
                        dialog, which ->
                }
                show()
            }
        }
        /*payFindBtn.setOnClickListener(){

        }*/
        findHistoryBtn.setOnClickListener(){
            val intent = Intent(ReaderClientApplication.context, DebtQuery::class.java)
            startActivity(intent)
        }
        changePassBtn.setOnClickListener(){
            val layout = LinearLayout(activity)
            val editText1 = EditText(activity);
            val editText2 = EditText(activity);

            editText1.hint = "请输入新密码"
            editText2.hint = "请再输入新密码"
            editText1.inputType = pass.inputType
            editText2.inputType = pass.inputType
            layout.addView(editText1)
            layout.addView(editText2)
            layout.orientation = LinearLayout.VERTICAL

            AlertDialog.Builder(activity).apply {
                setTitle("修改密码")
                setView(layout)
                setPositiveButton("确定"){ dialog, which ->
                    val password = editText1.text.toString()
                    if(password == editText2.text.toString()){
                        notificationsViewModel.updatePassword(token, password)
                        notificationsViewModel.passwordLiveData.observe(viewLifecycleOwner, Observer{
                                result ->
                            if(result.getOrNull() != null){
                                val response = result.getOrNull() as PasswordResponse
                                if(response.code == "0"){
                                    Toast.makeText(context, response.msg, Toast.LENGTH_SHORT).show()
                                }else{
                                    result.exceptionOrNull()?.printStackTrace()
                                }
                            }
                            loginAgain()
                        })

                    }else{
                        Toast.makeText(context, "两次输入密码不一致，请重试", Toast.LENGTH_SHORT).show()
                    }
                }
                setNegativeButton("取消"){ dialog, which ->
                }
                show()
            }
        }

        logOutBtn.setOnClickListener(){
            logout()
        }
        return root

    }

   /* private fun loss(){
        AlertDialog.Builder(activity).apply{
            setTitle("挂失借阅证")
            setMessage("确定挂失借阅证？")
            setPositiveButton("确定"){
                    dialog, which ->
            }
            setNegativeButton("取消"){
                    dialog, which ->
            }
            if(ReaderClientApplication.context != null)
                show()
        }
    }*/
   private fun loginAgain(){

        AlertDialog.Builder(activity).apply {
            setCancelable(false)
            setTitle("请重新登录")
            setPositiveButton("确定"){ dialog, which ->
                val intent = Intent(ReaderClientApplication.context, LoginActivity::class.java)
                startActivity(intent)
            }
            show()
        }
    }

    private fun logout(){
        AlertDialog.Builder(activity).apply{
            setTitle("注销登录")
            setMessage("确定退出当前账号吗？")
            setPositiveButton("确定"){
                    dialog, which ->
                val intent = Intent(ReaderClientApplication.context, LoginActivity::class.java)
                startActivity(intent)
            }
            setNegativeButton("取消"){
                    dialog, which ->
            }
                show()
        }
    }


}

/*
            var exitTime = 0
            if(System.currentTimeMillis().toInt() - exitTime > 2000) {
                Toast.makeText(ReaderClientApplication.context, "再按一次注销", Toast.LENGTH_SHORT).show()
                exitTime = System.currentTimeMillis().toInt()
            } else {
                val intent = Intent(ReaderClientApplication.context, LoginActivity::class.java)
                startActivity(intent)
            }
*/