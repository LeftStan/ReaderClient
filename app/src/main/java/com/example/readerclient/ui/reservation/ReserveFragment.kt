package com.example.readerclient.ui.reservation

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
import kotlinx.android.synthetic.main.fragment_reserve.*
import kotlinx.android.synthetic.main.fragment_search_book.*

class ReserveFragment: Fragment() {
    val viewModel by lazy { ViewModelProviders.of(this).get(BookViewModel::class.java)}

    private lateinit var adapter: BookAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reserve, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val layoutManager = LinearLayoutManager(activity)
        reserveRecyclerView.layoutManager = layoutManager
        adapter = BookAdapter(this, viewModel.bookList)
        reserveRecyclerView.adapter = adapter

        val prefs = activity?.getSharedPreferences("data", Context.MODE_PRIVATE)
        val token = prefs?.getString("token", "").toString()
        val sno = prefs?.getString("sno", "").toString()
        //Toast.makeText(ReaderClientApplication.context, prefs?.getString("token", ""), Toast.LENGTH_SHORT).show()
        //Toast.makeText(ReaderClientApplication.context, bookinfo, Toast.LENGTH_SHORT).show()

        var book = Book("", "", "", "", "", "")
        viewModel.bookList.clear()
        adapter.notifyDataSetChanged()
        viewModel.searchBooks(token, book)

        viewModel.bookLiveData.observe(viewLifecycleOwner, Observer{
                result ->
            if(result.getOrNull()!=null)
            {
                val bookResponse = result.getOrNull() as BookResponse

                Log.d("testing", result.getOrNull().toString())
                if(bookResponse.code == "0"){
                    val books = bookResponse.data
                    viewModel.bookList.clear()
                    for(i in books){

                        if(i.isOrdered == "1" && i.ordererID == sno)
                            viewModel.bookList.add(i)
                    }
                    adapter.notifyDataSetChanged()
                }else{
                    result.exceptionOrNull()?.printStackTrace()
                }
            }
            //Toast.makeText(ReaderClientApplication.context, books.toString(), Toast.LENGTH_SHORT).show()
        })
    }
}