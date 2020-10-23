package com.wzg.jetpacklib.basic

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType


/**
 * 类描述:
 * 创建人:    wzg
 * 创建时间:  2020/3/24
 * 修改备注:  说明本次修改内容
 */
abstract class BaseActivity<T : BaseViewModel, D : ViewDataBinding> : AppCompatActivity(),
    IIBaseViewModelEventObserver {

    private var loadDialog: ProgressDialog? = null

    var mViewModel: T? = null

    var mDataBinding: D? = null

    override fun getLContext(): Context = this

    @LayoutRes
    abstract fun getLayoutId(savedInstanceState: Bundle?): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBinding = DataBindingUtil.setContentView(this, getLayoutId(savedInstanceState))
        mViewModel = createViewModel()
        initView()
        initViewModel(mViewModel)
    }

    abstract fun initView()

    override fun getLifecycleOwner(): LifecycleOwner = this

    fun initViewModel(mViewModel: BaseViewModel?) {
        mViewModel?.baseActionEvent?.observe(getLifecycleOwner(), Observer {
            when (it.action) {
                BaseViewModelEvent.SHOW_LOADING_DIALOG -> {
                    showLoading(it.message)
                }
                BaseViewModelEvent.DISMISS_LOADING_DIALOG -> {
                    dismissLoading()
                }
                BaseViewModelEvent.SHOW_TOAST -> {
                    showToast(it.message)
                }
                BaseViewModelEvent.FINISH -> {
                    finishView()
                }
            }
        })
    }


    override fun showLoading(msg: String) {
        if (loadDialog == null) {
            loadDialog = ProgressDialog(this)
            loadDialog!!.setCancelable(false)
            loadDialog!!.setCanceledOnTouchOutside(false)
        }
        loadDialog?.let {
            if (!it.isShowing) {
                it.show()
            }
        }
    }

    override fun dismissLoading() {
        loadDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }

    override fun finishView() {
        finish()
    }

    fun <T : BaseViewModel> getViewModel(clazz: Class<T>) = ViewModelProvider(this).get(clazz)


    private fun createViewModel() = ViewModelProvider(this).get(getTClass())

    open fun getTClass(): Class<T> {
        return (javaClass.genericSuperclass as ParameterizedType?)!!.actualTypeArguments[0] as Class<T>
    }
}