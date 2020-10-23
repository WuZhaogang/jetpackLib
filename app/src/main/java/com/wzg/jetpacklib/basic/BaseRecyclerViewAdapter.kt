package com.wzg.jetpacklib.basic

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.wzg.jetpacklib.UserModel
import org.jetbrains.annotations.NotNull


/**
 * 类描述:
 * 创建人:    wzg
 * 创建时间:  2020/10/23
 * 修改备注:  说明本次修改内容
 */
abstract class BaseRecyclerViewAdapter<T : UserModel> constructor(layoutResId: Int, data: MutableList<T>) : BaseQuickAdapter<T, BaseViewHolder>(layoutResId, data) {
    /**
     * 当 ViewHolder 创建完毕以后，会执行此回掉
     * 可以在这里做任何你想做的事情
     */
    override fun onItemViewHolderCreated(@NotNull viewHolder: BaseViewHolder, viewType: Int) {
        // 绑定 view
        DataBindingUtil.bind<ViewDataBinding>(viewHolder.itemView)
    }
}