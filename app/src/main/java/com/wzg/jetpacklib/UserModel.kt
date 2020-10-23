package com.wzg.jetpacklib

import android.view.View
import com.orhanobut.logger.Logger

/**
 * 类描述:
 * 创建人:    wzg
 * 创建时间:  2020/3/23
 * 修改备注:  说明本次修改内容
 */
class UserModel {
    var id: String? = null
    var username: String? = null

    fun testClick(view: View,content:String?){
        Logger.e("dasdasdadasd   "+ content)
    }
}