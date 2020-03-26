package com.wzg.jetpacklib

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.wzg.jetpacklib.basic.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<UserViewModel>() {
    override fun getLayoutId(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }

    override fun createViewModel(): UserViewModel? {
        return getViewModel(UserViewModel::class.java)
    }

    override fun initView() {
        tvTest.setOnClickListener {
            mViewModel?.login(LoginRequestModel())
        }
        mViewModel?.userResp?.observe(getLifecycleOwner(), Observer {
            Log.e("dasdasdasd", it.userModel.username)
        })
    }
}
