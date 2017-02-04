package com.gunlei.app.ui.util.photo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;

import com.gunlei.app.ui.util.MD5;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * 相机工具，参考微信开放平台SD示例代码，有改动。
 */
public final class CameraUtil {

	private static final String TAG = "MicroMsg.SDK.CameraUtil";
	public static final String PHOTO_DEFAULT_EXT = ".jpg";

	private static String filePath = null;

	private CameraUtil() {
		// can't be instantiated
	}

	/**
	 * 启动拍照,JPEG格式
	 * @author beansoft
	 * @param activity
	 * @param imagePath
	 * @param requestCode
	 */
	public static void startCapture(final Activity activity, String imagePath, int requestCode ) {
		File outputFile = new File(imagePath);
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        ContentValues values = new ContentValues();
//        values.put(MediaStore.Images.Media.TITLE, tempfile.toString());
//        Uri photoUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//                               photoUri= intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempfile));
		// 指定调用相机拍照后照片的储存路径
		intent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(outputFile));
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.name());
		activity.startActivityForResult(intent, requestCode);

//        final String dir = SDCARD_ROOT + "/coderside/";
//        File file = new File(dir);
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//        CameraUtil.takePhoto(this.getActivity(), dir, "head", TAKE_PHOTO_REQUESTCODE);
	}

	/**
	 * 启动拍照,JPEG格式
	 * @author beansoft
	 * @param fragment
	 * @param imagePath
	 * @param requestCode
	 */
	public static void startCapture(final Fragment fragment, String imagePath, int requestCode) {
		File outputFile = new File(imagePath);
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(outputFile));
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.name());
		fragment.startActivityForResult(intent, requestCode);
	}

	// 启动相册
	public static void startAlbum(final Fragment fragment, int requestCode) {
		try {
			Intent intent = new Intent(Intent.ACTION_PICK, null);
			intent.setDataAndType(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
			fragment.startActivityForResult(intent, requestCode);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
			try {
				// 会提示选择相册提供者名字
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
				intent.setType("image/*");
				fragment.startActivityForResult(intent, requestCode);
			} catch (Exception e2) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}


	@SuppressLint("SdCardPath")
	/**
	 * 调用系统剪裁照片的缺点：如果竖拍，照片是躺着的
	 示例代码：startPhotoZoom(Uri.fromFile(new File(imageName)), 480);
	 */
	public static void startPhotoZoom(final Fragment fragment, Uri uri1, int size, String outputImageName, int requestCode) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri1, "image/*");
		// crop为true是设置在开启的intent中设置显示的view可以剪裁
		intent.putExtra("crop", "true");

		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX,outputY 是剪裁图片的宽高
		intent.putExtra("outputX", size);
		intent.putExtra("outputY", size);
		intent.putExtra("return-data", false);

		intent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(new File(outputImageName)));
		intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		fragment.startActivityForResult(intent, requestCode);
	}

	public static boolean takePhoto(final Activity activity, final String dir, final String filename, final int cmd) {
		filePath = dir + filename;

		final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		final File cameraDir = new File(dir);
		if (!cameraDir.exists()) {
			return false;
		}

		final File file = new File(filePath);
		final Uri outputFileUri = Uri.fromFile(file);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
		try {
			activity.startActivityForResult(intent, cmd);

		} catch (final ActivityNotFoundException e) {
			return false;
		}
		return true;
	}

	public static String getResultPhotoPath(Context context, final Intent intent, final String dir) {
		if (filePath != null && new File(filePath).exists()) {
			return filePath;
		}

		return resolvePhotoFromIntent(context, intent, dir);
	}

	public static String resolvePhotoFromIntent(final Context ctx, final Intent data, final String dir) {
		if (ctx == null || data == null || dir == null) {
			Log.e(TAG, "resolvePhotoFromIntent fail, invalid argument");
			return null;
		}

		String filePath = null;

		final Uri uri = Uri.parse(data.toURI());
		Cursor cu = ctx.getContentResolver().query(uri, null, null, null, null);
		if (cu != null && cu.getCount() > 0) {
			try {
				cu.moveToFirst();
				final int pathIndex = cu.getColumnIndex(MediaColumns.DATA);
				Log.e(TAG, "orition: " + cu.getString(cu.getColumnIndex(MediaStore.Images.ImageColumns.ORIENTATION)));
				filePath = cu.getString(pathIndex);
				Log.d(TAG, "photo from resolver, path:" + filePath);

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (data.getData() != null) {
			filePath = data.getData().getPath();
			if (!(new File(filePath)).exists()) {
				filePath = null;
			}
			Log.d(TAG, "photo file from data, path:" + filePath);

		} else if (data.getAction() != null && data.getAction().equals("inline-data")) {

			try {
				final String fileName = MD5.getMessageDigest(DateFormat.format("yyyy-MM-dd-HH-mm-ss", System.currentTimeMillis()).toString().getBytes()) + PHOTO_DEFAULT_EXT;
				filePath = dir + fileName;

				final Bitmap bitmap = (Bitmap) data.getExtras().get("data");
				final File file = new File(filePath);
				if (!file.exists()) {
					file.createNewFile();
				}

				BufferedOutputStream out;
				out = new BufferedOutputStream(new FileOutputStream(file));
				final int cQuality = 100;
				bitmap.compress(Bitmap.CompressFormat.PNG, cQuality, out);
				out.close();
				Log.d(TAG, "photo image from data, path:" + filePath);

			} catch (final Exception e) {
				e.printStackTrace();
			}

		} else {
			if (cu != null) {
				cu.close();
				cu = null;
			}
			Log.e(TAG, "resolve photo from intent failed");
			return null;
		}
		if (cu != null) {
			cu.close();
			cu = null;
		}
		return filePath;
	}


	/**
	 * 通过uri获取文件路径
	 *
	 * @param mUri
	 * @return
	 */
	public static String getFilePath(Context ctx, Uri mUri) {
		try {
			if (mUri.getScheme().equals("file")) {
				return mUri.getPath();
			} else {
				return getFilePathByUri(ctx, mUri);
			}
		} catch (FileNotFoundException ex) {
			return null;
		}
	}

	// 获取文件路径通过url
	private static  String getFilePathByUri(Context ctx, Uri mUri) throws FileNotFoundException {
		Cursor cursor = ctx.getContentResolver()
				.query(mUri, null, null, null, null);
		cursor.moveToFirst();
		return cursor.getString(1);
	}

}
