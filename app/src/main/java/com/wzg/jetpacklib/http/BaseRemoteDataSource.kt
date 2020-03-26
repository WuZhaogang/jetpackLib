package com.wzg.jetpacklib.http

import android.annotation.SuppressLint
import com.wzg.jetpacklib.basic.IBaseViewModelEvent
import com.wzg.jetpacklib.basic.OptionT
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

/**
 * 类描述:
 * 创建人:    wzg
 * 创建时间:  2020/3/24
 * 修改备注:  说明本次修改内容
 */
open class BaseRemoteDataSource<S>(private val baseViewModelEvent: IBaseViewModelEvent?) {

    protected fun <T : Any> getService(clz: Class<T>, host: String): T {
        return RetrofitClient.getInstance().getService(clz, host)
    }

    protected fun <T> execute(observable: Observable<T>, callback: RequestCallback<T>?, quietly: Boolean = false) {
        execute(observable, BaseSubscriber(callback), quietly)
    }

    protected fun <T> executeQuietly(observable: Observable<T>, callback: RequestCallback<T>?) {
        execute(observable, BaseSubscriber(callback), true)
    }

    @SuppressLint("CheckResult")
    private fun <T> execute(observable: Observable<T>, observer: DisposableObserver<T>, quietly: Boolean) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    if (!quietly) {
                        showLoading()
                    }
                }.doFinally {
                    if (!quietly) {
                        dismissLoading()
                    }
                }.subscribeWith(observer)
    }

    private fun <T> createData(t: T): Observable<T> {
        return Observable.create { emitter ->
            try {
                emitter.onNext(t)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }

    private fun showLoading() {
        baseViewModelEvent?.showLoading()
    }

    private fun dismissLoading() {
        baseViewModelEvent?.dismissLoading()
    }

    private fun showToast(msg: String) {
        baseViewModelEvent?.showToast(msg)
    }

}