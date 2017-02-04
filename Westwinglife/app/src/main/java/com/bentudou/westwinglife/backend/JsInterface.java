package com.bentudou.westwinglife.backend;

/**
 * @author lzz
 * @version v1.3.0
 *          Create date :2015/12/23
 *          Desc: js本地调用接口
 */
public interface JsInterface {
    String DNAME = "android";
    void getShareInfo(String title, String desc, String imgUrl);
}
