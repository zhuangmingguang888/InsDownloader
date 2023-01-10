package com.tree.insdownloader.view.widget;

import static com.tree.insdownloader.config.JosefinSansFont.SEMI_BOLD_ASSETS_PATH;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tree.insdownloader.R;
import com.tree.insdownloader.util.TextSpannableUtil;
import com.tree.insdownloader.util.TypefaceUtil;

public class MyPrivacyView extends FrameLayout implements View.OnClickListener {

    public OnPrivacyViewListener listener;

    public MyPrivacyView(Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.privacy_layout, this, false);
        Typeface semiBold = TypefaceUtil.getSemiBoldTypeFace();
        FrameLayout flContinue = view.findViewById(R.id.fl_continue);
        TextView privacy = view.findViewById(R.id.tv_privacy);
        TextView privacyTitle1 = view.findViewById(R.id.tv_privacy_title1);
        TextView privacyTitle2 = view.findViewById(R.id.tv_privacy_title2);
        TextView privacyTitle3 = view.findViewById(R.id.tv_privacy_title3);
        TextView privacyContinue = view.findViewById(R.id.tv_privacy_continue);

        privacy.setTypeface(semiBold);
        privacyTitle1.setTypeface(semiBold);
        privacyTitle2.setTypeface(semiBold);
        privacyTitle3.setTypeface(semiBold);
        privacyContinue.setTypeface(semiBold);

        int privacyContent = R.string.text_privacy_content;
        int privacyContentUrl = R.string.text_privacy_content_url;
        SpannableString privacyText = TextSpannableUtil.setTextUrlSpannableByTwoString(privacyContent, privacyContentUrl, null, semiBold);
        privacy.setMovementMethod(LinkMovementMethod.getInstance());
        privacy.setText(privacyText);
        flContinue.setOnClickListener(this);
        addView(view);
    }

    public void setListener(OnPrivacyViewListener listener) {
        this.listener = listener;
    }

    public interface OnPrivacyViewListener {
        void continueOnclick();
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.continueOnclick();
        }
    }
}
