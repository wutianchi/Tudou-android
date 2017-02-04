package com.gunlei.app.ui.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {
	/**
	 *SD卡地址
	 */
	private static String sdPath=Environment.getExternalStorageDirectory().getPath();
	/**
	 * 缓存地址
	 */
	private static String cachePath=null;
	/**
	 * 搭车图片文件存放目录
	 */
	private final static String folder = "/YDImage";
	/**
	 * 
	 * @param context
	 */
	public FileUtil(Context context) {
		cachePath=context.getCacheDir().getPath();
//		context.getFilesDir()
		System.out.println("cachePath:"+cachePath);
	}

   /** 
	* 存储图片文件地址
	* @return 
	*/  
	public static String getStorageDir() {
		return (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))?sdPath+folder :
			cachePath+folder;
	}
    /**
     * 判断sdcard是否可用
     * @return
     */
    public static boolean sdCardIsOK(){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
	/**
	 * 存储bitmap
	 * @param fileName
	 * @param bitmap
	 */
	public static void saveBitmap(String fileName,Bitmap bitmap){
		if(bitmap==null)
			return;
		String path=getStorageDir();
		File folderFile=new File(path);
		if(!folderFile.exists()){
			folderFile.mkdir();
		}
		File file=new File(path+File.separator+fileName);
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		FileOutputStream fos;
			try {
				fos = new FileOutputStream(file);
				bitmap.compress(CompressFormat.JPEG, 100, fos);
				fos.flush();
				fos.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
    public static void compressBmpToFile(Bitmap bmp,File file){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 90;
//        bmp.getByteCount();
        bmp.compress(CompressFormat.JPEG,options,baos);
        while (baos.toByteArray().length/1024>100){
            baos.reset();
            options-=10;
            bmp.compress(CompressFormat.JPEG,options,baos);
        }
        try{
            FileOutputStream fos=new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
	/**
	 * 获取Bitmap
	 * @param fileName
	 * @return
	 */
	public static Bitmap getBitmap(String fileName) {
        try {
            Bitmap file = BitmapFactory.decodeFile(getStorageDir() + File.separator + fileName);
           return file;
        }catch(Exception e){
           return null;
        }
    }
    public static File getFile(String fileName) {
       return new File(getStorageDir() + File.separator + fileName);
    }
	
	public boolean isFileExits(String fileName){
		return new File(getStorageDir()+File.separator+fileName).exists();
	}
	
	public long getFileSize(String fileName) {
		return new File(getStorageDir()+File.separator+fileName).length();
	}
    /**
     * 从输入流读取数据
     * @param inStream
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len = inStream.read(buffer)) !=-1 ){
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        return outSteam.toByteArray();
    }

    /**
     * 将输出流写入到文件
     * @param inStream
     * @param outFilePath
     * @throws Exception
     */
    public static void outInputStream(InputStream inStream, String outFilePath) throws Exception{
        FileOutputStream outStream = new FileOutputStream(outFilePath);
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len = inStream.read(buffer)) !=-1 ){
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
    }

	/**
	 * 删除文件
	 * @return
	 */
	public boolean deleteFile(){
		File dirFile=new File(getStorageDir());
		if(!dirFile.exists()){
			return false;
		}
		if(dirFile.isDirectory()){
			String[] list = dirFile.list();
			for (int i = 0; i < list.length; i++) {
				new File(dirFile,list[i]).delete();
			}
		}
		dirFile.delete();
		return true;
	}

    /**
     * 获取文件名的扩展名。
     * @param fileName
     * @return
     */
    public static String getExtension(String fileName) {
        if (fileName != null) {
            int i = fileName.lastIndexOf('.');
            if (i > 0 && i < fileName.length() - 1) {
                return fileName.substring(i + 1).toLowerCase();
            }
        }
        return "";
    }

}
