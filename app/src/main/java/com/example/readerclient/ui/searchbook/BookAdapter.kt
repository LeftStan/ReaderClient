package com.example.readerclient.ui.searchbook

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

class BookAdapter(private val fragment: Fragment, private val bookList: List<BookData>):
    RecyclerView.Adapter<BookAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bookName: TextView = view.findViewById(R.id.bookName)
        val bookAuthor: TextView = view.findViewById(R.id.bookAuthor)
        val bookID: TextView = view.findViewById(R.id.bookID)
        val bookISBN: TextView = view.findViewById(R.id.bookISBN)
        val bookType : TextView = view.findViewById(R.id.bookType)
        val bookPress: TextView = view.findViewById(R.id.bookPress)
        val reserveBtn: Button = view.findViewById(R.id.reserveBtn)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = bookList[position]
        holder.bookName.text = book.bookTitle
        holder.bookAuthor.text = "作者："+book.bookAuthor
        holder.bookID.text = "编号："+book.bookId
        holder.bookISBN.text = "ISBN:"+book.bookIsbn
        holder.bookType.text = "类型："+book.bookType
        holder.bookPress.text = "出版社："+book.bookPress
        if(book.borrowAble.toInt() < 1 || book.isOrdered.toInt() > 0 || book.isBorrowed.toInt() > 0)
        {
            holder.reserveBtn.isEnabled = false
        }
        val viewModel by lazy { ViewModelProviders.of(fragment).get(BookViewModel::class.java)}
        val prefs = ReaderClientApplication.context?.getSharedPreferences("data", Context.MODE_PRIVATE)
        val token = prefs?.getString("token", "").toString()
        val sno = prefs?.getString("sno", "").toString()
        holder.reserveBtn.setOnClickListener(){
            //val position = holder.adapterPosition
            val book = bookList[position]
            viewModel.reserveBook(token, Reserve(book.bookId, sno))
            viewModel.reserveResLiveData.observe(fragment.viewLifecycleOwner, Observer{
                    result ->
                if(result.getOrNull()!=null)
                {
                    val response = result.getOrNull() as ReserveResponse

                    Log.d("testing", result.getOrNull().toString())
                    if(response.code == "0"){
                        Toast.makeText(ReaderClientApplication.context, response.msg, Toast.LENGTH_SHORT).show()
                    }else{
                        result.exceptionOrNull()?.printStackTrace()
                    }
                }
            })
            holder.reserveBtn.isEnabled = false
        }

    }

    override fun getItemCount() = bookList.size
}
