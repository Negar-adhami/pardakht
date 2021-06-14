package com.rhyme.modiriathesab

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_transaction.view.*

class TransactionAdapter (var itemsList: ArrayList<Transaction>) : RecyclerView.Adapter<TransactionAdapter.LampViewHolder>(){


    fun updateLamps(newCarts: ArrayList<Transaction>) {
        itemsList.clear()
        itemsList.addAll(newCarts)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : LampViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.item_transaction, parent, false)

        return LampViewHolder(cellForRow).listen { pos, _ ->
            val lamp = itemsList[pos]
        }
    }


    override fun getItemCount() = itemsList.size


    override fun onBindViewHolder(holder: LampViewHolder, position: Int) {
        holder.bind(itemsList[position])
    }

    class LampViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val titleTv = view.title_tv
        private val costTv = view.cost_tv

        fun bind(lamp: Transaction) {
            val titleString = FaNum.convert(lamp.title)
            titleTv.text = titleString
            val PriceString = FaNum.convert(lamp.amount.toString()).trim() + " تومان"
            costTv.text = PriceString
        }
    }

}