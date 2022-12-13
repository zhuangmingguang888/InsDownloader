package com.tree.insdownloader.view

import android.graphics.Color
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.FragmentManager
import com.tree.insdownloader.R
import com.tree.insdownloader.base.BaseActivity
import com.tree.insdownloader.util.ApiUtil
import com.tree.insdownloader.view.fragment.GuideFragment



class StartActivity : BaseActivity() {

    private lateinit var guideFragment: GuideFragment
    private lateinit var fragmentManager: FragmentManager

    override fun getLayoutId() = R.layout.activity_start

    override fun initData() {
        guideFragment = GuideFragment()
        fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().apply {
            add(R.id.fl_start, guideFragment)
            show(guideFragment)
            commitAllowingStateLoss()
        }

        if(ApiUtil.isMOrHeight) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = Color.TRANSPARENT
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }


}