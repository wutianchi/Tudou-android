package com.bentudou.westwinglife.utils;

import com.bentudou.westwinglife.config.Constant;

import java.util.Map;

/**
 * Created by lzz on 2016/7/15.
 */
public class PriceUtils {
    public static String getPrice(Map<String, String> map) {
        double price=0;
        for (int i = 0; i< Constant.cartDataLists.size(); i++){
            for (int j=0;j<Constant.cartDataLists.get(i).getGoodsList().size();j++){
                if (null!=map.get(""+Constant.cartDataLists.get(i).getGoodsList().get(j).getGoodsId())){
                    price+=Integer.valueOf(map.get(""+Constant.cartDataLists.get(i).getGoodsList().get(j).getGoodsId()))*Constant.cartDataLists.get(i).getGoodsList().get(j).getShopPriceCny().doubleValue()+
                            Integer.valueOf(map.get(""+Constant.cartDataLists.get(i).getGoodsList().get(j).getGoodsId()))*
                                    Constant.cartDataLists.get(i).getGoodsList().get(j).getShopPriceCny().doubleValue()*Constant.cartDataLists.get(i).getGoodsList().get(j).getCustomsDuties().doubleValue();
                }
            }
        }
        return VerifitionUtil.getDoubleRMBStringPrice(price);
    }
    public static String getCostomPrice(Map<String, String> map) {
        double price=0;
        for (int i = 0; i< Constant.cartDataLists.size(); i++){
            for (int j=0;j<Constant.cartDataLists.get(i).getGoodsList().size();j++){
                if (null!=map.get(""+Constant.cartDataLists.get(i).getGoodsList().get(j).getGoodsId())){
                    price+=Integer.valueOf(map.get(""+Constant.cartDataLists.get(i).getGoodsList().get(j).getGoodsId()))*Constant.cartDataLists.get(i).getGoodsList().get(j).getShopPriceCny().doubleValue()+
                            Integer.valueOf(map.get(""+Constant.cartDataLists.get(i).getGoodsList().get(j).getGoodsId()))*
                                    Constant.cartDataLists.get(i).getGoodsList().get(j).getShopPriceCny().doubleValue()*Constant.cartDataLists.get(i).getGoodsList().get(j).getCustomsDuties().doubleValue();
                }
            }
        }
        return VerifitionUtil.getDoubleRMBStringPrice(price);
    }


}
