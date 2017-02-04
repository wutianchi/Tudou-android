package com.bentudou.westwinglife.json;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by lzz on 2016/6/29.
 */
public class MyOrderList implements Serializable {
    private int orderId;//订单id
    private String orderSn;//订单编码
    private String currency;//货币
    private int orderStatus;//订单状态（0.已取消/1.已确认/2.申请退款/3.订单异常/4.关闭状态/5.已退货）
    private int orderPayStatus;//支付状态（0.未付款/1.已付款）
    private int orderDeliveryStatus;//配送状态(0.未发货/1.已发货/2.已收货)
    private String orderAddTime;//订单新增时间
    private BigDecimal orderTariffsFee;//订单税费
    private BigDecimal orderInvoiceFee;//订单运费
    private BigDecimal couponPrice;//订单优惠金额
    private BigDecimal goodsCountFee;//订单商品总金额
    private BigDecimal orderPayFee;//支付总金额
    private BigDecimal orderAmountFee;//订单总金额
    private List<OrderGoodsList> orderGoodsList;//订单商品列表
    private String customsUserName;//收货人姓名
    private String customsMobile;//收货人电话
    private String customsIdCard;//收货人身份证
    private String shippingProvinceCn;//省份
    private String shippingCityCn;//城市
    private String shippingAddress;//地址
    private String expressSn;//运单号

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCustomsUserName() {
        return customsUserName;
    }

    public void setCustomsUserName(String customsUserName) {
        this.customsUserName = customsUserName;
    }

    public String getCustomsMobile() {
        return customsMobile;
    }

    public void setCustomsMobile(String customsMobile) {
        this.customsMobile = customsMobile;
    }

    public String getCustomsIdCard() {
        return customsIdCard;
    }

    public void setCustomsIdCard(String customsIdCard) {
        this.customsIdCard = customsIdCard;
    }

    public String getShippingProvinceCn() {
        return shippingProvinceCn;
    }

    public void setShippingProvinceCn(String shippingProvinceCn) {
        this.shippingProvinceCn = shippingProvinceCn;
    }

    public String getShippingCityCn() {
        return shippingCityCn;
    }

    public void setShippingCityCn(String shippingCityCn) {
        this.shippingCityCn = shippingCityCn;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getExpressSn() {
        return expressSn;
    }

    public void setExpressSn(String expressSn) {
        this.expressSn = expressSn;
    }

    public BigDecimal getOrderPayFee() {
        return orderPayFee;
    }

    public void setOrderPayFee(BigDecimal orderPayFee) {
        this.orderPayFee = orderPayFee;
    }

    public BigDecimal getOrderTariffsFee() {
        return orderTariffsFee;
    }

    public void setOrderTariffsFee(BigDecimal orderTariffsFee) {
        this.orderTariffsFee = orderTariffsFee;
    }

    public BigDecimal getOrderInvoiceFee() {
        return orderInvoiceFee;
    }

    public void setOrderInvoiceFee(BigDecimal orderInvoiceFee) {
        this.orderInvoiceFee = orderInvoiceFee;
    }

    public BigDecimal getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(BigDecimal couponPrice) {
        this.couponPrice = couponPrice;
    }

    public BigDecimal getGoodsCountFee() {
        return goodsCountFee;
    }

    public void setGoodsCountFee(BigDecimal goodsCountFee) {
        this.goodsCountFee = goodsCountFee;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getOrderPayStatus() {
        return orderPayStatus;
    }

    public void setOrderPayStatus(int orderPayStatus) {
        this.orderPayStatus = orderPayStatus;
    }

    public int getOrderDeliveryStatus() {
        return orderDeliveryStatus;
    }

    public void setOrderDeliveryStatus(int orderDeliveryStatus) {
        this.orderDeliveryStatus = orderDeliveryStatus;
    }

    public String getOrderAddTime() {
        return orderAddTime;
    }

    public void setOrderAddTime(String orderAddTime) {
        this.orderAddTime = orderAddTime;
    }

    public BigDecimal getOrderAmountFee() {
        return orderAmountFee;
    }

    public void setOrderAmountFee(BigDecimal orderAmountFee) {
        this.orderAmountFee = orderAmountFee;
    }

    public List<OrderGoodsList> getOrderGoodsList() {
        return orderGoodsList;
    }

    public void setOrderGoodsList(List<OrderGoodsList> orderGoodsList) {
        this.orderGoodsList = orderGoodsList;
    }
}
