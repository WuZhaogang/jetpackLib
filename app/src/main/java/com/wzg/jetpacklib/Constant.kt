package com.wzg.jetpacklib

/**
 * 类描述:
 * 创建人:    wzg
 * 创建时间:  2020/3/23
 * 修改备注:  说明本次修改内容
 */
const val SP_NAME = "spCashLoan"
const val SP_USER_KEY = "spUserModel"
const val SP_TOKEN = "spAccessToken"
const val SP_NAVIGATION_ADDRESS = "navigationAddress"
const val SP_NAVIGATION_NAME = "navigationName"

const val CODE_SUCCESS = "SUCCESS"
const val CODE_UNKNOWN = "-1"

// 数据字典
const val SP_DIC_KEY = "spDictModel"
const val SP_DIC_ORG_KEY = "dicDataOrg"

const val PAGE_SIZE = 10
const val START_PAGE = 0
const val NO_DATA_DEFAULT_PAGE = 0

val BASE_URL = if (BuildConfig.DEBUG) "http://192.168.3.66:9000/" else "http://192.168.3.66:9000/"


//百度语音Appid
const val BD_VOICE_APPID = "15427727"