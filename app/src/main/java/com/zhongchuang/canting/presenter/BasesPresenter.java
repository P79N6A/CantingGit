package com.zhongchuang.canting.presenter;


import android.content.Intent;

import com.zhongchuang.canting.app.CanTingAppLication;
import com.zhongchuang.canting.been.AddressBase;
import com.zhongchuang.canting.been.Banner;
import com.zhongchuang.canting.been.BaseBe;
import com.zhongchuang.canting.been.BaseBean;
import com.zhongchuang.canting.been.BaseResponse;
import com.zhongchuang.canting.been.CancelParam;
import com.zhongchuang.canting.been.Care;
import com.zhongchuang.canting.been.Cares;
import com.zhongchuang.canting.been.Catage;
import com.zhongchuang.canting.been.Codes;
import com.zhongchuang.canting.been.Favor;
import com.zhongchuang.canting.been.FriendInfo;
import com.zhongchuang.canting.been.FriendListBean;
import com.zhongchuang.canting.been.GAME;
import com.zhongchuang.canting.been.GrapRed;
import com.zhongchuang.canting.been.GrapRedDetail;
import com.zhongchuang.canting.been.Hand;
import com.zhongchuang.canting.been.Hands;
import com.zhongchuang.canting.been.Home;
import com.zhongchuang.canting.been.Host;
import com.zhongchuang.canting.been.INTEGRALIST;
import com.zhongchuang.canting.been.Ingegebean;
import com.zhongchuang.canting.been.MessageGroup;
import com.zhongchuang.canting.been.OrderData;
import com.zhongchuang.canting.been.OrderParam;
import com.zhongchuang.canting.been.OrderType;
import com.zhongchuang.canting.been.Param;
import com.zhongchuang.canting.been.Params;
import com.zhongchuang.canting.been.Product;
import com.zhongchuang.canting.been.ProductBuy;
import com.zhongchuang.canting.been.ProductDel;
import com.zhongchuang.canting.been.QfriendBean;
import com.zhongchuang.canting.been.RedInfo;
import com.zhongchuang.canting.been.ShopBean;
import com.zhongchuang.canting.been.TabEntity;
import com.zhongchuang.canting.been.Version;
import com.zhongchuang.canting.been.WEIXINREQ;
import com.zhongchuang.canting.been.apply;
import com.zhongchuang.canting.been.pay.alipay;
import com.zhongchuang.canting.hud.ToastUtils;
import com.zhongchuang.canting.net.BaseCallBack;
import com.zhongchuang.canting.net.HttpUtil;
import com.zhongchuang.canting.net.netService;
import com.zhongchuang.canting.utils.SpUtil;
import com.zhongchuang.canting.utils.TextUtil;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import rx.Subscription;


public class BasesPresenter implements BaseContract.Presenter {
    private Subscription subscription;

    private BaseContract.View mView;


    protected netService api;

    public BasesPresenter(BaseContract.View view) {
        mView = view;

        api = HttpUtil.getInstance().create(netService.class);
    }

    @Override
    public void getHomeBanner(String type) {
        Map<String, String> params = new TreeMap<>();
        params.put("imageSite", type);
        params.put("bannerSite", 0+ "");
        api.getBanner(params).enqueue(new BaseCallBack<Home>() {

            @Override
            public void onSuccess(Home userLoginBean) {
                mView.toEntity(userLoginBean.data, 6);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }
    @Override
    public void setLanguge(String languge) {
        Map<String, String> params = new TreeMap<>();
        params.put("userInfoId", SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        params.put("languge", languge);
        api.setLanguge(params).enqueue(new BaseCallBack<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse userLoginBean) {
                mView.toEntity(userLoginBean, 111);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void redGrab(String redEnvelopeId	,String sendType) {


        Map<String, String> map = new HashMap<>();
        map.put("userInfoId", SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        map.put("redEnvelopeId", redEnvelopeId);
        map.put("sendType", sendType);

        api.redGrab(map).enqueue(new BaseCallBack<GrapRed>() {

            @Override
            public void onSuccess(GrapRed userLoginBean) {
                mView.toEntity(userLoginBean.data, 19);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void getDetails(String startTime	,String pageNum	,String type,final  int state) {


        Map<String, String> map = new HashMap<>();
        map.put("userInfoId", SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        map.put("pageNum", pageNum);
        map.put("startTime", startTime);
        map.put("pageSize", "12");
        map.put("type", type);

        api.getDetails(map).enqueue(new BaseCallBack<GrapRedDetail>() {

            @Override
            public void onSuccess(GrapRedDetail userLoginBean) {
                mView.toEntity(userLoginBean.data, state);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void getLuckInfo(String redEnvelopeId) {


        Map<String, String> map = new HashMap<>();
        map.put("userInfoId", SpUtil.getUserInfoId(CanTingAppLication.getInstance()));

        map.put("redEnvelopeId", redEnvelopeId);


        api.getLuckInfo(map).enqueue(new BaseCallBack<RedInfo>() {

            @Override
            public void onSuccess(RedInfo userLoginBean) {
                mView.toEntity(userLoginBean.data, 69);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }
    @Override
    public void deposit(String number,String type) {
        Map<String, String> params = new TreeMap<>();
        params.put("number", number);
        params.put("type", type+ "");
        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));

        api.deposit(params).enqueue(new BaseCallBack<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse userLoginBean) {
                mView.toEntity(userLoginBean, 6);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }


    @Override
    public void getHomeBanners(String type) {
        Map<String, String> params = new TreeMap<>();
        params.put("imageSite", type);
        params.put("bannerSite", 3+ "");
        api.getBanner(params).enqueue(new BaseCallBack<Home>() {

            @Override
            public void onSuccess(Home userLoginBean) {
                mView.toEntity(userLoginBean.data, 66);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }
    public void addChatDetail(String friendId,String chatType,String stringStartTime,String stringEndTime) {
        Map<String, String> map = new TreeMap<>();
        map.put("userInfoId", CanTingAppLication.userId);
        map.put("friendId", friendId);
        map.put("chatType", chatType);
        map.put("stringStartTime", stringStartTime+"");
        map.put("stringEndTime",stringEndTime+"");
        api.addChatDetail(map).enqueue(new BaseCallBack<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse userLoginBean) {
                mView.toEntity(userLoginBean, 6);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });

    }

    @Override
    public void integralDetails(final int type, String pageNum,  String integralSite, String types, String stringStartTime, String stringEndTime,String pageSize) {
        Map<String, String> params = new TreeMap<>();
        params.put("pageNum", pageNum);
        if(TextUtil.isEmpty(pageSize)){
            params.put("pageSize", "50");
        }else {
            params.put("pageSize", pageSize);
        }

        params.put("integralSite", integralSite);
        params.put("type", types);
        if(TextUtil.isNotEmpty(stringStartTime)){
            params.put("stringStartTime", stringStartTime);
        }if(TextUtil.isNotEmpty(stringEndTime)){
            params.put("stringEndTime", stringEndTime);
        }


        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        api.integralDetails(params).enqueue(new BaseCallBack<INTEGRALIST>() {

            @Override
            public void onSuccess(INTEGRALIST userLoginBean) {
                mView.toEntity(userLoginBean.data, type);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void getFriendById( String Ids) {
        Map<String, String> params = new TreeMap<>();
        if(!TextUtil.isEmpty(Ids)){
            params.put("id", Ids);
        }

        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        api.getFriendById(params).enqueue(new BaseCallBack<QfriendBean>() {
            @Override
            public void onSuccess(QfriendBean userLoginBean) {
                mView.toEntity(userLoginBean.data, 55);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }
    @Override
    public void getFriendCirclesList(final int type, String pageNum,  String Id) {
        Map<String, String> params = new TreeMap<>();
        params.put("pageNum", pageNum);
        params.put("pageSize", "12");
        if(!TextUtil.isEmpty(Id)){
            params.put("Id", Id);
        }

        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        api.getFriendCirclesList(params).enqueue(new BaseCallBack<QfriendBean>() {
            @Override
            public void onSuccess(QfriendBean userLoginBean) {
                mView.toEntity(userLoginBean.data, type);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }
    @Override
    public void getFriendData(final int type, String pageNum,  String Id) {
        Map<String, String> params = new TreeMap<>();
        params.put("pageNum", pageNum);
        params.put("pageSize", "1");
        if(!TextUtil.isEmpty(Id)){
            params.put("Id", Id);
        }

        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        api.getFriendCirclesList(params).enqueue(new BaseCallBack<QfriendBean>() {
            @Override
            public void onSuccess(QfriendBean userLoginBean) {
                mView.toEntity(userLoginBean.data, type);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }
    @Override
    public void recordIntegralDetails(final int type, String pageNum,  String integralSite, String types, String stringStartTime, String stringEndTime) {
        Map<String, String> params = new TreeMap<>();
        params.put("pageNum", pageNum);
        params.put("pageSize", "20");
        params.put("integralSite", integralSite);
        params.put("type", types);
        if(TextUtil.isNotEmpty(stringStartTime)){
            params.put("stringStartTime", stringStartTime);
        }if(TextUtil.isNotEmpty(stringEndTime)){
            params.put("stringEndTime", stringEndTime);
        }


        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        api.recordIntegralDetails(params).enqueue(new BaseCallBack<INTEGRALIST>() {

            @Override
            public void onSuccess(INTEGRALIST userLoginBean) {
                mView.toEntity(userLoginBean.data, type);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }
    @Override
    public void getProductList(final int type, String pageNum, String pageSize, String searchInfo, String proSite, String searchType, String sortType) {
        Map<String, String> params = new TreeMap<>();
        params.put("pageNum", pageNum);
        params.put("pageSize", pageSize);
        params.put("searchInfo", searchInfo);
        params.put("proSite", proSite);
        params.put("searchType", searchType);
        params.put("sortType", sortType);
        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        api.getProductList(params).enqueue(new BaseCallBack<Product>() {

            @Override
            public void onSuccess(Product userLoginBean) {
                mView.toEntity(userLoginBean.data, type);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }
    @Override
    public void getActivityProductList(final int type, String pageNum, String pageSize, String searchInfo, String proSite, String productType) {
        Map<String, String> params = new TreeMap<>();
        params.put("pageNum", pageNum);
        params.put("pageSize", pageSize);
        params.put("searchInfo", searchInfo);
        params.put("proSite", proSite);
        params.put("productType", productType);
        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        api.getActivityProductList(params).enqueue(new BaseCallBack<Product>() {

            @Override
            public void onSuccess(Product userLoginBean) {
                mView.toEntity(userLoginBean.data, type);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void groupSort(String sortType	,String sortId) {
        Map<String, String> params = new TreeMap<>();
        params.put("sortId", sortId);
        params.put("sortType", sortType);
        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        api.groupSort(params).enqueue(new BaseCallBack<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse userLoginBean) {
                mView.toEntity(userLoginBean, 2);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void getProductBySecondName(final int type, String pageNum, String pageSize, String secondCategoryName, String proSite) {
        Map<String, String> params = new TreeMap<>();
        params.put("pageNum", pageNum);
        params.put("pageSize", pageSize);
        params.put("secondCategoryName", secondCategoryName);
        params.put("proSite", proSite);
        api.getProductBySecondName(params).enqueue(new BaseCallBack<Product>() {

            @Override
            public void onSuccess(Product userLoginBean) {
                mView.toEntity(userLoginBean.data, type);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void seaContentList() {
        Map<String, String> params = new TreeMap<>();

        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        api.seaContentList(params).enqueue(new BaseCallBack<Home>() {

            @Override
            public void onSuccess(Home userLoginBean) {
                mView.toEntity(userLoginBean.data, 1);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void clearSearch() {
        Map<String, String> params = new TreeMap<>();

        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        api.clearSearch(params).enqueue(new BaseCallBack<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse userLoginBean) {
                mView.toEntity(userLoginBean, 2);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void getAllCateList() {
        Map<String, String> params = new TreeMap<>();
        api.getAllCateList(params).enqueue(new BaseCallBack<Catage>() {

            @Override
            public void onSuccess(Catage userLoginBean) {
                mView.toEntity(userLoginBean.data, 2);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void getSecondList(String id) {
        Map<String, String> params = new TreeMap<>();
        if (TextUtil.isNotEmpty(id)) {
            params.put("id", id);
        }


        api.getSecondList(params).enqueue(new BaseCallBack<Catage>() {

            @Override
            public void onSuccess(Catage userLoginBean) {
                mView.toEntity(userLoginBean.data, 3);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void getProDetail(String id) {
        Map<String, String> params = new TreeMap<>();
        params.put("productSkuId", id);

        api.getProDetail(params).enqueue(new BaseCallBack<ProductDel>() {

            @Override
            public void onSuccess(ProductDel userLoginBean) {
                mView.toEntity(userLoginBean.data, 2);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }


    @Override
    public void getProudctSku(String productPlatformId) {
        Map<String, String> params = new TreeMap<>();
        if (TextUtil.isNotEmpty(productPlatformId)) {
            params.put("productPlatformId", productPlatformId);
        }


        api.getProudctSku(params).enqueue(new BaseCallBack<ProductBuy>() {

            @Override
            public void onSuccess(ProductBuy userLoginBean) {
                mView.toEntity(userLoginBean.data, 3);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void getProParameter(String productSkuId) {
        Map<String, String> params = new TreeMap<>();
        if (TextUtil.isNotEmpty(productSkuId)) {
            params.put("productSkuId", productSkuId);
        }


        api.getProParameter(params).enqueue(new BaseCallBack<Param>() {

            @Override
            public void onSuccess(Param userLoginBean) {
                mView.toEntity(userLoginBean.data, 12);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void addToCart(String shopId, String productPlatformId, String number, String proSite, String productSkuId) {
        Map<String, String> params = new TreeMap<>();

        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        params.put("shopId", shopId);
        params.put("productPlatformId", productPlatformId);
        params.put("number", number);
        params.put("proSite", proSite);
        params.put("productSkuId", productSkuId);


        api.addToCart(params).enqueue(new BaseCallBack<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse userLoginBean) {
                mView.toEntity(userLoginBean, 13);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void getMyShopCart(final int type, String pageNum, String pageSize, String proSite) {
        Map<String, String> params = new TreeMap<>();
        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        params.put("pageNum", pageNum);
        params.put("pageSize", pageSize);
        params.put("proSite", proSite);


        api.getMyShopCart(params).enqueue(new BaseCallBack<Product>() {

            @Override
            public void onSuccess(Product userLoginBean) {
                mView.toEntity(userLoginBean, 1);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }


    @Override
    public void deletProduct(String productSkuId) {
        Map<String, String> params = new TreeMap<>();

        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        params.put("productSkuId", productSkuId);


        api.deletProduct(params).enqueue(new BaseCallBack<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse userLoginBean) {
                mView.toEntity(userLoginBean, 2);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void getUserAddress(String type) {
        Map<String, String> params = new TreeMap<>();
        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        params.put("type", type);

        api.getUserAddress(params).enqueue(new BaseCallBack<AddressBase>() {

            @Override
            public void onSuccess(AddressBase userLoginBean) {
                mView.toEntity(userLoginBean.data, 2);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void alterDefaultAddress(String addressId) {
        Map<String, String> params = new TreeMap<>();

        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        params.put("addressId", addressId);


        api.alterDefaultAddress(params).enqueue(new BaseCallBack<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse userLoginBean) {
                mView.toEntity(userLoginBean, 4);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void addUserAddress(String addressId, String shippingName, String linKNumber, String shippingAddress, String detailedAddress, String isDefault) {
        Map<String, String> params = new TreeMap<>();

        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        params.put("shippingName", shippingName);
        if (TextUtil.isNotEmpty(addressId)) {
            params.put("addressId", addressId);
        }
        params.put("linKNumber", linKNumber);
        params.put("shippingAddress", shippingAddress);
        params.put("detailedAddress", detailedAddress);
        params.put("isDefault", isDefault);


        api.addUserAddress(params).enqueue(new BaseCallBack<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse userLoginBean) {
                mView.toEntity(userLoginBean, 3);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void addShop(String shopId, String type) {
        Map<String, String> params = new TreeMap<>();

        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        params.put("shopId", shopId);
        params.put("type", type);


        api.addShop(params).enqueue(new BaseCallBack<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse userLoginBean) {
                mView.toEntity(userLoginBean, 22);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void deleteAdress(String addressId, String addressType) {
        Map<String, String> params = new TreeMap<>();

        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        params.put("addressId", addressId);
        params.put("addressType", addressType);


        api.deleteAdress(params).enqueue(new BaseCallBack<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse userLoginBean) {
                mView.toEntity(userLoginBean, 3);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void deleteOrder(String orderId) {
        Map<String, String> params = new TreeMap<>();
        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        params.put("orderId", orderId);


        api.deleteOrder(params).enqueue(new BaseCallBack<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse userLoginBean) {
                mView.toEntity(userLoginBean, 3);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }


    @Override
    public void verifyPassword(String paymentPassword) {


        Map<String, String> map = new HashMap<>();
        map.put("userInfoId", SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        if(!TextUtil.isEmpty(paymentPassword)){
            map.put("paymentPassword", paymentPassword);
            map.put("type", 1+"");
        }else {
            map.put("type", 2+"");
        }


        api.verifyPassword(map).enqueue(new BaseCallBack<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse userLoginBean) {
                mView.toEntity(userLoginBean, 989);
                CanTingAppLication.isSetting=true;
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                if(code==202){
                    CanTingAppLication.isSetting=false;
                }else if(code==200) {
                    CanTingAppLication.isSetting=true;
                }
                mView.showTomast(t);
            }
        });
    }
    @Override
    public void getVersionAndUrl() {



        api.getVersionAndUrl().enqueue(new BaseCallBack<Version>() {

            @Override
            public void onSuccess(Version userLoginBean) {
                mView.toEntity(userLoginBean.data, 14);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }


    @Override
    public void receiptGoods(String orderType, String orderId) {
        Map<String, String> params = new TreeMap<>();

        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        if(TextUtil.isNotEmpty(orderType)){
            params.put("orderType", orderType);
        }
        params.put("orderId", orderId);


        api.receiptGoods(params).enqueue(new BaseCallBack<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse userLoginBean) {
                mView.toEntity(userLoginBean, 5);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }
    @Override
    public void cancelOrder(List<Params> cancelList) {
        CancelParam param=new CancelParam();
        param.userInfoId=TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance());
        param.cancelList=cancelList;
        api.cancelOrder(param).enqueue(new BaseCallBack<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse userLoginBean) {
                mView.toEntity(userLoginBean, 5);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void accountMoney(OrderParam proList) {


        api.accountMoney(proList).enqueue(new BaseCallBack<OrderData>() {

            @Override
            public void onSuccess(OrderData userLoginBean) {
                mView.toEntity(userLoginBean.data, 6);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void appList() {

        Map<String, String> params = new TreeMap<>();

        api.appList(params).enqueue(new BaseCallBack<apply>() {

            @Override
            public void onSuccess(apply userLoginBean) {
                mView.toEntity(userLoginBean.data, 6);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void getFrendList(String groupId)  {

        Map<String, String> params = new TreeMap<>();
        if(TextUtil.isNotEmpty(groupId)){
            params.put("groupId", groupId);
        }
//        params.put("paymentId", paymentId);

        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));

        api.getFrendList(params).enqueue(new BaseCallBack<FriendListBean>() {

            @Override
            public void onSuccess(FriendListBean userLoginBean) {
                mView.toEntity(userLoginBean.data, 18);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }
    @Override
    public void addGroup(String chatGroupName,String groupBackImage	)  {

        Map<String, String> params = new TreeMap<>();
//        params.put("paymentId", paymentId);
        params.put("chatGroupName", chatGroupName);
        params.put("groupBackImage", groupBackImage	);
        params.put("createBy", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));

        api.addGroup(params).enqueue(new BaseCallBack<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse userLoginBean) {
                mView.toEntity(userLoginBean, 15);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }
    @Override
    public void deleteGroups(String id)  {

        Map<String, String> params = new TreeMap<>();
//        params.put("paymentId", paymentId);
        params.put("id", id);
        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));

        api.deleteGroups(params).enqueue(new BaseCallBack<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse userLoginBean) {
                mView.toEntity(userLoginBean, 18);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }
    @Override
    public void alterGroupName(String chatGroupName,String id,String groupBackImage)  {

        Map<String, String> params = new TreeMap<>();
//        params.put("paymentId", paymentId);

        if(TextUtil.isNotEmpty(chatGroupName)){
            params.put("chatGroupName", chatGroupName);
        }
        if(TextUtil.isNotEmpty(groupBackImage)){
            params.put("groupBackImage", groupBackImage);
        }

        params.put("id", id);
        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));

        api.alterGroupName(params).enqueue(new BaseCallBack<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse userLoginBean) {
                mView.toEntity(userLoginBean, 17);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }
    @Override
    public void alterGroupImage(String id,String groupBackImage)  {

        Map<String, String> params = new TreeMap<>();
//        params.put("paymentId", paymentId);


        if(TextUtil.isNotEmpty(groupBackImage)){
            params.put("groupBackImage", groupBackImage);
        }

        params.put("id", id);
        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));

        api.alterGroupImage(params).enqueue(new BaseCallBack<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse userLoginBean) {
                mView.toEntity(userLoginBean, 17);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void updateOwnerName(String userGroupName,String groupsId)  {

        Map<String, String> params = new TreeMap<>();

        params.put("userGroupName", userGroupName	);
        params.put("groupsId", groupsId);
        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));

        api.updateOwnerName(params).enqueue(new BaseCallBack<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse userLoginBean) {
                mView.toEntity(userLoginBean, 12);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }
    @Override
    public void updateGroupsName(String groupsName,String groupsId)  {

        Map<String, String> params = new TreeMap<>();
//        params.put("paymentId", paymentId);
        params.put("groupsName", groupsName);
        params.put("groupsId", groupsId);
        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));

        api.updateGroupsName(params).enqueue(new BaseCallBack<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse userLoginBean) {
                mView.toEntity(userLoginBean, 15);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }


    @Override
    public void success(String paymentId,String orderCode)  {

        Map<String, String> params = new TreeMap<>();
//        params.put("paymentId", paymentId);
        params.put("orderCode", orderCode);
        api.success(params).enqueue(new BaseCallBack<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse userLoginBean) {
                mView.toEntity(userLoginBean, 18);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }
    @Override
    public void integerSuccess(String  orderId)  {

        Map<String, String> params = new TreeMap<>();
//        params.put("paymentId", paymentId);
        params.put(" orderId",  orderId);
        api.integerSuccess(params).enqueue(new BaseCallBack<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse userLoginBean) {
                mView.toEntity(userLoginBean, 18);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void submitOrders(OrderParam proList) {


        api.submitOrders(proList).enqueue(new BaseCallBack<alipay>() {

            @Override
            public void onSuccess(alipay userLoginBean) {
                mView.toEntity(userLoginBean.data, 9);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }
    @Override
    public void submitOrderpal(OrderParam proList) {


        api.submitOrderPal(proList).enqueue(new BaseCallBack<BaseBean>() {

            @Override
            public void onSuccess(BaseBean userLoginBean) {
                mView.toEntity(userLoginBean.data, 16);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }
    @Override
    public void submitOrder(OrderParam proList) {


        api.submitOrder(proList).enqueue(new BaseCallBack<WEIXINREQ>() {

            @Override
            public void onSuccess(WEIXINREQ userLoginBean) {
                mView.toEntity(userLoginBean.data, 8);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void getShopById(String mId) {


        Map<String, String> params = new TreeMap<>();

        params.put("mId", mId);
        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));


        api.getShopById(params).enqueue(new BaseCallBack<ShopBean>() {

            @Override
            public void onSuccess(ShopBean userLoginBean) {
                mView.toEntity(userLoginBean.data, 7);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void hostInfo() {


        Map<String, String> params = new TreeMap<>();

        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));


        api.hostInfo(params).enqueue(new BaseCallBack<Host>() {

            @Override
            public void onSuccess(Host userLoginBean) {
                mView.toEntity(userLoginBean.data, 7);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void focusList(String roomInfoId, String favoriteType) {


        Map<String, String> params = new TreeMap<>();
        params.put("roomInfoId", roomInfoId);
        params.put("favoriteType", favoriteType);
        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));


        api.focusList(params).enqueue(new BaseCallBack<Care>() {

            @Override
            public void onSuccess(Care userLoginBean) {
                mView.toEntity(userLoginBean.data.founsList, 5);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void upFavoriteType(String roomInfoId, String favoriteType, String anchorsId) {


        Map<String, String> params = new TreeMap<>();
        params.put("roomInfoId", roomInfoId);
        params.put("favoriteType", favoriteType);
        params.put("anchorsId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        params.put("userInfoId", anchorsId);


        api.upFavoriteType(params).enqueue(new BaseCallBack<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse userLoginBean) {
                mView.toEntity(userLoginBean, 6);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void  focusTV(String roomInfoId, String anchorsId, final String type) {

        Map<String, String> params = new TreeMap<>();
        params.put("roomInfoId", roomInfoId);
        params.put("anchorsId", anchorsId);
        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        params.put("type", type);

        api.focusTV(params).enqueue(new BaseCallBack<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse userLoginBean) {
                mView.toEntity(userLoginBean, Integer.valueOf(type));
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void  addInfo(String postInfo, String postImage) {

        Map<String, String> params = new TreeMap<>();
        params.put("postInfo", postInfo);
        if(TextUtil.isNotEmpty(postImage)){
            params.put("postImage", postImage);
        }else {
            params.put("postImage", "");
        }

        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));


        api.addInfo(params).enqueue(new BaseCallBack<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse userLoginBean) {
                mView.toEntity(userLoginBean, 16);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }
    @Override
    public void  addFriendComment(String topicId, String toUid,String fromUid,String content ) {

        Map<String, String> params = new TreeMap<>();
        params.put("topicId", topicId);
        params.put("fromUid", fromUid);
        params.put("content", content);
        params.put("toUid", toUid);

        params.put("userId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));


        api.addFriendComment(params).enqueue(new BaseCallBack<QfriendBean>() {

            @Override
            public void onSuccess(QfriendBean userLoginBean) {
                mView.toEntity(userLoginBean.data, 66);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }
    @Override
    public void  delFriendComment(String topicId, String sendId,String fromUid,String id ) {

        Map<String, String> params = new TreeMap<>();
        params.put("topicId", topicId);
        params.put("fromUid", fromUid);
        params.put("sendId", sendId);
        params.put("id", id);

        params.put("userId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));


        api.delFriendComment(params).enqueue(new BaseCallBack<QfriendBean>() {

            @Override
            public void onSuccess(QfriendBean userLoginBean) {
                mView.toEntity(userLoginBean.data, 88);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void delFriendCircle(String topicId, String sendId) {


        Map<String, String> map = new HashMap<>();
        map.put("userId", SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        map.put("sendId", sendId);
        map.put("topicId", topicId);

        api.delFriendCircle(map).enqueue(new BaseCallBack<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse userLoginBean) {
                mView.toEntity(userLoginBean, 99);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }
    @Override
    public void addRemark(String friendsId, String remarkName,String remarkPhone, String remarkMessage) {


        Map<String, String> map = new HashMap<>();
        map.put("userInfoId", SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        map.put("friendsId", friendsId);
        map.put("remarkName", remarkName);
        map.put("remarkPhone", remarkPhone);
        map.put("remarkMessage", remarkMessage);

        api.addRemark(map).enqueue(new BaseCallBack<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse userLoginBean) {
                mView.toEntity(userLoginBean, 999);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void getCircleImage(String id) {


        Map<String, String> map = new HashMap<>();
        if(TextUtil.isNotEmpty(id)){
            map.put("userInfoId", id);
        }else {
            map.put("userInfoId", SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        }


        api.getCircleImage(map).enqueue(new BaseCallBack<BaseBe>() {

            @Override
            public void onSuccess(BaseBe userLoginBean) {
                mView.toEntity(userLoginBean.data, 99);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void friendInfo(String friendsId) {


        Map<String, String> map = new HashMap<>();
        map.put("friendsId", friendsId);
        map.put("userInfoId", SpUtil.getUserInfoId(CanTingAppLication.getInstance()));

        api.friendInfo(map).enqueue(new BaseCallBack<FriendInfo>() {

            @Override
            public void onSuccess(FriendInfo userLoginBean) {
                mView.toEntity(userLoginBean.data, 79);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void  changeFriendLike(String topicId, String type ) {

        Map<String, String> params = new TreeMap<>();
        params.put("topicId", topicId);
        params.put("type", type);

        params.put("fromUid", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));


        api.changeFriendLike(params).enqueue(new BaseCallBack<QfriendBean>() {

            @Override
            public void onSuccess(QfriendBean userLoginBean) {
                mView.toEntity(userLoginBean.data, 66);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void upUserGroup(String chatUserId, String groupName, String groupId,String type) {


        Map<String, String> map = new HashMap<>();
        map.put("userInfoId", SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        map.put("chatUserId", chatUserId);
        map.put("groupName", groupName);
        map.put("groupId", groupId);
        map.put("type", type);

        api.upUserGroup(map).enqueue(new BaseCallBack<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse userLoginBean) {
                mView.toEntity(userLoginBean, 6);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }
    @Override
    public void upRoomInfo(String directSeeName, String leaveMassege, String roomImage, String directOverview) {


        Map<String, String> map = new HashMap<>();
        map.put("userInfoId", SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        map.put("roomInfoId", SpUtil.getString(CanTingAppLication.getInstance(), "room_id", ""));
        map.put("directSeeName", directSeeName);
        map.put("roomImage", roomImage);
        map.put("directOverview", directOverview);
        map.put("leaveMassege", leaveMassege);


        api.upRoomInfo(map).enqueue(new BaseCallBack<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse userLoginBean) {
                mView.toEntity(userLoginBean, 6);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }


    @Override
    public void getChatGroupList() {


        Map<String, String> params = new TreeMap<>();
        params.put("createBy", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));

        api.getChatGroupList(params).enqueue(new BaseCallBack<GAME>() {

            @Override
            public void onSuccess(GAME userLoginBean) {
                mView.toEntity(userLoginBean, 22);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void getBanners(String bannerSite) {


        Map<String, String> params = new TreeMap<>();
        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        params.put("type", bannerSite);
//        params.put("bannerType", "1");

        api.getBanners(params).enqueue(new BaseCallBack<Banner>() {

            @Override
            public void onSuccess(Banner userLoginBean) {
                mView.toEntity(userLoginBean, 22);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }


    @Override
    public void getHostdirHostList() {


        Map<String, String> params = new TreeMap<>();
        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));

        api.getHostdirHostList(params).enqueue(new BaseCallBack<Hand>() {

            @Override
            public void onSuccess(Hand userLoginBean) {
                mView.toEntity(userLoginBean.data, 6);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void getRankingList(String pageNum, String roomInfoId, String type, String startDate, String endDate, final int loadtype) {


        Map<String, String> params = new TreeMap<>();


        params.put("pageNum", pageNum);
        params.put("pageSize", "16");
        params.put("roomInfoId", SpUtil.getString(CanTingAppLication.getInstance(), "room_id", ""));
        params.put("type", type);
        params.put("startDate", startDate);
        params.put("endDate", endDate);

        api.getRankingList(params).enqueue(new BaseCallBack<Hands>() {

            @Override
            public void onSuccess(Hands userLoginBean) {
                mView.toEntity(userLoginBean, loadtype);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }
    @Override
    public void getChatDetailListForTime( String chatType, String timeType) {


        Map<String, String> params = new TreeMap<>();

        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));

        params.put("pageNum", 1+"");
        params.put("pageSize", "500");
        params.put("chatType", chatType);
        params.put("timeType", timeType);


        api.getChatDetailListForTime(params).enqueue(new BaseCallBack<Hand>() {

            @Override
            public void onSuccess(Hand userLoginBean) {
                mView.toEntity(userLoginBean,0);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }
    @Override
    public void getChatDetailForTimeSearch( String chatType, String stringStartTime,String stringEndTime) {


        Map<String, String> params = new TreeMap<>();

        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        params.put("pageNum", 1+"");
        params.put("pageSize", "500");
        params.put("chatType", chatType);
        params.put("stringStartTime", stringStartTime);
        params.put("stringEndTime", stringEndTime);


        api.getChatDetailForTimeSearch(params).enqueue(new BaseCallBack<Hand>() {

            @Override
            public void onSuccess(Hand userLoginBean) {
                mView.toEntity(userLoginBean,0);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }


    @Override
    public void getDirectRoomInfo() {


        Map<String, String> params = new TreeMap<>();


        params.put("roomInfoId", SpUtil.getString(CanTingAppLication.getInstance(), "room_id", ""));


        api.getDirectRoomInfo(params).enqueue(new BaseCallBack<Hands>() {

            @Override
            public void onSuccess(Hands userLoginBean) {
                mView.toEntity(userLoginBean.data, 3);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void getCodes(final String type) {


        Map<String, String> params = new TreeMap<>();


        params.put("phoneNumber", SpUtil.getMobileNumber(CanTingAppLication.getInstance()));
        params.put("type", type);


        api.getCodes(params).enqueue(new BaseCallBack<Codes>() {

            @Override
            public void onSuccess(Codes userLoginBean) {
                mView.toEntity(userLoginBean.data, Integer.valueOf(type));
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void uploadCode(String type,String codeUrl) {


        Map<String, String> params = new TreeMap<>();


        params.put("phoneNumber", SpUtil.getMobileNumber(CanTingAppLication.getInstance()));
        params.put("type", type);
        params.put("codeUrl", codeUrl);


        api.uploadCode(params).enqueue(new BaseCallBack<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse userLoginBean) {
                mView.toEntity(userLoginBean, 5);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }
    @Override
    public void getGiftDetailedList(String pageNum, final int loadtype) {


        Map<String, String> params = new TreeMap<>();


        params.put("roomInfoId", SpUtil.getString(CanTingAppLication.getInstance(), "room_id", ""));
        params.put("pageSize","12" );
        params.put("pageNum",pageNum );


        api.getGiftDetailedList(params).enqueue(new BaseCallBack<Hands>() {

            @Override
            public void onSuccess(Hands userLoginBean) {
                mView.toEntity(userLoginBean.data, loadtype);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }


    @Override
    public void getUserIntegral() {


        Map<String, String> params = new TreeMap<>();

        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));


        api.getUserIntegral(params).enqueue(new BaseCallBack<Ingegebean>() {

            @Override
            public void onSuccess(Ingegebean userLoginBean) {
                mView.toEntity(userLoginBean.data, 19);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }
    @Override
    public void anchorsList() {


        Map<String, String> params = new TreeMap<>();

        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));


        api.anchorsList(params).enqueue(new BaseCallBack<Cares>() {

            @Override
            public void onSuccess(Cares userLoginBean) {
                mView.toEntity(userLoginBean.data, 9);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void userInfo() {


        Map<String, String> params = new TreeMap<>();

        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));


        api.userInfo(params).enqueue(new BaseCallBack<OrderType>() {

            @Override
            public void onSuccess(OrderType userLoginBean) {
                mView.toEntity(userLoginBean.data, 7);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void rechargeInteger(String integralNumber,String payType) {


        Map<String, String> params = new TreeMap<>();

        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        params.put("payType", payType);
        params.put("integralNumber", integralNumber);

        api.rechargeInteger(params).enqueue(new BaseCallBack<alipay>() {

            @Override
            public void onSuccess(alipay userLoginBean) {
                mView.toEntity(userLoginBean.data, 9);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }
    @Override
    public void rechargeIntegers(String integralNumber,String payType) {


        Map<String, String> params = new TreeMap<>();

        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        params.put("payType", payType);
        params.put("integralNumber", integralNumber);

        api.rechargeIntegers(params).enqueue(new BaseCallBack<WEIXINREQ>() {

            @Override
            public void onSuccess(WEIXINREQ userLoginBean) {
                mView.toEntity(userLoginBean.data, 8);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void rechargeIntegerss(String integralNumber,String payType) {


        Map<String, String> params = new TreeMap<>();

        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        params.put("payType", payType);
        params.put("integralNumber", integralNumber);

        api.rechargeIntegerss(params).enqueue(new BaseCallBack<BaseBean>() {

            @Override
            public void onSuccess(BaseBean userLoginBean) {
                mView.toEntity(userLoginBean.data, 16);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void favoriteList(String pageNum, String pageSize, String orderType) {


        Map<String, String> params = new TreeMap<>();

        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        params.put("pageNum", pageNum);
        params.put("pageSize", pageSize);
        params.put("orderType", orderType.equals("4") ? "7" : orderType);

        api.favoriteList(params).enqueue(new BaseCallBack<OrderData>() {

            @Override
            public void onSuccess(OrderData userLoginBean) {
                mView.toEntity(userLoginBean.data, 7);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void getDirRoomClassify() {


        api.getDirRoomClassify().enqueue(new BaseCallBack<GAME>() {

            @Override
            public void onSuccess(GAME userLoginBean) {
                mView.toEntity(userLoginBean.data, 12);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    @Override
    public void orderDetails(String orderId) {


        Map<String, String> params = new TreeMap<>();

        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        params.put("orderId", orderId);

        api.orderDetails(params).enqueue(new BaseCallBack<OrderData>() {

            @Override
            public void onSuccess(OrderData userLoginBean) {
                mView.toEntity(userLoginBean.data.size() != 0 ? userLoginBean.data.get(0) : null, 7);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

    public void shoFavorite() {


        Map<String, String> params = new TreeMap<>();

        params.put("userInfoId", TextUtil.isEmpty(SpUtil.getUserInfoId(CanTingAppLication.getInstance())) ? "" : SpUtil.getUserInfoId(CanTingAppLication.getInstance()));
        params.put("pageNum", 1 + "");
        params.put("pageSize", 200 + "");

        api.shoFavorite(params).enqueue(new BaseCallBack<Favor>() {

            @Override
            public void onSuccess(Favor userLoginBean) {
                mView.toEntity(userLoginBean.data, 7);
            }

            @Override
            public void onOtherErr(int code, String t) {
                super.onOtherErr(code, t);
                mView.showTomast(t);
            }
        });
    }

}
