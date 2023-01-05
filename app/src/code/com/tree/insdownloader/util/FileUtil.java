package com.tree.insdownloader.util;


import static com.tree.insdownloader.config.WebViewConfig.DOWNLOAD_INS_ROOT_PATH;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.tree.insdownloader.app.App;
import com.tree.insdownloader.config.WebViewConfig;
import com.tree.insdownloader.logic.network.service.OnDownloadListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * 文件操作工具
 *
 * @author zhaung
 */
public class FileUtil {

    public static final String DOWN_LOAD_PATH = Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_DOWNLOADS + WebViewConfig.DOWNLOAD_INS_ROOT_PATH + File.separator;
    public static long contentLength;

    public static void saveMediaFileToSdcard(String destFileName, InputStream is, OnDownloadListener listener) throws Exception {

        if (ApiUtil.isROrHeight()) {
            copySandFileToExternalUri(destFileName, is, listener);
            return;
        }
        String destFileDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + DOWNLOAD_INS_ROOT_PATH;
        File destFile = new File(destFileDir, destFileName);
        if (destFile.exists()) {
            destFile.delete();
        }
        byte[] buf = new byte[2048];
        int len = 0;
        int rate = 0;
        FileOutputStream fos = null;
        File dir = new File(destFileDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, destFileName);
        try {
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                rate += len;
                int progress = (int) (rate * 100L / contentLength);  //下载进度
                if (listener != null) {
                    listener.onDownloading(progress);
                }
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
    public static boolean copySandFileToExternalUri(String newFileName, InputStream is, OnDownloadListener listener) {
        int rate = 0;
        ContentResolver resolver = App.getAppContext().getContentResolver();
        ContentValues values = new ContentValues();
        values.put(MediaStore.Downloads.DISPLAY_NAME, newFileName);
        values.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + DOWNLOAD_INS_ROOT_PATH);
        Uri uri = MediaStore.Files.getContentUri("external");
        Uri externalUri = resolver.insert(uri, values);
        ContentResolver contentResolver = App.getAppContext().getContentResolver();
        OutputStream outputStream = null;
        boolean ret = false;
        try {
            outputStream = contentResolver.openOutputStream(externalUri);
            int readCount = 0;
            byte[] buffer = new byte[2048];
            while ((readCount = is.read(buffer)) != -1) {
                rate += readCount;
                int progress = (int) (rate * 100L / contentLength);  //下载进度
                if (listener != null) {
                    listener.onDownloading(progress);
                }
                outputStream.write(buffer, 0, readCount);
                outputStream.flush();
            }
            if (listener != null) {
                listener.onDownloadSuccess(null);
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
                Log.d("Exception", e.getMessage());
            }
            return ret;
        }
    }

    /**
     * 根据路径和名字查出文件
     *
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
        Cursor cursor;
        if (fileName.contains(".mp4")) {
            String selection = queryPath + "=? and " + MediaStore.Video.Media.DISPLAY_NAME + "=?";
            cursor = App.getAppContext().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Files.FileColumns._ID}, selection, new String[]{filePath, fileName}, null);
            if (cursor != null && cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media._ID));
                Uri uri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id);
                return uri;
            }
        } else {
            String selection = queryPath + "=? and " + MediaStore.Images.Media.DISPLAY_NAME + "=?";
            cursor = App.getAppContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Files.FileColumns._ID}, selection, new String[]{filePath, fileName}, null);
            if (cursor != null && cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                Uri uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
                return uri;
            }
        }

        //关闭查询
        if (cursor != null) {
            cursor.close();
        }
        return null;
    }

    public static Date getVideoTime(String fileName) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(App.getAppContext(), FileUtil.FileGetFromPublic(DOWN_LOAD_PATH, fileName));
        String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        long timeInmillisec = Long.parseLong(time);
        long duration = timeInmillisec / 1000;
        long hours = duration / 3600;
        long minutes = (duration - hours * 3600) / 60;
        long seconds = duration - (hours * 3600 + minutes * 60);
        Date date = new Date();
        date.setMinutes((int) minutes);
        date.setSeconds((int) seconds % 60);
        return date;
    }


    public static final int SIZETYPE_B = 1;// 获取文件大小单位为B的double值
    public static final int SIZETYPE_KB = 2;// 获取文件大小单位为KB的double值
    public static final int SIZETYPE_MB = 3;// 获取文件大小单位为MB的double值
    public static final int SIZETYPE_GB = 4;// 获取文件大小单位为GB的double值

    /**
     * 获取文件指定文件的指定单位的大小
     *
     * @param filePath 文件路径
     * @param sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB
     * @return double值的大小
     */
    public static double getFileOrFilesSize(String filePath, int sizeType) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("获取文件大小", "获取失败!");
        }
        return FormetFileSize(blockSize, sizeType);
    }

    /**
     * 调用此方法自动计算指定文件或指定文件夹的大小
     *
     * @param filePath 文件路径
     * @return 计算好的带B、KB、MB、GB的字符串
     */
    public static String getAutoFileOrFilesSize(String filePath) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("获取文件大小", "获取失败!");
        }
        return FormetFileSize(blockSize);
    }

    /**
     * 获取指定文件大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
            Log.e("获取文件大小", "文件不存在!");
        }
        return size;
    }

    /**
     * 获取指定文件夹
     *
     * @param f
     * @return
     * @throws Exception
     */
    private static long getFileSizes(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSizes(flist[i]);
            } else {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    private static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 转换文件大小,指定转换的类型
     *
     * @param fileS
     * @param sizeType
     * @return
     */
    private static double FormetFileSize(long fileS, int sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case SIZETYPE_B:
                fileSizeLong = Double.valueOf(df.format((double) fileS));
                break;
            case SIZETYPE_KB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
                break;
            case SIZETYPE_MB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
                break;
            case SIZETYPE_GB:
                fileSizeLong = Double.valueOf(df
                        .format((double) fileS / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }

}
