package com.wzg.jetpacklib.ext

import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

/**
 * 类描述:    通用方法
 * 创建人:    wzg
 * 创建时间:  2020/10/22
 * 修改备注:  说明本次修改内容
 */

fun Any.toJsonStr() = Gson().toJson(this)

//获取时间
fun Long.getDateStr() = fun(): String {
    //时间格式,HH是24小时制，hh是AM PM12小时制
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm")
//比如timestamp=1449210225945；
    val date_temp = java.lang.Long.valueOf(this)
    val date_string = sdf.format(Date(date_temp))
    return date_string
//至于取10位或取13位，date_temp*1000L就是这种截取作用。如果是和服务器传值的，就和后台商量好就可以了
}