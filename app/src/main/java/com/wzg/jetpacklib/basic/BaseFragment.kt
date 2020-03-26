package com.wzg.jetpacklib.basic

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider

/**
 * 类描述:
 * 创建人:    wzg
 * 创建时间:  2020/3/24
 * 修改备注:  说明本次修改内容
 */
abstract class BaseFragment : Fragment(), IIBaseViewModelEventObserver {
    private var loadDialog: ProgressDialog? = null
    protected var isViewCreated: Boolean = false
    protected var isLoadCompleted: Boolean = false
    var rootView: View? = null

    protected abstract fun lazyLoad()

    override fun getLContext(): Context = this.context!!

    abstract fun getLayoutId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView == null) {
            rootView = layoutInflater.inflate(getLayoutId(), container, false)
            isViewCreated = true
        } else {
            val viewGroup: ViewGroup? = rootView?.parent as ViewGroup?
            viewGroup?.removeView(rootView)
        }
        return rootView
    }

    override fun getLifecycleOwner(): LifecycleOwner = this

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && isViewCreated && !isLoadCompleted) {
            //处理Fragment嵌套Fragment userVisibleHint 一直返回true的问题
            if (parentFragment != null) {
                if (parentFragment?.userVisibleHint != null && parentFragment?.userVisibleHint!!) {
                    lazyLoad()
                    isLoadCompleted = true
                }
            } else {
                lazyLoad()
                isLoadCompleted = true
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (userVisibleHint) {
            lazyLoad()
            isLoadCompleted = true
        }
    }

    override fun showLoading(msg: String) {
        if (loadDialog == null) {
            loadDialog = ProgressDialog(getLContext())
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
        activity?.finish()
    }

    override fun pop() {

    }

    fun <T : BaseViewModel> getViewModel(clazz: Class<T>) = ViewModelProvider(this).get(clazz)

}