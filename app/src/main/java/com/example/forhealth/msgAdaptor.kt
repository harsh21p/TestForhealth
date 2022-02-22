package com.example.forhealth

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
internal class MsgAdaptor(private var msgList: List<ModelMsgClass>, private val listener: ThirdScreen) :
    RecyclerView.Adapter<MsgAdaptor.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view),View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if(position!=RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }

        var msg: TextView = view.findViewById(R.id.msg)

    }

    interface OnItemClickListener{

        fun onItemClick(position: Int){

        }

    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.msg, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = msgList[position]
        holder.msg.text = movie.getMsg()

    }
    override fun getItemCount(): Int {
        return msgList.size
    }
}
