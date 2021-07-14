package com.minux.memo

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment(), View.OnClickListener, BaseDialog.BaseDialogClickListener {
    override fun onClick(v: View?) {

    }

    fun showDialog(title: String, context: Context){
        val dig = BaseDialog(context)
        dig.listener = this
        dig.show(title)
    }

    override fun onOKClicked() {

    }
}