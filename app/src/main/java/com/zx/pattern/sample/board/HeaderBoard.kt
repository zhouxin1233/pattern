package com.zx.pattern.sample.board

import android.view.ViewGroup
import android.widget.FrameLayout
import com.zx.pattern.Board
import com.zx.pattern.Container
import com.zx.pattern.GroupBoard

class HeaderBoard(container: Container): GroupBoard(container) {
    private val headerLayout by lazy {
        FrameLayout(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }
    override fun setup(root: ViewGroup?) {
        root?.addView(headerLayout)
    }

    override fun boards(): MutableList<Class<out Board>> {
        return mutableListOf(
            HeaderMainBoard::class.java
        )
    }

}