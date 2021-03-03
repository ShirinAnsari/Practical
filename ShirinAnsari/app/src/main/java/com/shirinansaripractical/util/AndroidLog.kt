package com.shirinansaripractical.util

import android.content.Context
import android.util.Log
import com.shirinansaripractical.BuildConfig

class AndroidLog(context: Context) {

    init {
        mContext = context
    }

    companion object {
        private var isLogEnabled: Boolean? = BuildConfig.DEBUG
        private lateinit var mContext: Context

        fun v(tag: String, message: String) {
            if (isLogEnabled!!) {
                Log.v(tag, message)
            }
        }

        fun d(tag: String, message: String) {
            if (isLogEnabled!!) {
                Log.d(tag, message)
            }
        }

        fun i(tag: String, message: String) {
            if (isLogEnabled!!) {
                Log.i(tag, message)
            }
        }

        fun w(tag: String, message: String) {
            if (isLogEnabled!!) {
                Log.w(tag, message)
            }
        }

        fun e(tag: String, message: String) {
            if (isLogEnabled!!) {
                Log.e(tag, message)
            }
        }
    }
}
