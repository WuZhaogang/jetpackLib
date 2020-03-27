package com.wzg.jetpacklib.http

import com.wzg.jetpacklib.basic.BaseServerErrorModel
import io.reactivex.observers.DisposableObserver
import retrofit2.HttpException

/**
 * 类描述:
 * 创建人:    wzg
 * 创建时间:  2020/3/24
 * 修改备注:  说明本次修改内容
 */
class BaseSubscriber<T> constructor(private val requestCallback: RequestCallback<T>?) : DisposableObserver<T>() {

    override fun onNext(t: T) {
        requestCallback?.onSuccess(t)
    }

    override fun onError(e: Throwable) {
        if (requestCallback == null) {
            return
        }
        if (e is HttpException) {
            val bodyString = e.response()?.errorBody()?.string()
            if (bodyString != null) {
                val baseServerErrorModel = RetrofitClient.getInstance().getGson()?.fromJson(bodyString, BaseServerErrorModel::class.java)
                if (e is BaseException) {
                    requestCallback.onFail(e)
                } else {
                    requestCallback.onFail(ServerResultException(message = baseServerErrorModel?.message!!))
                }
            }
        }
    }

    override fun onComplete() {

    }
}