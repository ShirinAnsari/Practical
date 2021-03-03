package com.shirinansaripractical.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

object CommonUtil {

    fun showSnackBar(view: View, msg: String) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show()
    }
}