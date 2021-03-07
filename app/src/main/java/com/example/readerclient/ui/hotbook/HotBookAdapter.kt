package com.example.readerclient.ui.hotbook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.readerclient.R
import com.example.readerclient.logic.model.HotBookData

class HotBookAdapter (private val fragment: Fragment, private val resList: List<HotBookData>):
    RecyclerView.Adapter<HotBookAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bookName: TextView = view.findViewById(R.id.hotBookName)
        val bookAuthor: TextView = view.findViewById(R.id.hotBookCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotBookAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hotbook_item, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder:HotBookAdapter.ViewHolder, position: Int) {
        val temp = resList[position]
            holder.bookName.text = temp.bookTitle
            holder.bookAuthor.text = "热度："+ temp.borrowCount


    }

    override fun getItemCount() = resList.size

}