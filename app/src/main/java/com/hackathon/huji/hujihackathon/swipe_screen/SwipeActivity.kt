package com.hackathon.huji.hujihackathon

import android.arch.lifecycle.ViewModelProviders
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.util.DiffUtil
import android.support.v7.widget.DefaultItemAnimator
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import com.yuyakaido.android.cardstackview.*
import kotlin.collections.ArrayList
import GroupInfoDialogFragment
import com.hackathon.huji.hujihackathon.swipe_screen.CardStackAdapter
import com.hackathon.huji.hujihackathon.swipe_screen.SpotDiffCallback


const val LOG_TAG = "SwipeActivity"

class SwipeActivity : AppCompatActivity(), CardStackListener {

    private val drawerLayout by lazy { findViewById<DrawerLayout>(R.id.drawer_layout) }
    private val cardStackView by lazy { findViewById<CardStackView>(R.id.card_stack_view) }
    private val manager by lazy { CardStackLayoutManager(this, this) }
    private val adapter by lazy { CardStackAdapter(ArrayList()) }
    private val model by lazy { ViewModelProviders.of(this).get(SwipingViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe)
        setupNavigation()
        setupCardStackView()
        setupButton()
        model.suggestedGroups.observe(this, Observer<List<Group>> { groups ->
            if (groups == null) {
                adapter.setGroups(ArrayList())
            } else {
                adapter.setGroups(groups)
            }

        })

    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.closeDrawers()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCardDragging(direction: Direction, ratio: Float) {
        Log.d("CardStackView", "onCardDragging: d = ${direction.name}, r = $ratio")
    }

    override fun onCardSwiped(direction: Direction) {
        if (direction == Direction.Left) {
            // set group false
            // add group to never show again!
        } else if (direction == Direction.Right) {
            // send swipe right API
        }
        Log.d("CardStackView", "onCardSwiped: p = ${manager.topPosition}, d = $direction")
        if (manager.topPosition == adapter.itemCount - 5) {
            paginate()
        }
    }

    override fun onCardRewound() {
        Log.d("CardStackView", "onCardRewound: ${manager.topPosition}")
    }

    override fun onCardCanceled() {
        Log.d("CardStackView", "onCardCanceled: ${manager.topPosition}")
    }

    override fun onCardAppeared(view: View, position: Int) {
//        val textView = view.findViewById<TextView>(R.id.item_name)
        Log.d("CardStackView", "onCardAppeared:")
    }

    override fun onCardDisappeared(view: View, position: Int) {
//        val textView = view.findViewById<TextView>(R.id.item_name)
        Log.d("CardStackView", "onCardDisappeared:")
    }

    private fun setupNavigation() {
        // NavigationView
        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.reload -> reload()
                R.id.add_spot_to_first -> addFirst(1)
                R.id.add_spot_to_last -> addLast(1)
                R.id.remove_spot_from_first -> removeFirst(1)
                R.id.remove_spot_from_last -> removeLast(1)
                R.id.replace_first_spot -> replace()
                R.id.swap_first_for_last -> swap()
            }
            drawerLayout.closeDrawers()
            true
        }
    }

    private fun setupCardStackView() {
        initialize()
    }

    private fun setupButton() {
        val skip = findViewById<View>(R.id.skip_button)
        skip.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Left)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager.setSwipeAnimationSetting(setting)
            cardStackView.swipe()
        }

        val info = findViewById<View>(R.id.info_button)
        info.setOnClickListener {
            showEditDialog(manager.topPosition)
        }

        val like = findViewById<View>(R.id.like_button)
        like.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Right)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager.setSwipeAnimationSetting(setting)
            cardStackView.swipe()
        }
    }

    private fun showEditDialog(groupIndex: Int) {
        val fm = supportFragmentManager
        val group = adapter.getGroups()[groupIndex]
        Log.d(LOG_TAG, "Opening info dialog for group: " + groupIndex.toShort())
        val editNameDialogFragment = GroupInfoDialogFragment.newInstance(group)
        editNameDialogFragment.show(fm, "fragment_edit_name")
    }

    private fun initialize() {
        manager.setStackFrom(StackFrom.None)
        manager.setVisibleCount(3)
        manager.setTranslationInterval(8.0f)
        manager.setScaleInterval(0.95f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(Direction.HORIZONTAL)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(true)
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        manager.setOverlayInterpolator(LinearInterpolator())
        cardStackView.layoutManager = manager
        cardStackView.adapter = adapter
        cardStackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
    }

    private fun paginate() {
//        val old = adapter.getGroups()
//        val new = old.plus(createSpots())
//        Log.d(
//            LOG_TAG,
//            StringBuilder().append("paginate: ").append("old: ").append(old.toString()).append("new: ").append(new.toString()).toString()
//        )
//        val callback = SpotDiffCallback(old, new)
//        val result = DiffUtil.calculateDiff(callback)
//        adapter.setGroups(new)
//        result.dispatchUpdatesTo(adapter)
    }

    private fun reload() {
        val old = adapter.getGroups()
        val new = ArrayList<Group>()
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setGroups(new)
        result.dispatchUpdatesTo(adapter)
    }

    private fun addFirst(size: Int) {
        val old = adapter.getGroups()
        val new = mutableListOf<Group>().apply {
            addAll(old)
            for (i in 0 until size) {
                add(manager.topPosition, createGroup())
            }
        }
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setGroups(new)
        result.dispatchUpdatesTo(adapter)
    }

    private fun addLast(size: Int) {
        val old = adapter.getGroups()
        val new = mutableListOf<Group>().apply {
            addAll(old)
            addAll(List(size) { createGroup() })
        }
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setGroups(new)
        result.dispatchUpdatesTo(adapter)
    }

    private fun removeFirst(size: Int) {
        if (adapter.getGroups().isEmpty()) {
            return
        }

        val old = adapter.getGroups()
        val new = mutableListOf<Group>().apply {
            addAll(old)
            for (i in 0 until size) {
                removeAt(manager.topPosition)
            }
        }
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setGroups(new)
        result.dispatchUpdatesTo(adapter)
    }

    private fun removeLast(size: Int) {
        if (adapter.getGroups().isEmpty()) {
            return
        }

        val old = adapter.getGroups()
        val new = mutableListOf<Group>().apply {
            addAll(old)
            for (i in 0 until size) {
                removeAt(this.size - 1)
            }
        }
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setGroups(new)
        result.dispatchUpdatesTo(adapter)
    }

    private fun replace() {
        val old = adapter.getGroups()
        val new = mutableListOf<Group>().apply {
            addAll(old)
            removeAt(manager.topPosition)
            add(manager.topPosition, createGroup())
        }
        adapter.setGroups(new)
        adapter.notifyItemChanged(manager.topPosition)
    }

    private fun swap() {
        val old = adapter.getGroups()
        val new = mutableListOf<Group>().apply {
            addAll(old)
            val first = removeAt(manager.topPosition)
            val last = removeAt(this.size - 1)
            add(manager.topPosition, last)
            add(first)
        }
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setGroups(new)
        result.dispatchUpdatesTo(adapter)
    }

    private fun createGroup(): Group {
        val maccabi = Group("Maccabi SP", "111", "Basketball", "Sacher_Park")
        maccabi.addMember(User("Yuval"))
        maccabi.addMember(User("Itamar"))
        return maccabi
    }
}
