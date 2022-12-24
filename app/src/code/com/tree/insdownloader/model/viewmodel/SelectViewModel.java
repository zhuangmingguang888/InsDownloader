package com.tree.insdownloader.model.viewmodel;

import android.content.Context;
import com.tree.insdownloader.AppManager;
import com.tree.insdownloader.dialog.SelectDialog;
import com.tree.insdownloader.util.LocaleUtil;
import com.tree.insdownloader.view.activity.HomeActivity;
import java.util.Locale;

public class SelectViewModel {

    public void changeTheme(int tag) {
        switch (tag) {
            case SelectDialog.DARK:

                break;
            case SelectDialog.LIGHT:
                break;
            case SelectDialog.SYSTEM_DEFAULT:
                break;

        }
    }

    public void changeLanguage(int tag) {
        Locale locale = null;
        switch (tag) {
            case SelectDialog.CHINESE:
                locale = Locale.CHINA;
                break;
            case SelectDialog.AFRIKAANS:
                locale = new Locale("af");
                break;
            case SelectDialog.ARABIC:
                locale = new Locale("ar");
                break;
            case SelectDialog.DANISH:
                locale = new Locale("da");
                break;
            case SelectDialog.FRENCH:
                locale = Locale.FRENCH;
                break;
            case SelectDialog.DUTCH:
                locale = new Locale("nl");
                break;
            case SelectDialog.CZECH:
                locale = new Locale("cs");
                break;
            default:
                locale = Locale.ENGLISH;
                break;
        }
        Context context = AppManager.getInstance().getActivity(HomeActivity.class);
        LocaleUtil.changeAllActivityLanguage(locale, context);
        AppManager.getInstance().goHomeActivity(HomeActivity.class);
    }
}
