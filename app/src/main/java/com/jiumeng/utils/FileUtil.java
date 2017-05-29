package com.jiumeng.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by jiumeng on 2017/5/15.
 */

public class FileUtil {


    /*
    * 该方法会判断当前sd卡是否存在，然后选择缓存地址
    * */
    public static File getDiskDir(String dirName) {
        File fileParent;
        if (!sdIsAvailable() || getSdCardAvailableSize() < 30) {
            Debug.e("sd card Not Available");
            fileParent = new File(Environment.getDataDirectory().getAbsolutePath() + File.separator + dirName);
        } else {
            fileParent = new File(Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + File.separator + dirName);
        }
        if (!fileParent.exists()) {
            fileParent.mkdirs();
        }
        Debug.video("parentDir:" + fileParent.getAbsolutePath());
        return fileParent;
    }

    /**
     * 该方法会判断当前sd卡是否存在，然后选择缓存地址
     *
     * @param context
     * @param dirName
     * @return
     */
    public static File getDiskCacheDir(Context context, String dirName) {
        String cachePath;
        if (FileUtil.sdIsAvailable() && FileUtil.getSdCardAvailableSize() > 10) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        File file = new File(cachePath + File.separator + dirName);
        if (!file.exists()){
            file.mkdirs();
        }
        return file;
    }

    /*
    * 判断sd卡是否可用
    * */
    public static boolean sdIsAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }


    public static void writeObject(File fileParent,String fileName,Object obj) {

        File file = new File(fileParent, fileName);
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file));
            os.writeObject(obj);
            os.close();
        } catch (Exception e) {
            Debug.e("writeObject error:" + e.getLocalizedMessage());
            e.printStackTrace();
        }

    }

    public static Object readObject(File fileParent,String fileName) {

        File file = new File(fileParent, fileName);
        try {
            ObjectInputStream os = new ObjectInputStream(new FileInputStream(file));
            Object object = os.readObject();
            os.close();
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            Debug.e("readObject error:" + e.getLocalizedMessage());
            return null;
        }
    }

    /*
    * 获取sd卡剩余空间大小，单位：m
    * */
    public static int getSdCardAvailableSize() {
        return (int) (Environment.getExternalStorageDirectory().getUsableSpace() / 1024 / 1024);
    }


}
