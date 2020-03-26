package com.wzg.jetpacklib.basic

/**
 * 类描述:
 * 创建人:    wzg
 * 创建时间:  2018/2/1
 * 修改时间:  2018/2/1
 * 修改备注:  说明本次修改内容
 */
data class BaseServerErrorModel(
    var error: String?, // Bad Request
    var errors: List<Error?>?,
    var message: String?, // 密码错误
    var path: String?, // ServletWebRequest: uri=/api/userController/appLogin;client=192.168.3.66
    var status: Int?, // 400
    var timestamp: Long? // 1585104136653
) {
    data class Error(
        var code: String?, // badRequestException
        var defaultMessage: String?, // 密码错误
        var objectName: String? // login
    )
}