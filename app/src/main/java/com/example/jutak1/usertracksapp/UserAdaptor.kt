package com.example.jutak1.usertracksapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdaptor(private var userList:ArrayList<User>) : RecyclerView.Adapter<UserAdaptor.UsersViewHolder>() {

     class UsersViewHolder(v: View):RecyclerView.ViewHolder(v){
        val textView : TextView = v.findViewById(R.id.tvUserItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        TODO("Not yet implemented")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_user, parent, false)
        return UsersViewHolder(view)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
        return userList.size
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        TODO("Not yet implemented")
        val user = userList[position]
        holder.textView.text = user.userName

    }


}