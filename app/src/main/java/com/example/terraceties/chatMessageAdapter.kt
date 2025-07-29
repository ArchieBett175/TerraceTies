package com.example.terraceties

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class chatMessageAdapter(private val messageList :
                         ArrayList<chatMessageClass>):RecyclerView.Adapter<chatMessageAdapter.chatViewHolder>(){

    inner class chatViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        val userName : TextView = itemView.findViewById(R.id.usernameText)
        val messageText : TextView = itemView.findViewById(R.id.messageText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): chatViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.chat_item, parent, false)
        return chatViewHolder(v)
    }

    override fun getItemCount() = messageList.size

    override fun onBindViewHolder(holder: chatViewHolder, position: Int) {
        holder.userName.text = (messageList[position].userName)
        holder.messageText.text = (messageList[position].message)
    }




}
