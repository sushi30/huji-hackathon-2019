package com.hackathon.huji.hujihackathon

import android.content.Context
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView


class UsersAdapter(context: Context, users: ArrayList<User>) : ArrayAdapter<User>(context, 0, users) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val user = getItem(position)
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_member, parent, false)
        }
        val avatar = convertView!!.findViewById(R.id.item_avatar) as ImageView
        val name = convertView.findViewById(R.id.item_text) as TextView
        avatar.setImageResource(R.drawable.ic_person_black_24dp)
        name.text = user!!.name
        return convertView
    }
}