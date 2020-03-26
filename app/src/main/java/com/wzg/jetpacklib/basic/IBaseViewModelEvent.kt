package com.wzg.jetpacklib.basic

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner

/**
 * 类描述:
 * 创建人:    wzg
 * 创建时间:  2020/3/24
 * 修改备注:  说明本次修改内容
 */
interface IBaseViewModelEvent {

    fun showLoading(msg: String)

    fun showLoading() {
        showLoading("")
    }

    fun dismissLoading()

    fun showToast(msg: String)

    fun finishView()

}

interface IIBaseViewModelEventObserver : IBaseViewModelEvent {

    fun getLifecycleOwner(): LifecycleOwner

    fun getLContext(): Context

    fun <T> startActivity(clazz: Class<T>) {
        getLContext().startActivity(Intent(getLContext(), clazz))
    }

    override fun showToast(msg: String) {
        Toast.makeText(getLContext(), msg, Toast.LENGTH_SHORT).show()
    }

}