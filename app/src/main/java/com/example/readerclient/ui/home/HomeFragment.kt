package com.example.readerclient.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.readerclient.R
import com.example.readerclient.ReaderClientApplication
import com.example.readerclient.ui.hotbook.HotBook
import com.example.readerclient.ui.reservation.BookReserve
import com.example.readerclient.ui.searchbook.SearchBook
import com.example.readerclient.ui.searchborrow.ReturnBook
import com.example.readerclient.ui.searchborrow.BorrowSearch

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val textView: TextView = root.findViewById(R.id.text_home)
        val sBtn: ImageButton = root.findViewById(R.id.searchBtn)
        val kBtn: ImageButton = root.findViewById(R.id.bookKeepBtn)
        val hBtn: ImageButton = root.findViewById(R.id.bookHistoryBtn)
        val tBtn: ImageButton = root.findViewById(R.id.bookTopBtn)
        val rBtn: ImageButton = root.findViewById(R.id.reservationBtn)

        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val prefs = activity?.getSharedPreferences("data", Context.MODE_PRIVATE)
        Log.d("Im trying to get token!", prefs?.getString("token", ""))

        sBtn.setOnClickListener(){
            val intent = Intent(ReaderClientApplication.context, SearchBook::class.java)
            startActivity(intent)
        }

        kBtn.setOnClickListener(){
            val intent = Intent(ReaderClientApplication.context, BorrowSearch::class.java)
            startActivity(intent)
        }

        hBtn.setOnClickListener(){
            val intent = Intent(ReaderClientApplication.context, ReturnBook::class.java)
            startActivity(intent)
        }

        tBtn.setOnClickListener(){
            val intent = Intent(ReaderClientApplication.context, HotBook::class.java)
            startActivity(intent)
        }

        rBtn.setOnClickListener(){
            val intent = Intent(ReaderClientApplication.context, BookReserve::class.java)
            startActivity(intent)
        }


        return root
    }
}