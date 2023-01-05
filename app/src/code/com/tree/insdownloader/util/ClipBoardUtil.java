package com.tree.insdownloader.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

import com.tree.insdownloader.app.App;

public class ClipBoardUtil {

    /**
     * 获取剪切板内容
     *
     * @return
     */

    public static String pastToClipBoard() {

        ClipboardManager manager = (ClipboardManager) App.getAppContext().getSystemService(Context.CLIPBOARD_SERVICE);

        if (manager != null) {

            if (manager.hasPrimaryClip() && manager.getPrimaryClip().getItemCount() > 0) {

                CharSequence addedText = manager.getPrimaryClip().getItemAt(0).getText();

                String addedTextString = String.valueOf(addedText);

                if (!TextUtils.isEmpty(addedTextString)) {

                    return addedTextString;

                }

            }

        }

        return "";

    }

    /**
     * 清空剪切板
     */

    public static void clearToClipBoard() {

        ClipboardManager manager = (ClipboardManager) App.getAppContext().getSystemService(Context.CLIPBOARD_SERVICE);

        if (manager != null) {

            try {

                manager.setPrimaryClip(manager.getPrimaryClip());

                manager.setPrimaryClip(ClipData.newPlainText("", ""));

            } catch (Exception e) {

                e.printStackTrace();

            }

        }

    }

    /**
     * 复制剪切板
     */

    public static void copyToClipBoard(String copyStr) {

        ClipboardManager manager = (ClipboardManager) App.getAppContext().getSystemService(Context.CLIPBOARD_SERVICE);
        if (manager != null) {
            try {
                //创建一个新的文本clip对象
                ClipData clip = ClipData.newPlainText("simple text", copyStr);
                //把clip对象放在剪贴板中
                manager.setPrimaryClip(clip);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


}