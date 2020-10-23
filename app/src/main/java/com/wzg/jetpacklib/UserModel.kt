package com.wzg.jetpacklib

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

/**
 * 类描述:
 * 创建人:    wzg
 * 创建时间:  2020/3/23
 * 修改备注:  说明本次修改内容
 */
class UserModel : BaseObservable() {
    var id: String? = null
    var username: String = ""
        @Bindable
        get
        set(value) {
            field = value
            notifyPropertyChanged(BR.username)
        }

}