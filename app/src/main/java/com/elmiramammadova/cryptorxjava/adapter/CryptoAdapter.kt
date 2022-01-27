package com.elmiramammadova.cryptorxjava.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.elmiramammadova.cryptorxjava.R
import com.elmiramammadova.cryptorxjava.model.CryptoModel
import kotlinx.android.synthetic.main.recycler_row.view.*
import java.util.zip.Inflater

class CryptoAdapter(private val cryptoList:ArrayList<CryptoModel>, private val listener : Listener):RecyclerView.Adapter<CryptoAdapter.CryptoHolder>() {
    interface Listener{
        fun onItemClick(cryptoModel: CryptoModel)
    }
    private val colors:Array<String> = arrayOf("#53ae66","#536cae","#ae5364","#53aea4","#5359ae","#53ae5f","#ae9253","#17efbb")


    class CryptoHolder(view: View):RecyclerView.ViewHolder(view){
        fun bind(cryptoModel: CryptoModel,position: Int,listener:Listener,colors:Array<String>){
            itemView.textCurrency.text=cryptoModel.name
            itemView.textPrice.text=cryptoModel.price
            itemView.setBackgroundColor(Color.parseColor(colors[position % 8]))
            itemView.setOnClickListener {
                listener.onItemClick(cryptoModel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.recycler_row,parent,false)
       return CryptoHolder(view)
    }

    override fun onBindViewHolder(holder: CryptoHolder, position: Int) {
       holder.bind(cryptoList[position],position,listener,colors)
    }

    override fun getItemCount(): Int {
      return cryptoList.size
    }
}