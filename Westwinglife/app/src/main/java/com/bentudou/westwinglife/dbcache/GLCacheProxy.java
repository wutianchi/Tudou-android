package com.bentudou.westwinglife.dbcache;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Ben
 * @version v1.2.1
 * Create date :2015/12/16
 * Desc: 本地缓存代理类
 */
public class GLCacheProxy implements InvocationHandler{

    private Object target;
    private static final String TAG = "GLCacheProxy";

    public GLCacheProxy(Object target){
        this.target = target;
    }

    public <T> T getProxy(){
        return (T) Proxy.newProxyInstance(
            target.getClass().getClassLoader(),
            target.getClass().getInterfaces(),
            this
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{

        Object result = null;
        Log.d(TAG, String.format("方法[%s]开始进入",method.getName()));
        for (Object obj: args) {
            Log.d(TAG, String.format("参数值依次为：%s",obj.toString()));
        }
        try {
            result = method.invoke(target, args);
        }catch (RuntimeException e){
            Log.e(TAG, String.format("方法[%s]执行异常", method.getName()));
            e.printStackTrace();
        }
        Log.d(TAG, String.format("方法[%s]执行完成",method.getName()));
        return result;
    }
}
