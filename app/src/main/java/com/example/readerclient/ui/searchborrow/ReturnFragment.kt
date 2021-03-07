package com.example.readerclient.ui.searchborrow

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.readerclient.R
import com.example.readerclient.logic.model.BorrowResponse
import kotlinx.android.synthetic.main.fragment_borrow_history.borrowHistoryRecyclerView

class ReturnFragment: Fragment()  {

    val viewModel by lazy { ViewModelProviders.of(this).get(BorrowHistoryViewModel::class.java)}

    private lateinit var adapter: ReturnAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_borrow_history, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val layoutManager = LinearLayoutManager(activity)
        borrowHistoryRecyclerView.layoutManager = layoutManager
        adapter = ReturnAdapter(this, viewModel.borrowList)
        borrowHistoryRecyclerView.adapter = adapter

        val prefs = activity?.getSharedPreferences("data", Context.MODE_PRIVATE)
        val token = prefs?.getString("token", "").toString()
        val cardId = prefs?.getString("sno", "").toString()

        viewModel.searchBorrowHistory(token, cardId)
        viewModel.borrowLiveData.observe(viewLifecycleOwner, Observer {
                result ->
            if(result.getOrNull()!=null)
            {
                val borrowResponse = result.getOrNull() as BorrowResponse
                if(borrowResponse.code == "0"){
                    val borrow = borrowResponse.data
                    viewModel.borrowList.clear()
                    for(i in borrow){
                        Log.d("who", i.toString())
                        if(i.isReturn == "1")
                            viewModel.borrowList.add(i)
                    }
                    adapter.notifyDataSetChanged()
                }else{
                    result.exceptionOrNull()?.printStackTrace()
                }
            }
        })

    }
}