package com.bentudou.westwinglife.backend;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.bentudou.westwinglife.utils.ToastUtils;
import com.gunlei.app.ui.view.ProgressHUD;
import com.bentudou.westwinglife.activity.LoginActivity;
import com.bentudou.westwinglife.activity.RegistActivity;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit.RetrofitError;

/**
 * 处理请求响应失败
 * Created by Ben on 2015/8/20.
 */
public abstract class CallbackSupport<T> implements retrofit.Callback<T>{


    /**
     * 用于标识
     */
    protected Integer tag;
    /**
     * 进度条对象
     */
    protected ProgressHUD progressHUD = null;

    /**
     * View控件
     */
    protected View view;

    /**
     *上下文
     */
    protected Context context;

    /**
     * 响应体JSONObject
     */
    protected JSONObject data = null;

    /**
     * http请求状态码
     */
    protected int statusCode = 0;

    /**
     * 服务器返回的错误信息
     */
    protected String message = null;

    /**
     * 参数
     */
    protected String arg = null;

    /**
     * 状态码
     */
    protected String status = null;
    /**
     * 错误码
     */
    protected String errorcode = null;

    /**
     * http响应头
     */
    protected String header = null;

    /**
     * <title>构造器</title>
     * <br>
     * @param context
     */
    public CallbackSupport(Context context){
        this.context = context;
    }

    /**
     * <title>构造器</title>
     * <br>如果progressHUD不为空的话就会关闭进度条
     * @param progressHUD
     * @param context
     */
    public CallbackSupport(ProgressHUD progressHUD, Context context){
        this.progressHUD = progressHUD;
        this.context = context;
    }

    /**
     * <title>构造器</title>
     * <br>如果progressHUD不为空的话就会关闭进度条
     * @param progressHUD 进度条
     * @param context 上下文
     * @param tag 来源标识
     */
    public CallbackSupport(ProgressHUD progressHUD, Context context, Integer tag){
        this.progressHUD = progressHUD;
        this.context = context;
        this.tag = tag;
    }


    /**
     *
     * @param progressHUD
     * @param context
     * @param tag
     * @param arg
     */
    public CallbackSupport(ProgressHUD progressHUD, Context context, Integer tag, String arg){
        this.progressHUD = progressHUD;
        this.context = context;
        this.tag = tag;
        this.arg = arg;
    }

    /**
     *
     * @param progressHUD
     * @param context
     * @param view 重新加载按钮控件
     */
    public CallbackSupport(ProgressHUD progressHUD, Context context, View view){
        this.progressHUD = progressHUD;
        this.context = context;
        this.view = view;
    }

    @Override
    public void failure (RetrofitError error) {

        if (progressHUD != null)
            progressHUD.dismiss();
        displayMessage();

    }

    //Toast提示错误信息
    private void displayMessage() {
        ToastUtils.showToastCenter(context,"服务器离家出走了,请稍后再试");
    }
}
