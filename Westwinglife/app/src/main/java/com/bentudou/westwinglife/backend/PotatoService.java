package com.bentudou.westwinglife.backend;

import com.bentudou.westwinglife.json.AddSub;
import com.bentudou.westwinglife.json.Address;
import com.bentudou.westwinglife.json.BtnToken;
import com.bentudou.westwinglife.json.CartData;
import com.bentudou.westwinglife.json.CheckSign;
import com.bentudou.westwinglife.json.City;
import com.bentudou.westwinglife.json.Citys;
import com.bentudou.westwinglife.json.Classify;
import com.bentudou.westwinglife.json.CommitBack;
import com.bentudou.westwinglife.json.Coupon;
import com.bentudou.westwinglife.json.Data;
import com.bentudou.westwinglife.json.Express;
import com.bentudou.westwinglife.json.GetCoupon;
import com.bentudou.westwinglife.json.GetCouponList;
import com.bentudou.westwinglife.json.Goods;
import com.bentudou.westwinglife.json.GoodsDetailSession;
import com.bentudou.westwinglife.json.GrowList;
import com.bentudou.westwinglife.json.HeadInfo;
import com.bentudou.westwinglife.json.HeadSession;
import com.bentudou.westwinglife.json.IndexGoods;
import com.bentudou.westwinglife.json.IsCollection;
import com.bentudou.westwinglife.json.Leaveword;
import com.bentudou.westwinglife.json.ImgUrl;
import com.bentudou.westwinglife.json.LastPayInfo;
import com.bentudou.westwinglife.json.MyOrder;
import com.bentudou.westwinglife.json.OneClassify;
import com.bentudou.westwinglife.json.OrderDetail;
import com.bentudou.westwinglife.json.OverseasLive;
import com.bentudou.westwinglife.json.PayInfo;
import com.bentudou.westwinglife.json.PayNumber;
import com.bentudou.westwinglife.json.RMBRate;
import com.bentudou.westwinglife.json.SearchGoods;
import com.bentudou.westwinglife.json.SearchKeySession;
import com.bentudou.westwinglife.json.Session;
import com.bentudou.westwinglife.json.SpecialSale;
import com.bentudou.westwinglife.json.StartImgInfo;
import com.bentudou.westwinglife.json.Success;
import com.bentudou.westwinglife.json.UnReadMessage;
import com.bentudou.westwinglife.json.UserCartNumber;
import com.bentudou.westwinglife.json.UserFavoite;
import com.bentudou.westwinglife.json.UserInfo;
import com.bentudou.westwinglife.json.UserMessage;
import com.bentudou.westwinglife.json.UserMessageDetail;
import com.bentudou.westwinglife.json.UserSign;
import com.bentudou.westwinglife.json.VersionInfo;
import com.squareup.okhttp.RequestBody;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.PartMap;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedFile;

/**
 * Created by lzz on 2016/3/9.
 */
public interface PotatoService {

    /*
    * 笨土豆接口
    * */

    /**
     * 获取session
     * @param data
     */
    @GET("/Login/getSessionId.htm")
    public void getSession(Callback<Session> data);

  /**
     * 获取session
     * @param data
     */
    @GET("/Index/getImgUrl.htm")
    public void getImgUrl(Callback<ImgUrl> data);

    /**
     * 接口1
     * 获取验证码
     * @param data
     */
    @GET("/Login/getCaptcha.htm")
    public void getIdentifyingcode(@Query("userName") String userName,
                                   @Query("flag") String flag,Callback<Session> data);

    /**
     * 接口2
     * 提交注册
     * @param data
     */
    @GET("/Login/createAccount.htm")
    public void commitRegist(@Query("userName") String userName, @Query("password") String password,
                             @Query("captcha") String captcha, @Query("flag") String flag,
                             @Query("sessionId") String sessionId,
                             @Query("invitationCode") String invitationCode, Callback<BtnToken> data);
    /**
     * 接口3
     * 用户登录
     * @param data
     */
    @GET("/Login/loginAccount.htm")
    public void userLogin(@Query("userName") String userName, @Query("password") String password,Callback<BtnToken> data);

    /**
     * 接口4
     * 忘记密码
     * @param data
     */
    @GET("/Login/forgotPassword.htm")
    public void forgetPassword(@Query("newPassword") String newPassword,
                               @Query("userName") String userName, @Query("captcha") String captcha,
                                @Query("sessionId") String sessionId, Callback<Success> data);
   /**
    * 接口5
     * 重置密码
     * @param data
     */
    @GET("/Login/editPassword.htm")
    public void changePassword(@Query("BtdToken") String BtdToken,
                               @Query("newPassword") String newPassword,
                               @Query("oldPassword") String oldPassword, Callback<Success> data);

    /**
     * 接口6
     * 首页接口
     * @param data
     */
    @GET("/Index/getNewIndexData.htm")
    public void readHomePageDatas(@Query("operationPositionShowGoodsNumber") int operationPositionShowGoodsNumber, Callback<HeadSession> data);

    /**
     * 接口7
     * 获取搜索关键字
     * @param data
     */
    @GET("/Search/getSearchKeyword.htm")
    public void getSearchKeyword( Callback<SearchKeySession> data);

    /**
     * 接口8
     * 获取搜索结果
     * @param data
     */
    @GET("/Search/searchGoodsPage.htm")
    public void getSearchGoodsList(@Query("keyWords") String keyWords, Callback<SearchGoods> data);

    /**
     * 接口9
     * 分类接口
     * @param data
     */
    @GET("/Category/findCategoryList.htm")
    public void readClassifyDatas( Callback<Classify> data);

    /**
     * 接口10
     * 商品列表接口
     * @param data
     */
    @GET("/Category/findGoodsListByCategoryId.htm")
    public void readGoodsList(@Query("categoryId") String categoryId, Callback<Goods> data);

    /**
     * 接口11
     * 商品详情接口
     * @param data
     */
    @GET("/Goods/getGoodsInfo.htm")
    public void readGoodsDetail(@Query("BtdToken") String BtdToken,
                                @Query("goodsId") String goodsId, Callback<GoodsDetailSession> data);

    /**
     * 接口12
     * 添加商品到购物车接口
     * @param data
     */
    @GET("/Cart/addUserCart.htm")
    public void addUserCart(@Query("BtdToken") String BtdToken,
                            @Query("goodsId") int goodsId,@Query("count") int count, Callback<AddSub> data);

    /**
     * 接口13
     * 叠加商品到购物车接口
     * @param data
     */

    @GET("/Cart/addUserCartTwo.htm")
    public void addUserCartTwo(@Query("BtdToken") String BtdToken,
                               @Query("goodsId") int goodsId,@Query("count") int count, Callback<AddSub> data);

    /**
     * 接口14
     * 获取购物车数据接口
     * @param data
     */
    @GET("/Cart/selectUserCart.htm")
    public void selectUserCart(@Query("BtdToken") String BtdToken,@Query("curType") String curType, Callback<CartData> data);

    /**
     * 接口15
     * 删除购物车商品
     * @param data
     */
    @GET("/Cart/deleteCartGoods.htm")
    public void deleteCartGoods(@Query("BtdToken") String BtdToken,
                                @Query("goodsId") int goodsId, Callback<Success> data);

     /**
      * 接口16
     * 购物车提交
     * @param data
     */
    @GET("/Order/confirmOrderInfo.htm")
    public void confirmOrderInfo(@Query("BtdToken") String BtdToken,
                                 @Query("goodsCountList") String goodsCountList,@Query("curType") String curType, Callback<OrderDetail> data);

     /**
      * 接口16s
     * 直播立即购买提交
     * @param data
     */
    @GET("/Order/confirmOrderInfo.htm")
    public void confirmOrderInfo(@Query("BtdToken") String BtdToken,
                                 @Query("goodsCountList") String goodsCountList,@Query("curType") String curType,@Query("isGoodsLive") boolean isGoodsLive, Callback<OrderDetail> data);

    /**
     * 接口17
     * 确认订单
     * @param data
     */
    @GET("/Order/createOrderInfo.htm")
    public void createOrderInfo(@Query("BtdToken") String BtdToken,@Query("goodsCountList") String goodsCountList,
                                @Query("userAddressId") int userAddressId,
                                @Query("isDel") boolean isDel,@Query("curType") String curType,
                                @Query("orderInfoRemark") String orderInfoRemark, Callback<OrderDetail> data);
  /**
     * 接口17s
     * 确认订单
     * @param data
     */
    @GET("/Order/createOrderInfo.htm")
    public void createOrderInfo(@Query("BtdToken") String BtdToken,@Query("goodsCountList") String goodsCountList,
                                @Query("userAddressId") int userAddressId,
                                @Query("isDel") boolean isDel,@Query("curType") String curType,
                                @Query("isGoodsLive") boolean isGoodsLive,
                                @Query("orderInfoRemark") String orderInfoRemark,Callback<OrderDetail> data);

     /**
      * 接口18
     * 查看订单列表接口
     * @param data
     */
    @GET("/Order/findOrderList.htm")
    public void findOrderList(@Query("BtdToken") String BtdToken,
                              @Query("Page") int Page,@Query("queryStatus") String queryStatus,Callback<MyOrder> data);

    /**
     * 接口19
     * 获取支付信息接口
     * @param data
     */
    @GET("/Order/gotoPayOrder.htm")
    public void gotoPayOrder(@Query("BtdToken") String BtdToken,
                             @Query("orderId") int orderId,@Query("curType") String curType,Callback<PayInfo> data);

    /**
     * 接口20
     * 使用优惠券接口
     * @param data
     */
    @GET("/Order/payOrder.htm")
    public void payOrder(@Query("BtdToken") String BtdToken,
                         @Query("orderId") int orderId,
                         @Query("userCouponsId") int userCouponsId,Callback<LastPayInfo> data);

      /**
       * 接口21
     * 不使用优惠券接口
     * @param data
     */
    @GET("/Order/payOrder.htm")
    public void payOrder(@Query("BtdToken") String BtdToken,
                         @Query("orderId") int orderId,Callback<LastPayInfo> data);

     /**
      * 接口22
     * 查看地址接口
     * @param data
     */
    @GET("/Address/shipAddressList.htm")
    public void shipAddressList(@Query("BtdToken") String BtdToken, Callback<Address> data);

    /**
     * 接口23
     * 删除地址接口
     * @param data
     */
    @GET("/Users/delUserAddress.htm")
    public void delUserAddress(@Query("BtdToken") String BtdToken,
                               @Query("userAddressId") int userAddressId, Callback<Success> data);

    /**
     * 接口24
     * 个人中心接口
     * @param data
     */
    @GET("/Users/getUserInfo.htm")
    public void getUserInfo(@Query("BtdToken") String BtdToken, Callback<UserInfo> data);

    /**
     * 接口25
     * 获取优惠券接口
     * @param data
     */
    @GET("/Users/getUserCoupon.htm")
    public void getUserCoupon(@Query("BtdToken") String BtdToken,
                              @Query("typeStatus") int typeStatus,@Query("curType") String curType, Callback<Coupon> data);

    /**
     * 接口26
     * 获取消息列表接口
     * @param data
     */
    @GET("/Message/selectUserMessageList.htm")
    public void selectUserMessageList(@Query("BtdToken") String BtdToken,
                                      @Query("page") int page,@Query("rows") int rows, Callback<UserMessage> data);

    /**
     * 接口27
     * 获取未读消息数量接口
     * @param data
     */
    @GET("/Message/getUnReadCount.htm")
    public void getUnReadCount(@Query("BtdToken") String BtdToken, Callback<UnReadMessage> data);

    /**
     * 接口28
     * 标记全读接口
     * @param data
     */
    @GET("/Message/markUserMessageAsRead.htm")
    public void markUserMessageAsRead(@Query("BtdToken") String BtdToken, Callback<Success> data);

    /**
     * 接口29
     * 查看消息详情
     * @param data
     */
    @GET("/Message/getUserMessageInfo.htm")
    public void getUserMessageInfo(@Query("BtdToken") String BtdToken,
                                   @Query("id") int id, Callback<UserMessageDetail> data);
    /**
     * 接口30
     * 查看push消息详情
     * @param data
     */
    @GET("/Message/getUserMessageInfo.htm")
    public void getUserMessageInfo(@Query("BtdToken") String BtdToken,
                                   @Query("id") int id,@Query("sysmsgId") int sysmsgId, Callback<UserMessageDetail> data);

    /**
     * 接口31
     * 删除消息接口
     * @param data
     */
    @GET("/Message/delUserMessage.htm")
    public void delUserMessage(@Query("BtdToken") String BtdToken,@Query("id") int id, Callback<Success> data);

    /**
     * 接口32
     * 获取收藏列表接口
     * @param data
     */
    @GET("/Users/findCollectPage.htm")
    public void findCollectPage(@Query("BtdToken") String BtdToken,
                                @Query("page") int page,@Query("rows") int rows, Callback<UserFavoite> data);

    /**
     * 接口33
     * 删除收藏接口
     * @param data
     */
    @GET("/Users/delCollect.htm")
    public void delCollect(@Query("BtdToken") String BtdToken,
                           @Query("goodsId") int goodsId, Callback<Success> data);

    /**
     * 接口34
     * 添加收藏接口
     * @param data
     */
    @GET("/Users/addCollect.htm")
    public void addCollect(@Query("BtdToken") String BtdToken,
                           @Query("goodsId") int goodsId, Callback<Success> data);

    /**
     * 接口35
     * 商品是否收藏
     * @param data
     */
    @GET("/Users/IsCollectByGoodsId.htm")
    public void IsCollectByGoodsId(@Query("BtdToken") String BtdToken,
                                   @Query("goodsId") int goodsId, Callback<IsCollection> data);

    /**
     * 接口38
     * 检查用户是否签到以及用户等级
     * @param data
     */
    @GET("/UserSign/checkUserSign.htm")
    public void checkUserSign(@Query("BtdToken") String BtdToken, Callback<CheckSign> data);

    /**
     * 接口39
     * 用户签到功能
     * @param data
     */
    @GET("/UserSign/createUserSign.htm")
    public void createUserSign(@Query("BtdToken") String BtdToken, Callback<UserSign> data);

    /**
     * 接口40
     * 获取用户成长列表
     * @param data
     */
    @GET("/UserSign/findUserGrowthLogList.htm")
    public void findUserGrowthLogList(@Query("BtdToken") String BtdToken, Callback<GrowList> data);

    /**
     * 接口41
     * 根据用户ID查询购物总数量
     * @param data
     */
    @GET("/Cart/getCartCount.htm")
    public void getCartCount(@Query("BtdToken") String BtdToken, Callback<UserCartNumber> data);

    /**
     * 接口42
     * 获取系统发放的所有优惠券
     * @param data
     */
    @GET("/Users/getCouponsListToIndex.htm")
    public void getCouponsListToIndex(@Query("BtdToken") String BtdToken, Callback<GetCouponList> data);

    /**
     * 接口43
     * 获取系统发放的所有优惠券
     * @param data
     */
    @GET("/Users/getUserCouponsListToIndex.htm")
    public void getUserCouponsListToIndex(@Query("BtdToken") String BtdToken,
                                          @Query("couponId") String couponId, Callback<GetCoupon> data);

    /**
     * 接口44
     * 获取系统发放的所有优惠券
     * @param data
     */
    @GET("/Order/cannlOrder.htm")
    public void cannlOrder(@Query("BtdToken") String BtdToken,@Query("orderId") int orderId, Callback<Success> data);

    /**
     * 接口45
     * 获取支付串
     * @param data
     */
    @GET("/Payment/globalAlipay.htm")
    public void globalAlipay(@Query("BtdToken") String BtdToken,@Query("orderId") int orderId, Callback<Success> data);
    /**
     * 接口46
     * 获取首页直播数据集合
     * @param data
     */
    @GET("/GoodsLive/getGoodsLiveList.htm")
    public void getGoodsLiveList(@Query("page") int page,@Query("rows") int rows, Callback<OverseasLive> data);
    /**
     * 接口47
     * 获取最新app版本号
     * @param data
     */
    @GET("/ProcedureStartData/getAppNewVersion.htm")
    public void getAppNewVersion(@Query("appType") int appType, Callback<VersionInfo> data);
    /**
     * 接口48
     * 获取最新启动页
     * @param data
     */
    @GET("/ProcedureStartData/getAppNewStartPage.htm")
    public void getAppNewStartPage(Callback<StartImgInfo> data);
     /**
     * 接口49
     * 获取物流状态
     * @param data
     */
    @GET("/ExpressMessage/getAppExpressMessageByExpressSn.htm")
    public void getExpressMessageByExpressSn(@Query("BtdToken") String BtdToken,
                                             @Query("expressSn") String expressSn,Callback<Express> data);
    /**
     * 接口50
     * 获取汇率
     * @param data
     */
    @GET("/Index/getBTDouExchangeRate.htm")
    public void getBTDouExchangeRate(Callback<RMBRate> data);
    /**
     * 接口51
     * 获取首页精选商品数据
     * @param data
     */
    @GET("/OperatingPosition/getGoodsOperationsList.htm?operationsType=05")
    public void getIndexSiftGoodsList(@Query("page") int page,@Query("rows") int rows, Callback<IndexGoods> data);
    /**
     * 接口52
     * 特卖会接口
     * @param data
     */
    @GET("/Index/getIndexGoodsDiscountList.htm")
    public void getIndexGoodsDiscountList(@Query("page") int page,@Query("rows") int rows, Callback<SpecialSale> data);
    /**
     * 接口52
     * 获取APP运营位商品列表集合
     * @param data
     */
    @GET("/OperatingPosition/getGoodsOperationsList.htm")
    public void getGoodsOperationsList(@Query("page") int page,@Query("rows") int rows,
                                       @Query("operationsType") String operationsType, Callback<SpecialSale> data);
    /**
     * 接口53
     * 获取所有的一级分类
     * @param data
     */
    @GET("/Category/getFirstCategoryList.htm")
    public void getFirstCategoryList( Callback<OneClassify> data);
     /**
     * 接口54
     * 获取所有的一级分类
     * @param data
     */
    @GET("/Category/findCategoryListByFirstCategoryId.htm")
    public void findCategoryListByFirstCategoryId(@Query("categoryId") int categoryId, Callback<OneClassify> data);
    /**
     * 接口55
     * 获取待付款订单数量
     * @param data
     */
    @GET("/Order/findOrderNumberUnpaid.htm")
    public void findOrderNumberUnpaid(@Query("BtdToken") String BtdToken, Callback<PayNumber> data);
    /**
     * 接口55
     * 清除购物车失效商品接口
     * @param data
     */
    @GET("/Cart/cleanDisabledCartGoods.htm")
    public void cleanDisabledCartGoods(@Query("BtdToken") String BtdToken, Callback<Success> data);
 /**
     * 接口55
     * 清除收藏夹失效商品接口
     * @param data
     */
    @GET("/Users/cleanDisabledUserCollectGoods.htm")
    public void cleanDisabledUserCollectGoods(@Query("BtdToken") String BtdToken, Callback<Success> data);
    /**
     * 接口56
     *设置默认地址接口
     * @param data
     */
    @GET("/Address/updateAddressIsDefault.htm")
    public void updateAddressIsDefault(@Query("BtdToken") String BtdToken,
                                       @Query("userAddressId") int userAddressId,
                                       @Query("isDefaultAddress") int isDefaultAddress, Callback<Success> data);

//   /**
//    * 上传一张图片
//    * @param BtdToken
//    * @param imgs
//    * @param callback
//    * @return
//    */
//   @Multipart
//   @POST("/UploadImage/uploadIDImage.htm")
//   public void uploadIDImage(@Part("BtdToken") String BtdToken, @Part("iDImg\"; filename=\"image.png\"") RequestBody imgs, Callback<Success> callback);
 /**
    * 上传一张图片
    * @param params
    * @param params
    * @param callback
    * @return
    */
   @Multipart
   @POST("/UploadImage/uploadIDImage.htm")
   public void uploadIDImage(@PartMap Map<String,RequestBody> params, Callback<Success> callback);

 /**
     * POST接口示例
     * @param mobile
     * @param callback
     */

    @POST("/dealer/login")
    public void login(@Body Data mobile, Callback<Data> callback);

    /**
     * PUT接口示例
     * @param form
     * @param data
     */
    @PUT("/order")
    void insertOrder(@Body Data form, Callback<String> data);

    /**
     * DELETE接口示例
     * @param data
     */
    @DELETE("/dealer/favorite/{id}")
    public void cancelFavorite(@Path("id") String id, Callback<String> data);

/*
* 获取省市区接口
* @param data
* */
    @GET("/Address/getInternationalAdress.htm")
    public void gainCity(@Query("internationalId") int internationalId, Callback<City> data);
    /*
* 保存收货信息接口
* @param data
* */
    @GET("/Users/updateUserAddress.htm")
    public void keepAddress(@Query("BtdToken") String BtdToken, @Query("consignee") String consignee, @Query("address") String address, @Query("country") int country, @Query("province") int province, @Query("city") int city, @Query("district") int district, @Query("tel") String tel, @Query("idCard") String idCard, Callback<Citys> data);
    /*
* 修改收货信息接口
* @param data
* */
    @GET("/Users/updateUserAddress.htm")
    public void alterAddress(@Query("BtdToken") String BtdToken, @Query("consignee") String consignee, @Query("address") String address, @Query("country") int country, @Query("province") int province, @Query("city") int city, @Query("district") int district, @Query("tel") String tel, @Query("idCard") String idCard, @Query("userAddressId") int userAddressId, Callback<Citys> data);
    /*
     * GET保存留言
     *@param data
     **/
    @GET("/Collect/addGoodsCollect.htm")
    public void leaveword(@Query("goodsCollectUserName") String goodsCollectUserName, @Query("goodsCollectPhoneNumber") String goodsCollectPhoneNumber, @Query("goodsCollectGoodsUrl") String goodsCollectGoodsUrl, @Query("goodsCollectNumber") int  goodsCollectNumber, @Query("goodsCollectDec") String goodsCollectDec, Callback<Leaveword> callback);
}
