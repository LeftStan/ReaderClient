package com.example.readerclient.ui.hotbook

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.readerclient.R
import com.example.readerclient.logic.model.BorrowResponse
import com.example.readerclient.logic.model.HotBookResponse
import com.example.readerclient.ui.searchborrow.BorrowAdapter
import kotlinx.android.synthetic.main.fragment_borrow_history.*
import kotlinx.android.synthetic.main.fragment_borrow_history.borrowHistoryRecyclerView
import kotlinx.android.synthetic.main.fragment_hot_book.*

class HotBookFragment: Fragment()  {

    val viewModel by lazy { ViewModelProviders.of(this).get(HotBookViewModel::class.java)}

    private lateinit var adapter: HotBookAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_hot_book, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val layoutManager = LinearLayoutManager(activity)
        hotBookRecyclerView.layoutManager = layoutManager
        adapter = HotBookAdapter(this, viewModel.resultList)
        hotBookRecyclerView.adapter = adapter

        val prefs = activity?.getSharedPreferences("data", Context.MODE_PRIVATE)
        val token = prefs?.getString("token", "").toString()
        val sno = prefs?.getString("sno", "").toString()

        viewModel.hotBookQuery(token, sno)
        viewModel.hotBookLiveData.observe(viewLifecycleOwner, Observer {
                result ->
            if(result.getOrNull()!=null)
            {
                val response = result.getOrNull() as HotBookResponse
                if(response.code == "0"){
                    val res = response.data
                    viewModel.resultList.clear()
                    viewModel.resultList.addAll(res)
                    adapter.notifyDataSetChanged()
                }else{
                    result.exceptionOrNull()?.printStackTrace()
                }
            }
        })

    }
}