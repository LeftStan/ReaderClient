package com.example.readerclient.ui.debtquery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.readerclient.R
import com.example.readerclient.logic.model.DebtData
import com.example.readerclient.logic.model.HotBookData

class DebtAdapter (private val fragment: Fragment, private val resList: List<DebtData>):
    RecyclerView.Adapter<DebtAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bookName: TextView = view.findViewById(R.id.bookNameD)
        val bookID: TextView = view.findViewById(R.id.bookIDD)
        val debtMoney: TextView = view.findViewById(R.id.debtMoney)
        val debtReason: TextView = view.findViewById(R.id.debtReason)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DebtAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.debt_item, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder:DebtAdapter.ViewHolder, position: Int) {
        val temp = resList[position]
        holder.bookName.text = temp.bookTitle
        holder.bookID.text = "图书编号："+ temp.bookId
        holder.debtMoney.text = "欠款数目：" + temp.debtMoney
        holder.debtReason.text = "欠款原因：" + temp.debtReason
    }

    override fun getItemCount() = resList.size

}