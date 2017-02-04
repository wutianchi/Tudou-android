package com.bentudou.westwinglife.config;


import android.app.Activity;

import com.bentudou.westwinglife.json.CartDataList;
import com.bentudou.westwinglife.json.HeadList;
import com.bentudou.westwinglife.json.OrderGoodsList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 常量
 */
public class Constant {

    public static final int IO_BUFFER_SIZE = 2*1024;//根据图片大小给的每次下载字节大小
    public static final String IMG_200 = ".w200";//根据图片大小给的每次下载字节大小
    public static final String IMG_400 = ".w400";//根据图片大小给的每次下载字节大小
    private static final long serialVersionUID = -328164355515242415L;
    public static boolean debugMode = false;
    public static final String URL_NEW_APP = "http://pre.im/gunlei";
    public static String numberPhone = "10690409482138";//发送验证码的手机号码
    //移动web服务地址
    public static final String URL_WEB_LINE = "http://m.gunlei.com/gunlei_h5";//线上分享页分享地址
    public static final String URL_WEB_DOAD = URL_WEB_LINE+"/manual_download.html";
    public static final String URL_YING_LINE = "http://a.app.qq.com/o/simple.jsp?pkgname=com.gunlei.dealer";//应用宝分享地址
    /**
     * 购物车集合
     */
    public static Map<String,String> map = new HashMap<>();
    public static Map<String,String> allmap = new HashMap<>();
    public static List<CartDataList> cartDataLists =new ArrayList<>();
    public static int live_img_num = 0;//直播第几张图片
    public static int push_value = 1;//首页标志
    public static int allselect = 1;//购物车全选标志
    public static String start_img_name = "";//保存的启动页名字
    public static String search_name = "";//保存的启动页名字
    public static List<OrderGoodsList> orderGoodsLists = new ArrayList<>();//订单详情商品列表数据
    public static Activity activity = null;//MainActivity

    //服务器端
//      public static /* final */ String URL_BASE_TEST = "http://cross.bentudou.com";// 线上服务器地址
    public static /* final */ String URL_BASE_TEST = "http://cross.bentudou.dev.costrun.cn";// 测试入口地址
//    public static /* final */ String URL_BASE_TEST = "http://192.168.20.35:8080/cross.bentudou.com";// 志鹏开发入口地址

    public static /* final */ String URL_BASE_IMG = "http://img.westwinglife.cn";// 图片地址

}
