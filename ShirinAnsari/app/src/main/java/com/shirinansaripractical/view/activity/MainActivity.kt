package com.shirinansaripractical.view.activity

import android.os.Bundle
import com.shirinansaripractical.view.activity.base.BaseActivity
import com.shirinansaripractical.R
import com.shirinansaripractical.databinding.ActivityMainBinding


class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView(R.layout.activity_main)
    }
}