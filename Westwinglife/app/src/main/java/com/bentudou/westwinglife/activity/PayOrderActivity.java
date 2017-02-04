package com.bentudou.westwinglife.activity;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.alipay.PayResult;
import com.bentudou.westwinglife.alipay.PayUtils;
import com.bentudou.westwinglife.utils.ToastUtils;
import com.gunlei.app.ui.base.BaseTitleActivity;

/**
 * Created by lzz on 2016/6/20.
 */
public class PayOrderActivity extends BaseTitleActivity implements View.OnClickListener {
    private TextView tv_go_pay;
    private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_CHECK_FLAG = 2;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();
                    String resultStatus = payResult.getResultStatus();
                    Log.d("resultStatus", "handleMessage:----- "+resultStatus);
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        ToastUtils.showToastCenter(PayOrderActivity.this, "支付成功");
//                        uplodeData();
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            ToastUtils.showToastCenter(PayOrderActivity.this, "支付结果确认中");

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            ToastUtils.showToastCenter(PayOrderActivity.this, "支付失败");

                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    ToastUtils.showToastCenter(PayOrderActivity.this, "检查结果为：" + msg.obj);
                    break;
                }
                default:
                    break;
            }
        };
    };
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_pay_order);
    }

    @Override
    protected void initView() {
        tv_go_pay = (TextView) findViewById(R.id.tv_go_pay);
        tv_go_pay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_go_pay:
//                PayUtils.pay(PayOrderActivity.this, mHandler, "0.01", "20151230");
                break;
        }
    }
}
