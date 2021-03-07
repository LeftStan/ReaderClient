package com.example.readerclient.ui.searchbook

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.readerclient.R
import com.example.readerclient.ReaderClientApplication
import com.example.readerclient.logic.model.Book
import com.example.readerclient.logic.model.BookData
import com.example.readerclient.logic.model.BookResponse
import kotlinx.android.synthetic.main.fragment_search_book.*

class BookFragment: Fragment() {
    val viewModel by lazy { ViewModelProviders.of(this).get(BookViewModel::class.java)}

    private lateinit var adapter: BookAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_book, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val layoutManager = LinearLayoutManager(activity)
        bookRecyclerView.layoutManager = layoutManager
        adapter = BookAdapter(this, viewModel.bookList)
        bookRecyclerView.adapter = adapter
        //adapter =BookAdapter(this, getBooks())
        //bookRecyclerView.adapter = adapter
        val prefs = activity?.getSharedPreferences("data", Context.MODE_PRIVATE)
        var token = prefs?.getString("token", "").toString()
        //Toast.makeText(ReaderClientApplication.context, prefs?.getString("token", ""), Toast.LENGTH_SHORT).show()
        //Toast.makeText(ReaderClientApplication.context, bookinfo, Toast.LENGTH_SHORT).show()

        searchBookEdit.addTextChangedListener { editable ->
            val content = editable.toString()
            val bookinfo = searchBookspinner.selectedItem.toString()
            var book = Book("", "", "", "", "", "")
            if (content.isNotEmpty()) {
                //viewModel.searchBooks("", "")
                if(bookinfo == "书名"){
                    book = Book("", "", "", "", content, "")
                }else if(bookinfo == "类型"){
                    book = Book("", "", "", "", "", content)
                }else if(bookinfo == "作者"){
                    book = Book(content, "", "", "", "", "")
                }else if(bookinfo == "出版社"){
                    book = Book("", "", "", content, "", "")
                }else if(bookinfo == "图书ID"){
                    book = Book("", content, "", "", "", "")
                }else if(bookinfo == "ISBN"){
                    book = Book("", "", content, "", "", "")
                }
                //Toast.makeText(ReaderClientApplication.context, book.toString(), Toast.LENGTH_SHORT).show()
                //viewModel.searchBooks(token, book)
                viewModel.bookList.clear()
                viewModel.searchBooks(token, book)
                adapter.notifyDataSetChanged()
            } else {
                viewModel.bookList.clear()
                adapter.notifyDataSetChanged()
                bookRecyclerView.visibility = View.GONE
                bgImageView.visibility = View.VISIBLE
            }

        }
        viewModel.bookLiveData.observe(viewLifecycleOwner, Observer{
                result ->
            if(result.getOrNull()!=null)
            {
                val bookResponse = result.getOrNull() as BookResponse
                bookRecyclerView.visibility = View.VISIBLE
                bgImageView.visibility = View.GONE
                Log.d("testing", result.getOrNull().toString())
                if(bookResponse.code == "0"){
                    val books = bookResponse
                    viewModel.bookList.clear()
                    viewModel.bookList.addAll(books.data)
                    adapter.notifyDataSetChanged()
                }else{
                    result.exceptionOrNull()?.printStackTrace()
                }
            }
            //Toast.makeText(ReaderClientApplication.context, books.toString(), Toast.LENGTH_SHORT).show()
        })


    }

//data class Book(val id: String, val title: String, val author: String,
//                val press: String, val type: String, val borrowable: String)
 /*   private fun getBooks(): List<Book>{
        val booksList = ArrayList<Book>()
        val funName = arrayOf(
            "名称", "声音", "图像", "大小",
            "时间", "背景", "电池", "网络"
        )
        val funColor = arrayOf(
            "red", "green", "blue", "yellow",
            "red", "green", "blue", "yellow"
        )
        for(i in 0..7){
            //val news = News("This is news title $i", getRandomLengthString("This is news content $i. "))
            val books = Book(funName[i], funColor[i], funName[i], funColor[i], funName[i], funColor[i])
            booksList.add(books)
        }
        return booksList
    }
*/
}