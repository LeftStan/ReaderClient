package com.example.readerclient.ui.searchborrow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.readerclient.R
import com.example.readerclient.logic.model.BorrowData


class ReturnAdapter(private val fragment: Fragment, private val borrowList: List<BorrowData>):
    RecyclerView.Adapter<ReturnAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bookName: TextView = view.findViewById(R.id.bookNameN)
        val bookAuthor: TextView = view.findViewById(R.id.bookAuthorN)
        val bookID: TextView = view.findViewById(R.id.bookIDN)
        val bookType : TextView = view.findViewById(R.id.bookTypeN)
        val borrowOutDate: TextView = view.findViewById(R.id.borrowOutDate)
        val borrowData: TextView = view.findViewById(R.id.borrowDate)
        val btn: Button = view.findViewById(R.id.renewBtn)

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
        holder.borrowOutDate.text = "归还时间：" + borrow.borrowOutdateTime
        holder.btn.visibility = View.GONE
    }

    override fun getItemCount() = borrowList.size
}
