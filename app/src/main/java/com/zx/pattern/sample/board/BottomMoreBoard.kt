package com.zx.pattern.sample.board

import android.view.ViewGroup
import android.widget.Button
import com.zx.pattern.Board
import com.zx.pattern.Container
import com.zx.pattern.sample.BoardMessage
import com.zx.pattern.sample.R
import com.zx.pattern.sample.ZxDriver
import com.zx.pattern.sample.model.ConfigInfo

class BottomMoreBoard(container: Container): Board(container) {
    override fun setup(root: ViewGroup?) {
        root?.findViewById<Button>(R.id.bottomTv2)?.setOnClickListener {
            val configInfo = ConfigInfo()
            configInfo.age = 19
            configInfo.nickName="小新"
            configInfo.uid = "123456"

            ZxDriver.ins.container?.observe(ConfigInfo::class.java)?.update {
                configInfo
            }
        }
        root?.findViewById<Button>(R.id.bottomBtn)?.setOnClickListener {
            val configInfo = ConfigInfo()
            configInfo.age = 18
            configInfo.nickName="小新"
            configInfo.uid = "123456"

            ZxDriver.ins.container?.provide(configInfo)


            ZxDriver.ins.container?.dispatchMessage(BoardMessage.MSG_FROM_BOTTOM,configInfo)
        }



    }

    override fun canHandleMessage(msgType: Int): Boolean {
       return true
    }

    override fun onReceiveMessage(msgType: Int, msg: Any?) {

    }

}