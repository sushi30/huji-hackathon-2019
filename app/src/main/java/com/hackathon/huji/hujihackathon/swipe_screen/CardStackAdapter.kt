package com.hackathon.huji.hujihackathon.swipe_screen

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.hackathon.huji.hujihackathon.Group
import com.hackathon.huji.hujihackathon.R
import me.gujun.android.taggroup.TagGroup


class CardStackAdapter(
    private var groups: List<Group> = emptyList()
) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            inflater.inflate(
                R.layout.item_group,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val group = groups[position]
        holder.name.text = "${group.name}"
        holder.tags.setTags(*group.tags.toTypedArray())
        Glide.with(holder.image)
            .load(group.image)
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

    fun addGroups(inGroups: List<Group>) {
        inGroups.forEach { g -> groups = groups + g }
    }

    fun getGroups(): List<Group> {
        return groups
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.item_name)
        var image: ImageView = view.findViewById(R.id.item_image)
        var tags: TagGroup = view.findViewById(R.id.tag_group)
    }

}
