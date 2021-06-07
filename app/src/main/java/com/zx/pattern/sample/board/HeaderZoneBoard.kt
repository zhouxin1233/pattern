package com.zx.pattern.sample.board

import android.view.ViewGroup
import com.zx.pattern.Board
import com.zx.pattern.sample.BoardMessage
import com.zx.pattern.Container

class HeaderZoneBoard(container: Container) : Board(container) {
    override fun setup(root: ViewGroup?) {
    }

    override fun canHandleMessage(msgType: Int): Boolean {
        return msgType == BoardMessage.MSG_HEAD.ordinal
    }

    override fun onReceiveMessage(msgType: Int, msg: Any?) {
    }
}