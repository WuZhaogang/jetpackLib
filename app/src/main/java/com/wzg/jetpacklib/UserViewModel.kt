package com.wzg.jetpacklib

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.wzg.jetpacklib.basic.BaseViewModel
import com.wzg.jetpacklib.http.BaseException
import com.wzg.jetpacklib.http.RequestCallback

/**
 * 类描述:
 * 创建人:    wzg
 * 创建时间:  2020/3/24
 * 修改备注:  说明本次修改内容
 */
class UserViewModel : BaseViewModel() {
    private val userDataSource = UserDataSource(this)
    var userResp = MutableLiveData<UserResp>()


    fun login(loginRequestModel: LoginRequestModel) {
        userDataSource.login(loginRequestModel, object : RequestCallback<UserResp> {
            override fun onSuccess(data: UserResp) {
                userResp.value = data
            }

            override fun onFail(exception: BaseException) {
                showToast(exception.message!!)
            }
        })
    }
}