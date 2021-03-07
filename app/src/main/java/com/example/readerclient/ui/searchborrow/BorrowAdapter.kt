package com.example.readerclient.ui.searchborrow

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.readerclient.R
import com.example.readerclient.ReaderClientApplication
import com.example.readerclient.logic.model.*
import com.example.readerclient.ui.searchbook.BookViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class BorrowAdapter(private val fragment: Fragment, private val borrowList: List<BorrowData>):
    RecyclerView.Adapter<BorrowAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bookName: TextView = view.findViewById(R.id.bookNameN)
        val bookAuthor: TextView = view.findViewById(R.id.bookAuthorN)
        val bookID: TextView = view.findViewById(R.id.bookIDN)
        val bookType : TextView = view.findViewById(R.id.bookTypeN)
        val borrowOutDate: TextView = view.findViewById(R.id.borrowOutDate)
        val borrowData: TextView = view.findViewById(R.id.borrowDate)
        val renewBtn: Button = view.findViewById(R.id.renewBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.borrow_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val borrow = borrowList[position]
        holder.bookName.text = borrow.bookTitle
        holder.bookAuthor.text = "作者："+borrow.bookAuthor
        holder.bookID.text = "编号："+borrow.bookId
        holder.bookType.text = "类型："+borrow.bookType
        holder.borrowData.text = "借阅时间：" + borrow.borrowDate
        holder.borrowOutDate.text = "到期时间：" + borrow.borrowOutdateTime
        val prefs = ReaderClientApplication.context?.getSharedPreferences("data", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val status = prefs.getString(borrow.bookId, "1")
        if(status == "0")holder.renewBtn.isEnabled = false
        //val borrowdate = LocalDate.parse(borrow.borrowDate, DateTimeFormatter.ISO_DATE)
        //val outdate = LocalDate.parse(borrow.borrowOutdateTime, DateTimeFormatter.ISO_DATE)

        holder.renewBtn.setOnClickListener(){
            val viewModel by lazy { ViewModelProviders.of(fragment).get(BorrowHistoryViewModel::class.java)}
            val token = prefs?.getString("token", "").toString()
            val sno = prefs?.getString("sno", "").toString()
            val book = borrowList[position]
            val bookId = book.bookId
            editor.putString(bookId, "0")
            editor.apply()
            viewModel.renewBook(token, Renew(book.bookId, sno))
            viewModel.renewBookLiveData.observe(fragment.viewLifecycleOwner, Observer{
                    result ->
                Log.d("retesting:", result.toString())
                if(result.getOrNull()!=null)
                {
                    val response = result.getOrNull() as RenewResponse
                    Toast.makeText(ReaderClientApplication.context, response.msg, Toast.LENGTH_SHORT).show()
                    Log.d("testing", result.getOrNull().toString())
                    if(response.code == "-1"){
                        Toast.makeText(ReaderClientApplication.context, response.msg, Toast.LENGTH_SHORT).show()
                    }else{
                        result.exceptionOrNull()?.printStackTrace()
                    }
                }
            })
            holder.renewBtn.isEnabled = false

        }
        
    }

    override fun getItemCount() = borrowList.size

}
