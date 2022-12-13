package com.tree.insdownloader.view.fragment

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import com.tree.insdownloader.R
import com.tree.insdownloader.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_guide.*

class GuideFragment : BaseFragment() {

    override fun getLayoutId() = R.layout.fragment_guide

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tv_guide_title.typeface = Typeface.createFromAsset(context?.assets,"fonts/JosefinSans-SemiBold.ttf")
    }
}