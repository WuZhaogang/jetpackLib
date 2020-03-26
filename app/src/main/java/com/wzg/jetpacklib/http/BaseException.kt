package com.wzg.jetpacklib.http

import com.wzg.jetpacklib.CODE_UNKNOWN

/**
 * 作者：leavesC
 * 时间：2019/5/31 10:48
 * 描述：
 */
sealed class BaseException(errorMessage: String, val code: String = CODE_UNKNOWN) :
        RuntimeException(errorMessage)

class ServerResultException(message: String, code: String = CODE_UNKNOWN) : BaseException(message, code)