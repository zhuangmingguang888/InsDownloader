package com.tree.insdownloader.util;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;

import com.tree.insdownloader.R;
import com.tree.insdownloader.app.App;

public class TextSpannableUtil {

    public static SpannableString setTextUrlSpannableByTwoString(int s1, int s2, String url, Typeface semiBold) {
        String privacyContent = App.getAppContext().getString(s1);
        String privacyContentUrl = App.getAppContext().getString(s2);
        int start = privacyContentUrl.indexOf(privacyContent);
        int end = privacyContentUrl.length() - 1;
        SpannableString spannableString = new SpannableString(privacyContentUrl);
        spannableString.setSpan(new UnderlineSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new URLSpan("http://www.baidu.com "), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //网络
        spannableString.setSpan(new ForegroundColorSpan(App.getAppContext().getColor(R.color.text_color_privacy_url)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
