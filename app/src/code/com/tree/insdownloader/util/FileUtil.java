package com.tree.insdownloader.util;


import static com.tree.insdownloader.config.WebViewConfig.DOWNLOAD_INS_ROOT_PATH;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.tree.insdownloader.app.App;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件操作工具
 *
 * @author zhaung
 */
public class FileUtil {

    public static void saveMediaFileToSdcard(String destFileName, InputStream is, Context context) throws Exception {

        String destFileDir = null;
        if (ApiUtil.isROrHeight()) {
            copySandFileToExternalUri(context, destFileName, is);
            return;
        }
        destFileDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + DOWNLOAD_INS_ROOT_PATH;
        File destFile = new File(destFileDir, destFileName);
        if (destFile.exists()) {
            destFile.delete();
        }
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        File dir = new File(destFileDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, destFileName);
        try {
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
            }
        }
    }

    public static String readStringFromAssets(Context context, String fileName) {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        StringBuffer sb = new StringBuffer();
        try {
            inputStream = assetManager.open(fileName);
            isr = new InputStreamReader(inputStream);
            br = new BufferedReader(isr);

            sb.append(br.readLine());
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append("\n" + line);
            }

            br.close();
            isr.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 拷贝沙盒中的文件到外部存储区域
     */
    public static boolean copySandFileToExternalUri(Context context, String newFileName, InputStream is) {
        ContentResolver resolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put(MediaStore.Downloads.DISPLAY_NAME, newFileName);
        values.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + DOWNLOAD_INS_ROOT_PATH);
        Uri uri = MediaStore.Files.getContentUri("external");
        Uri externalUri = resolver.insert(uri, values);
        ContentResolver contentResolver = context.getContentResolver();
        OutputStream outputStream = null;
        boolean ret = false;
        try {
            outputStream = contentResolver.openOutputStream(externalUri);
            int readCount = 0;
            byte[] buffer = new byte[1024];
            while ((readCount = is.read(buffer)) != -1) {
                outputStream.write(buffer, 0, readCount);
                outputStream.flush();
            }
            ret = true;
        } catch (Exception e) {
            ret = false;
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ret;
        }
    }

    /**
     * 根据路径和名字查出文件
     * @param filePath 文件路径
     * @param fileName 文件名
     * @return
     */
    public static Uri FileGetFromPublic(String filePath, String fileName) {
        String queryPath;
        //判断是否有加斜杠
        if (!filePath.endsWith("/")) {
            filePath = filePath + File.separator;
        }
        //判断Android版本
        if (ApiUtil.isQOrHeight()) {
            //Android10以下
            //加上文件名
            filePath = filePath + fileName;
            //使用DATA字段做查询
            queryPath = MediaStore.Images.Media.DATA;
        } else {
            //Android10及以上
            //使用RELATIVE_PATH字段做查询
            queryPath = MediaStore.Images.Media.RELATIVE_PATH;
        }
        //拼接查询条件
        //queryPath表示文件所在路径
        //DISPLAY_NAME表示文件名
        String selection = queryPath + "=? and " + MediaStore.Images.Media.DISPLAY_NAME + "=?";
        Cursor cursor = App.getAppContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Files.FileColumns._ID}, selection, new String[]{filePath, fileName}, null);

        if (cursor != null && cursor.moveToFirst()) {
            //查出id
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));
            //根据id查询URI
            Uri uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
            return uri;
        }
        //关闭查询
        if (cursor != null) {
            cursor.close();
        }
        return null;
    }
}
