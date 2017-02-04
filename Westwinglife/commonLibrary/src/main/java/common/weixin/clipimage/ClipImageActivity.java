package common.weixin.clipimage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.gunlei.app.R;
import com.gunlei.app.ui.base.BaseTitleActivity;
import com.gunlei.app.ui.util.DeviceUtil;
import com.gunlei.app.ui.util.Logger;
import com.gunlei.app.ui.util.ViewFinderUtil;
import com.gunlei.app.ui.util.photo.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 裁剪图片的Activity
 * 
 * @ClassName: CropImageActivity
 * @Description:
 * @author xiechengfa2000@163.com
 * @date 2015-5-8 下午3:39:22
 */
public class ClipImageActivity extends BaseTitleActivity implements OnClickListener {
	private static final String TAG = Logger.getTag(ClipImageActivity.class);

	public static final String RESULT_PATH = "crop_image";
	public static final String KEY = "path";
	public static final String MAX_WIDTH = "MAX_WIDTH";
	public static final String MAX_HEIGHT = "MAX_HEIGHT";
	private ClipImageLayout mClipImageLayout = null;
	private RelativeLayout rootLayout;

	private int max_width = 200;// 最大图片剪切后宽度
	private int max_height = 200;

	// 此方法只适用于Activity, 不适用于Fragment
	public static void startActivity(Activity activity, String path, int code, int maxImageWidth, int maxImageHeight) {
		Intent intent = new Intent(activity, ClipImageActivity.class);
		intent.putExtra(KEY, path);
		intent.putExtra(MAX_WIDTH, maxImageWidth);
		intent.putExtra(MAX_HEIGHT, maxImageHeight);
		activity.startActivityForResult(intent, code);
	}

	public static void startActivityFromFragment(Fragment fragment, String path, int code, int maxImageWidth, int maxImageHeight) {
		Intent intent = new Intent(fragment.getActivity(), ClipImageActivity.class);
		intent.putExtra(KEY, path);
		intent.putExtra(MAX_WIDTH, maxImageWidth);
		intent.putExtra(MAX_HEIGHT, maxImageHeight);
		fragment.startActivityForResult(intent, code);// Fragment 必须如此启动 2015-06-25 否则会无法拿到结果数据
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.weixin_crop_image_layout);
	}

	@Override
	protected void initView() {
		mClipImageLayout = (ClipImageLayout) findViewById(R.id.clipImageLayout);
		rootLayout = ViewFinderUtil.findViewById(this, R.id.rootLayout);
		String path = getIntent().getStringExtra(KEY);
		max_width = getIntent().getIntExtra(MAX_WIDTH, 200);
		max_height = getIntent().getIntExtra(MAX_HEIGHT, 200);

		Logger.d(TAG, path);

		// 有的系统返回的图片是旋转了，有的没有旋转，所以处理
		int degreee = readBitmapDegree(path);
		Bitmap bitmap = createBitmap(path);
		if (bitmap != null) {
			if (degreee == 0) {
				mClipImageLayout.setImageBitmap(bitmap);
			} else {
				mClipImageLayout.setImageBitmap(rotateBitmap(degreee, bitmap));
			}
		} else {
			finish();
		}
		findViewById(R.id.okBtn).setOnClickListener(this);
		findViewById(R.id.cancleBtn).setOnClickListener(this);
	}

	@Override
	protected void onNextClick() {
		Logger.d(TAG, "onNextClick");
		finishClip();
		finish();
	}

	@Override
	public void initTitle() {
		super.initTitle();
		setTitleText("剪裁照片");
		super.setNextText("使用");
		super.title_next.setEnabled(true);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.okBtn) {
			finishClip();
		}
		finish();
		Log.e("ClipImageActivity.OK", "finish()");
	}

	private void finishClip() {
		Bitmap bitmap = mClipImageLayout.clip();

		String path = Environment.getExternalStorageDirectory() + "/"
                + "head_tmp.jpg";
		saveBitmap(bitmap, path);

		saveBitmap(Util.extractThumbNail(path, max_width, max_height, true), path);// 剪切大图

		Intent intent = new Intent();
		intent.putExtra(RESULT_PATH, path);
		setResult(RESULT_OK, intent);
	}

	private void saveBitmap(Bitmap bitmap, String path) {
		File f = new File(path);
		if (f.exists()) {
			f.delete();
		}

		FileOutputStream fOut = null;
		try {
			f.createNewFile();
			fOut = new FileOutputStream(f);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
			fOut.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (fOut != null)
					fOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 创建缩略图片
	 * 
	 * @param path
	 * @return
	 */
	private Bitmap createBitmap(String path) {
		if (path == null) {
			return null;
		}

		DisplayMetrics screenSize = DeviceUtil.getDisplayMetrics(this);
		int scrWidth = screenSize.widthPixels;
		int scrHeight = screenSize.heightPixels;
		if(scrWidth > 0 && scrHeight > 0) {
			return Util.extractThumbNail(path, scrWidth , scrHeight, false);// 屏幕大小
		}
		return Util.extractThumbNail(path, 1280 , 800, false);// 失败时1280x800
	}

	// 读取图像的旋转度
	private int readBitmapDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	// 旋转图片
	private Bitmap rotateBitmap(int angle, Bitmap bitmap) {
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, false);
		return resizedBitmap;
	}
}
