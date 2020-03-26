package com.wzg.jetpacklib

import com.wzg.jetpacklib.basic.IBaseViewModelEvent
import com.wzg.jetpacklib.http.BaseRemoteDataSource
import com.wzg.jetpacklib.http.RequestCallback

/**
 * 类描述:
 * 创建人:    wzg
 * 创建时间:  2020/3/24
 * 修改备注:  说明本次修改内容
 */
class UserDataSource(iBaseViewModelEvent: IBaseViewModelEvent) : BaseRemoteDataSource<UserService>(iBaseViewModelEvent) {
    fun login(loginRequestModel: LoginRequestModel, callback: RequestCallback<UserResp>) {
        execute(getService(UserService::class.java, BASE_URL).login(loginRequestModel), callback)
    }
}