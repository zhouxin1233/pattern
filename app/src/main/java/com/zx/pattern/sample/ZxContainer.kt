package com.zx.pattern.sample

import android.content.Context
import com.zx.pattern.Board
import com.zx.pattern.Container
import com.zx.pattern.sample.board.BottomBoard
import com.zx.pattern.sample.board.HeaderBoard

class ZxContainer(context:Context) : Container(context){
    override fun boards(): MutableList<Class<out Board>> {
        return mutableListOf(
            HeaderBoard::class.java,
            BottomBoard::class.java
        )
//            .also {
//            it.add(BottomBoard::class.java)
//        }
        // todo:zx also
    }

    override fun onCreate() {
        super.onCreate()
        ZxDriver.ins.container = this
    }

    fun dispatchMessage(msgType: BoardMessage) {
        dispatchMessage(msgType.ordinal)
    }

    fun dispatchMessage(msgType: BoardMessage, msg: Any?) {
        dispatchMessage(msgType.ordinal,msg)
    }
}