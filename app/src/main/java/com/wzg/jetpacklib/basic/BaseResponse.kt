package com.wzg.jetpacklib.basic

/**
 * 类描述:
 * 创建人:    wzg
 * 创建时间:  2020/3/23
 * 修改备注:  说明本次修改内容
 */
class BaseResponse<T>(
        var code: String = "",
        var message: String? = null,
        var data: T
)