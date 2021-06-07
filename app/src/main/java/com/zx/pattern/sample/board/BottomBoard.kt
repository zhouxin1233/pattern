package com.zx.pattern.sample.board

import android.view.LayoutInflater
import android.view.ViewGroup
import com.zx.pattern.Board
import com.zx.pattern.Container
import com.zx.pattern.GroupBoard
import com.zx.pattern.sample.R

class BottomBoard(container: Container): GroupBoard(container) {
    private lateinit var groupView: ViewGroup

    override fun setup(root: ViewGroup?) {
        groupView = LayoutInflater.from(context)
            .inflate(R.layout.board_bottom, root, true) as ViewGroup

    }

    override fun getGroupView(): ViewGroup {
        return groupView
    }

    override fun boards(): MutableList<Class<out Board>> {
        return ArrayList<Class<out Board>>().apply {
            add(BottomMoreBoard::class.java)
        }
    }

}