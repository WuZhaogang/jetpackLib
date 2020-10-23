package com.wzg.jetpacklib.ext

import com.google.gson.Gson

/**
 * 类描述:    通用方法
 * 创建人:    wzg
 * 创建时间:  2020/10/22
 * 修改备注:  说明本次修改内容
 */

fun Any.toJsonStr() = Gson().toJson(this)
