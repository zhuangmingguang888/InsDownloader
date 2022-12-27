package com.tree.insdownloader.util;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * 文件操作工具
 *
 * @author zhaung
 */
public class FileUtil {

  /*  public static void saveMediaFileToSdcard(String destFileName, InputStream is, Context context) {

        String destFileDir = null;
        if (ApiUtil.isROrHeight()) {
            copySandFileToExternalUri(context, destFileName, is);
            return;
        }
        destFileDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + TEST_DOWNLOAD_INS_PATH;
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
        } catch (Exception e) {
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
    }*/

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
/*    public static boolean copySandFileToExternalUri(Context context, String newFileName, InputStream is) {
        ContentResolver resolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put(MediaStore.Downloads.DISPLAY_NAME, newFileName);
        values.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + TEST_DOWNLOAD_INS_PATH);
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
    }*/
}
