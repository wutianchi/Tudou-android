package com.bentudou.westwinglife.json;

import java.util.List;

/**
 * Created by lzz on 2016/9/28.
 */
public class SpecialSaleData {
    private boolean isLastPage;
    private List<HeadList> goodsOperationsList;

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public List<HeadList> getGoodsOperationsList() {
        return goodsOperationsList;
    }

    public void setGoodsOperationsList(List<HeadList> goodsOperationsList) {
        this.goodsOperationsList = goodsOperationsList;
    }
}
