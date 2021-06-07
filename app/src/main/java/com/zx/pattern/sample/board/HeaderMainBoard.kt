package com.zx.pattern.sample.board

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.zx.pattern.Board
import com.zx.pattern.Container
import com.zx.pattern.sample.BoardMessage
import com.zx.pattern.sample.R
import com.zx.pattern.sample.model.ConfigInfo

class HeaderMainBoard(container: Container) : Board(container) {
    private lateinit var rootView:ViewGroup
    override fun setup(root: ViewGroup?) {
        rootView =  LayoutInflater.from(context).inflate(R.layout.board_header_main,root,true) as ViewGroup

        container?.observe(ConfigInfo::class.java)?.addObserver {
            // 数据更新了
            Log.e("zhouxin"," ${it.nickName} ${it.age}")
        }
    }
    override fun canHandleMessage(msgType: Int): Boolean {
        return msgType == BoardMessage.MSG_FINISH_PAGE.ordinal
                || msgType == BoardMessage.MSG_FROM_BOTTOM.ordinal
    }

    override fun onReceiveMessage(msgType: Int, msg: Any?) {
        when(msgType){
            BoardMessage.MSG_FROM_BOTTOM.ordinal -> {

                Toast.makeText(rootView.context,"other board 传来信息 ${(msg as ConfigInfo).nickName} ${Thread.currentThread().name}",Toast.LENGTH_SHORT).show()
            }
            else ->{

            }
        }
    }
}