package com.hemraj.mo_engage.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hemraj.mo_engage.R
import kotlinx.android.synthetic.main.bottomsheet_item_view.view.*
import kotlinx.android.synthetic.main.generic_info_bottom_sheet_view.view.*

class GenericInfoBottomSheet(): BottomSheetDialogFragment() {

    private var title: String? = null
    private var itemList = arrayListOf<String>()
    private var reqCode: Int = 1001
    private var listenerCallBack: ICallback? = null

    constructor(builder: Builder) : this() {
        title = builder.getTitle()
        itemList = builder.getItemList()
        reqCode = builder.getRequestCode()
        listenerCallBack = builder.getListenerCallBacks()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.generic_info_bottom_sheet_view, container, false)
        initView(rootView)
        return rootView
    }

    private fun initView(rootView: View) {
        rootView.bottomSheetTitle.text = title

        rootView.itemListLinearLayout.removeAllViews()
        itemList.forEach {
            val textView = layoutInflater.inflate(R.layout.bottomsheet_item_view, null)
            textView.bottomSheetFilterTv.text = it
            textView.setOnClickListener { _->
                listenerCallBack?.onOptionSelected(reqCode, it)

            }
            rootView.itemListLinearLayout.addView(textView)
        }
    }


    fun show(activity: AppCompatActivity, tag: String) {
        if (!activity.supportFragmentManager.isStateSaved) {
            show(activity.supportFragmentManager, tag)
        }
    }

    interface ICallback {

        fun onOptionSelected(reqCode: Int, text: String)
    }


    class Builder {
        private lateinit var genericInfoBottomSheet: GenericInfoBottomSheet
        private var title: String? = null
        private var itemList: ArrayList<String> = arrayListOf()
        private var reqCode: Int = 1001
        private var mListenerCallBack: ICallback? = null

        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun getTitle(): String? {
            return title
        }

        fun setItemList(itemList: ArrayList<String>): Builder {
            this.itemList = itemList
            return this
        }

        fun getItemList(): ArrayList<String> {
            return itemList
        }

        fun setListenerCallBacks(listener: ICallback): Builder {
            mListenerCallBack = listener
            return this
        }

        fun getListenerCallBacks(): ICallback? {
            return mListenerCallBack
        }

        fun setRequestCode(reqCode: Int): Builder {
            this.reqCode = reqCode
            return this
        }

        fun getRequestCode(): Int {
            return reqCode
        }

        fun build(): GenericInfoBottomSheet {
            genericInfoBottomSheet = GenericInfoBottomSheet(this)
            return genericInfoBottomSheet
        }
    }
}