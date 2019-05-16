package com.hackathon.huji.hujihackathon

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide

class CardStackAdapter(
    private var groups: List<Group> = emptyList()
) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_spot, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val group = groups[position]
        holder.name.text = "${group.id}. ${group.name}"
        holder.city.text = group.tags.toString()
        Glide.with(holder.image)
            .load("https://source.unsplash.com/THozNzxEP3g/600x800")
            .into(holder.image)
        holder.itemView.setOnClickListener { v ->
            Toast.makeText(v.context, group.name, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return groups.size
    }

    fun setGroups(spots: List<Group>) {
        this.groups = spots
    }

    fun getGroups(): List<Group> {
        return groups
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.item_name)
        var city: TextView = view.findViewById(R.id.item_city)
        var image: ImageView = view.findViewById(R.id.item_image)
    }

}
