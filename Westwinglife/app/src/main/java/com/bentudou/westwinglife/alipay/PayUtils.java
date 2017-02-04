package com.bentudou.westwinglife.alipay;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.bentudou.westwinglife.config.Constant;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by 天池 on 2015/12/30.
 */
public class PayUtils {
    // 商户PID
    public static final String PARTNER = "2088021762521925";
    // 商户收款账号
    public static final String SELLER = "finance@bentudou.com";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMWgrfU39cAR3wn8DLG1oj0KzozrNu7jlkWlC0z9fjUFvyAw5LNOKbsGsm3mHQD2CwIG6KjG+CRHLHur0rHo9AZJRMKNoPtUtFcsbN9lmzLzSgIjRWOkpB3Kd+FL/qJpXUsH0krNznZzRm7Lh+BQUZxJDzXbmnNPWSrl2+ydCrtrAgMBAAECgYEAjEKy0/RIfW3xn1N86u9fWvV9jPQvg7KQ55Lwh07uMNGISV+KIVwbL/rbzjXgZds6CtE+dPLqEE1XW9qh6dTMwxwtvq0ItbwvjtYjrKL8Eqykr7wmraAS0VrQg3lGRDSDBiVKkz+jgvdu/57ib16SKg0edUGMOYcdn5qtjkAqcqECQQD7EMNXBYwGa/5EBHV1dgmq2Icmve23AToNY8KJaphH11/2or3gNAmmTxWJBlXZIIlPaBGsmlXC4zuFcTzC2gqRAkEAyYMLOVGE/zq4PGj+MmKsjRL7olA/VOFd517tPkMPGLzcAyysrbi0K1GVc02ALcuKmXyuw8LTA4p3RpYbalOMOwJBANzDWbC8XOsMrNDsRshZWZjTusuTOGKx/nTlopqok2ygcm5xnZp2x4FoitwpKPW6iWK8dMYcE4tBugXVk4MWrTECQHS4xCKSVpAkKh1Lz5nN0BbzHPB6vQRkeikPsinzW+Y/Vs077lW9/BZvbjrYdFuSX5jiQLTBv8p4RPiM8BpZhIsCQEJ8mJfI5mpmZmR58/RNVuw3vozwfEj7kVwQ3smu2GaBs6OVQX4gehux0JhD94jlwngeE8J+YWoVTCDEnI113EA=";
    // 支付宝公钥
    //支付
  private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_CHECK_FLAG = 2;

    private String eMoney,orderNumber;

    //支付
    /**
     * call alipay sdk pay. 调用SDK支付
     *
     */
    public static void pay(final Activity activity, final Handler mHandler, final String eMoney) {
//        // 订单
//        String orderInfo = getOrderInfo("[笨土豆]订单编号20161206114969", "[笨土豆]订单编号20161206114969", "53.0","201612061149691212");
//
//        // 对订单做RSA 签名
//        String sign = sign(orderInfo);
//        try {
//            // 仅需对sign 做URL编码
//            sign = URLEncoder.encode(sign, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
////
////        // 完整的符合支付宝参数规范的订单信息
//        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
//                + getSignType();
        Log.d("payInfo", "pay:------ "+eMoney);
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(activity);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(eMoney,true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * check whether the device has authentication alipay account.
     * 查询终端设备是否存在支付宝认证账户
     *
     */
//    public void check(View v) {
//        Runnable checkRunnable = new Runnable() {
//
//            @Override
//            public void run() {
//                // 构造PayTask 对象
//                PayTask payTask = new PayTask(activity);
//                // 调用查询接口，获取查询结果
//                boolean isExist = payTask.checkAccountIfExist();
//
//                Message msg = new Message();
//                msg.what = SDK_CHECK_FLAG;
//                msg.obj = isExist;
//                mHandler.sendMessage(msg);
//            }
//        };
//
//        Thread checkThread = new Thread(checkRunnable);
//        checkThread.start();
//
//    }

    /**
     * get the sdk version. 获取SDK版本号
     *
     */
//    public void getSDKVersion() {
//        PayTask payTask = new PayTask(activity);
//        String version = payTask.getVersion();
//    }

    /**
     * create the order info. 创建订单信息
     *
     */
    public static String getOrderInfo(String subject, String body,String price,String orderNumber) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo(orderNumber) + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + Constant.URL_BASE_TEST+"/Payment/globalAlipayNotify.htm"
                + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\""

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     *
     */
    public static String getOutTradeNo(String orderNumber) {
//        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
//                Locale.getDefault());
//        Date date = new Date();
//        String key = format.format(date);
//
//        Random r = new Random();
//        key = key + r.nextInt();
//        key = key.substring(0, 15);
        return orderNumber;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content
     *            待签名订单信息
     */
    public static String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     *
     */
    public static String getSignType() {
        return "sign_type=\"RSA\"";
    }

}
