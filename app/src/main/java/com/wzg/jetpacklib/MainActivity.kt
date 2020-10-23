package com.wzg.jetpacklib

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.orhanobut.logger.Logger
import com.wzg.jetpacklib.basic.BaseActivity
import com.wzg.jetpacklib.databinding.ActivityMainBinding
import com.wzg.jetpacklib.databinding.ItemTestBindingBinding

class MainActivity : BaseActivity<UserViewModel, ActivityMainBinding>() {

    override fun getLayoutId(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        val userModel = UserModel()
        userModel.username = ("暗示大萨达撒大所大所多")
        mDataBinding?.userModel = userModel
        mViewModel?.userResp?.observe(getLifecycleOwner(), Observer {
            Logger.e(it.userModel.username.toString())
        })

        val userModels = ArrayList<UserModel>()
        for (i in 0..10) {
            val tempUserModel = UserModel()
            tempUserModel.username = ("aaaa")
            userModels.add(tempUserModel)
        }
        mDataBinding?.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Logger.e(userModel.username + "       bbbbbbb")
            }
        })
        mDataBinding?.rlData?.layoutManager = LinearLayoutManager(this)
        val testBindingAdapter = TestBindingAdapter(userModels)
        mDataBinding?.rlData?.adapter = testBindingAdapter
        testBindingAdapter.setOnItemClickListener { adapter, view, position ->
            val binding = DataBindingUtil.getBinding<ItemTestBindingBinding>(view)
            if (binding != null) {
                binding.userModel?.username = ("dadadadsadas")
                binding.executePendingBindings()
//                testBindingAdapter.notifyItemChanged(position)
            }
        }
    }
}
