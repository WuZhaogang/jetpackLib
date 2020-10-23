package com.wzg.jetpacklib

import android.os.Bundle
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.Observer
import com.orhanobut.logger.Logger
import com.wzg.jetpacklib.basic.BaseActivity
import com.wzg.jetpacklib.databinding.ActivityMainBinding

class MainActivity : BaseActivity<UserViewModel, ActivityMainBinding>() {

    override fun getLayoutId(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        val userModel = UserModel()
        userModel.username = "暗示大萨达撒大所大所多"
        mDataBinding?.userResp = userModel
        mViewModel?.userResp?.observe(getLifecycleOwner(), Observer {
            Logger.e(it.userModel.username.toString())
        })
    }


    object TextFunc {
        @BindingAdapter("onSetText")
        @JvmStatic
        fun onSetText(view: View?, content: String?) {
            Logger.e("   aaaaaaa")
        }
    }

}
