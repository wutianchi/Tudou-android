package com.gunlei.app.ui.util;


import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 文件管理工具类
 * @author liuchangjiong@yongche.com
 * 2014-12-18
 */
public class FileManager {

    public static final String DIR_CHAT_VOICE = "chat_voice";
    public static final String DIR_DOWNLOAD_APK = "apk";

    private final static String TAG = FileManager.class.getSimpleName().toString();

    /**
     * 判断sdcard是否可用
     *
     * @return true/false
     */
    public static boolean sdCardIsAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取Android手机中SD卡存储信息 获取剩余空间
     *
     * @return 剩余空间 MB
     */
    public static long getSDCardInfo() {
        // 需要判断手机上面SD卡是否插好，如果有SD卡的情况下，我们才可以访问得到并获取到它的相关信息，当然以下这个语句需要用if做判断
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // 取得sdcard文件路径
            File path = Environment.getExternalStorageDirectory();
            StatFs statfs = new StatFs(path.getPath());
            // 获取block的SIZE
            long blocSize = statfs.getBlockSize();
            // 空闲的Block的数量
            long availaBlock = statfs.getAvailableBlocks();
            // 计算总空间大小和空闲的空间大小
            // 存储空间大小跟空闲的存储空间大小就被计算出来了。
            return blocSize * availaBlock / 1024 / 1024;

        }

        return 0;
    }


    /**
     * 保存数据到文件,如果文件夹不存在，会自动创建好
     *
     * @param ctx
     * @param inSDCard 是否保存到SDCard
     * @param dirName  保存到的文件夹名称
     * @param fileName 文件名
     * @param is       输入流
     * @return 写文件出错时返回false，成功时返回true
     */
    public static boolean saveFile(Context ctx, boolean inSDCard, String dirName, String fileName, InputStream is) {
        File outFile = newFile(ctx, inSDCard, dirName, fileName);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(outFile);
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = is.read(buffer)) != -1) {
                fos.write(buffer, 0, count);
            }
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 创建一个文件夹文件对象，创建对象后是否自动在存储设备上创建文件夹由参数createDirIfNeeded指定
     *
     * @param ctx
     * @param inSDCard          是否在SDCard上创建
     * @param dirName           文件夹名称
     * @param createDirIfNeeded 是否自动在存储设备上创建对应的文件夹
     * @return 文件夹文件对象
     */
    public static File newDir(Context ctx, boolean inSDCard, String dirName, boolean createDirIfNeeded) {
        File dir = null;
        if (inSDCard) {
            dir = new File(Environment.getExternalStorageDirectory() + File.separator + "." +
                    ctx.getPackageName() + File.separator + dirName);
        } else {
            dir = new File(ctx.getFilesDir(), dirName);
        }

        if (createDirIfNeeded && !dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    /**
     * 创建一个文件夹文件对象，创建对象后会自动在存储设备上创建对应的文件夹，如不需要自动创建文件夹请使用
     * newDir(Context ctx, boolean inSDCard, String dirName, boolean createDirIfNeeded)
     * 并将最后一个参数置为false
     *
     * @param ctx
     * @param inSDCard 是否在SDCard上创建
     * @param dirName  文件夹名称
     * @return 文件夹文件对象
     */
    public static File newDir(Context ctx, boolean inSDCard, String dirName) {
        return newDir(ctx, inSDCard, dirName, true);
    }

    /**
     * 创建一个普通文件对象，创建对象后是否自动在存储设备上创建相关的文件夹由参数createDirIfNeeded指定
     *
     * @param ctx
     * @param inSDCard          是否在SDCard上创建
     * @param dirName           保存到的文件夹名称
     * @param fileName          文件名
     * @param createDirIfNeeded 是否在存储设备上自动创建相关的文件夹
     * @return 文件对象
     */
    public static File newFile(Context ctx, boolean inSDCard, String dirName, String fileName, boolean createDirIfNeeded) {
        return new File(newDir(ctx, inSDCard, dirName, createDirIfNeeded), fileName);
    }

    /**
     * 创建一个普通文件对象，创建对象后会自动在存储设备上创建相关的文件夹，如不需要自动创建文件夹请使用
     * newFile(Context ctx, boolean inSDCard, String dirName, String fileName, boolean createDirIfNeeded)
     * 并将最后一个参数置为false
     *
     * @param ctx
     * @param inSDCard 是否在SDCard上创建
     * @param dirName  保存到的文件夹名称
     * @param fileName 文件名
     * @return 文件对象
     */
    public static File newFile(Context ctx, boolean inSDCard, String dirName, String fileName) {
        return newFile(ctx, inSDCard, dirName, fileName, true);
    }

    /**
     * 判断文件是否存在
     *
     * @param ctx
     * @param inSDCard 是否在SDCard的文件
     * @param dirName  保存文件的文件夹名称
     * @param fileName 文件名
     * @return true代表文件存在，false为不存在
     */
    public static boolean exists(Context ctx, boolean inSDCard, String dirName, String fileName) {
        File file = newFile(ctx, inSDCard, dirName, fileName, false);
        return file.exists();
    }

    /**
     * 判断文件夹是否存在
     *
     * @param ctx
     * @param inSDCard 是否在SDCard的文件夹
     * @param dirName  文件夹名称
     * @return true代表文件夹存在，false为不存在
     */
    public static boolean exists(Context ctx, boolean inSDCard, String dirName) {
        File dir = newDir(ctx, inSDCard, dirName, false);
        return dir.exists();
    }

    /**
     * 删除存储设备上的文件
     *
     * @param ctx
     * @param inSDCard 文件是否在SDCard
     * @param dirName  文件存储的文件夹
     * @param fileName 文件名
     * @return true代表文件删除成功，false为删除失败
     */
    public static boolean deleteFile(Context ctx, boolean inSDCard, String dirName, String fileName) {
        File file = newFile(ctx, inSDCard, dirName, fileName, false);
        if (file != null && file.exists()) {
            return file.delete();
        } else {
            return true;
        }
    }

    /**
     * 删除指定的文件夹中的文件，满足filter条件的所有文件将被删除
     *
     * @param ctx
     * @param inSDCard 文件是否在SDCard
     * @param dirName  文件夹名称
     * @param filter   文件过滤条件，filter.accept(File file)接口返回true的文件将被删除
     */
    public static void deleteFiles(Context ctx, boolean inSDCard, String dirName, FileFilter filter) {
        File dir = newDir(ctx, inSDCard, dirName, false);
        if (dir.exists()) {
            if (filter != null) {
                for (File file : dir.listFiles()) {
                    if (filter.accept(file)) {
                        file.delete();
                    }
                }
            } else {
                for (File file : dir.listFiles()) {
                    file.delete();
                }
            }
        }
    }

    /**
     * 删除指定的文件夹中的所有文件
     *
     * @param ctx
     * @param inSDCard 文件是否在SDCard
     * @param dirName  文件夹名称deleteFilesdeleteFilesdeleteFiles
     */
    public static void deleteFiles(Context ctx, boolean inSDCard, String dirName) {
        deleteFiles(ctx, inSDCard, dirName, null);
    }

    /**
     * 删除文件夹中的所有文件，并将此文件夹也删除，如只需要删除文件夹中的文件，请使用deleteFiles
     *
     * @param ctx
     * @param inSDCard 文件是否在SDCard
     * @param dirName  文件夹名称
     */
    public static void deleteDir(Context ctx, boolean inSDCard, String dirName) {
        File dir = newDir(ctx, inSDCard, dirName, false);
        if (dir.exists()) {
            deleteFiles(ctx, inSDCard, dirName);
            dir.delete();
        }
    }


    /**
     * 删除指定文件  (传入绝对路径)
     */
    public static void deleteFile(String filePath) {
        new File(filePath).delete();
    }


    /**
     * 判断文件是否存在(传入绝对路径)
     *
     * @return true代表文件存在，false为不存在
     */
    public static boolean exists(String filePath) {
        return new File(filePath).exists();
    }

    /**
     * 删除所有文件（除了 名称为  XX）
     */
    public static void deleteFiles(final String dirFileString, final String picName) {
        //删除文件夹下的所有文件(包括子目录)
        try {
            new Thread() {
                public void run() {
                    File dirFile = new File(dirFileString);
                    File[] files = dirFile.listFiles();
                    if (files != null) {
                        for (int i = 0; i < files.length; i++) {
                            //删除子文件
                            if (files[i].toString().indexOf(picName) == -1) {
                                FileManager.deleteFile(files[i].getAbsolutePath());
                            }
                        }
                    }
                }

                ;
            }.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteFolderFile(String filePath, boolean deleteThisPath) throws IOException {

        if (!TextUtils.isEmpty(filePath)) {
            File file = new File(filePath);
            if (file.isDirectory()) {// 处理目录  
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFolderFile(files[i].getAbsolutePath(), true);
                }
            }

            if (deleteThisPath) {
                if (!file.isDirectory()) {// 如果是文件，删除  
                    file.delete();
                } else {// 目录  
                    if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除  
                        file.delete();
                    }  
                }  
            }  
        }  
    }

    /**
     * 获取SD卡路径
     * */
    public static final String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(
                Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
         }

    /**
     * 保存
     * @param path
     * @param filename
     * @param data
     */
    public static void save(String path, String filename, String data) {
        FileOutputStream fos = null;
        File sdpath = new File(path);
        if (!sdpath.exists()) {
            sdpath.mkdirs();
        }
        LogUtils.e("filename:" + sdpath + filename);
        try {
            fos = new FileOutputStream(sdpath + filename);
            fos.write(data.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取指定文件的 内容
     * @param path
     * @param filename
     * @return
     */
    public static String read(String path, String filename) {
        File f1 = new File(path + "/" + filename);
        String str = null;
        InputStream is = null;
        InputStreamReader input = null;
        try {
            LogUtils.e("f1:" + f1);
            is = new FileInputStream(f1);
            input = new InputStreamReader(is, "UTF-8");
            BufferedReader reader = new BufferedReader(input);
            while ((str = reader.readLine()) != null) {
                return str;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return str;

    }

    /**
     * 服务大厅 apk下载路径
     * @param c
     * @return
     */
    public static String downloadApkDir(Context c){
        String path = c.getPackageName() + File.separator + DIR_DOWNLOAD_APK;
        return path;
    }
}
