package com.wzg.jetpacklib

import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.orhanobut.logger.Logger
import com.wzg.jetpacklib.basic.BaseRecyclerViewAdapter
import com.wzg.jetpacklib.databinding.ItemTestBindingBinding

/**
 * 类描述:
 * 创建人:    wzg
 * 创建时间:  2020/10/23
 * 修改备注:  说明本次修改内容
 */
class TestBindingAdapter(data: ArrayList<UserModel>) : BaseRecyclerViewAdapter<UserModel>(R.layout.item_test_binding, data) {
    override fun convert(holder: BaseViewHolder, item: UserModel) {
        val itemTestBindingBinding = DataBindingUtil.getBinding<ItemTestBindingBinding>(holder.itemView)
        if (itemTestBindingBinding != null) {
            itemTestBindingBinding.userModel = item
            itemTestBindingBinding.executePendingBindings()
        }
    }
}