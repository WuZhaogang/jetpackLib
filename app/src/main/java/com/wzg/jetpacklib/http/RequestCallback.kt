package com.wzg.jetpacklib.http

/**
 * 作者：leavesC
 * 时间：2019/5/31 10:47
 * 描述：
 */
interface RequestCallback<T> {
    fun onSuccess(data: T)
    fun onFail(exception: BaseException)

}
