package com.wzg.jetpacklib.basic

/**
 * 类描述:
 * 创建人:    wzg
 * 创建时间:  2020/3/24
 * 修改备注:  说明本次修改内容
 */
open class BaseEvent(open val action: Int)

class BaseViewModelEvent(override val action: Int) : BaseEvent(action) {

    companion object {

        const val SHOW_LOADING_DIALOG = 1

        const val DISMISS_LOADING_DIALOG = 2

        const val SHOW_TOAST = 3

        const val FINISH = 4
    }

    var message: String = ""

}