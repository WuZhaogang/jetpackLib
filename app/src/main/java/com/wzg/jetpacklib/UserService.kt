package com.wzg.jetpacklib

import com.wzg.jetpacklib.basic.BaseResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * 类描述:
 * 创建人:    wzg
 * 创建时间:  2020/3/23
 * 修改备注:  说明本次修改内容
 */
interface UserService {
    @POST("/manage-service/api/userController/appLogin")
    fun login(@Body beanLogin: LoginRequestModel): Observable<UserResp>
}