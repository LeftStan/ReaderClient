package com.example.readerclient.ui.reservation


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.readerclient.R
import com.example.readerclient.logic.model.*

class BookAdapter(private val fragment: Fragment, private val bookList: List<BookData>):
    RecyclerView.Adapter<BookAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bookName: TextView = view.findViewById(R.id.bookNameR)
        val bookAuthor: TextView = view.findViewById(R.id.bookAuthorR)
        val bookID: TextView = view.findViewById(R.id.bookIDR)
        val bookISBN: TextView = view.findViewById(R.id.bookISBNR)
        val bookType : TextView = view.findViewById(R.id.bookTypeR)
        val bookPress: TextView = view.findViewById(R.id.bookPressR)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reserve_item, parent, false)
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

    }

    override fun getItemCount() = bookList.size
}
