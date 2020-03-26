package com.wzg.jetpacklib

/**
 * 类描述:
 * 创建人:    wzg
 * 创建时间:  2020/3/23
 * 修改备注:  说明本次修改内容
 */
class UserResp {
    var userModel = UserModel()

    class UserModel {
        var id: String? = null
        var username: String? = null
    }
}