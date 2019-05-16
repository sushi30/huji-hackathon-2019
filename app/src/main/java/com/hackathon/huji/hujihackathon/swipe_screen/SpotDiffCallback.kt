package com.hackathon.huji.hujihackathon.swipe_screen

import android.support.v7.util.DiffUtil
import com.hackathon.huji.hujihackathon.Group

class SpotDiffCallback(
    private val old: List<Group>,
    private val new: List<Group>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return old.size
    }

    override fun getNewListSize(): Int {
        return new.size
    }

    override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return old[oldPosition].id == new[newPosition].id
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return old[oldPosition] == new[newPosition]
    }

}
