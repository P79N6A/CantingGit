package com.zhongchuang.canting.activity.mall;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhongchuang.canting.R;;
import com.zhongchuang.canting.activity.ChatActivity;
import com.zhongchuang.canting.adapter.BannerAdapter;
import com.zhongchuang.canting.adapter.ListImageAdapter;
import com.zhongchuang.canting.app.CanTingAppLication;
import com.zhongchuang.canting.base.BaseAllActivity;
import com.zhongchuang.canting.been.Banner;
import com.zhongchuang.canting.been.Oparam;
import com.zhongchuang.canting.been.OrderParam;
import com.zhongchuang.canting.been.Param;
import com.zhongchuang.canting.been.ProductBuy;
import com.zhongchuang.canting.been.ProductDel;
import com.zhongchuang.canting.been.SubscriptionBean;
import com.zhongchuang.canting.presenter.BaseContract;
import com.zhongchuang.canting.presenter.BasesPresenter;
import com.zhongchuang.canting.utils.SpUtil;
import com.zhongchuang.canting.utils.TextUtil;
import com.zhongchuang.canting.widget.BaseDailogManager;
import com.zhongchuang.canting.widget.MarkaBaseDialog;
import com.zhongchuang.canting.widget.RegularListView;
import com.zhongchuang.canting.widget.RxBus;
import com.zhongchuang.canting.widget.ShopBuyWindow;
import com.zhongchuang.canting.widget.ShopTypeWindow;
import com.zhongchuang.canting.widget.StickyScrollView;
import com.zhongchuang.canting.widget.banner.BannerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;

public class ShopMallDetailActivity extends BaseAllActivity implements View.OnClickListener, BaseContract.View {


    @BindView(R.id.bannerView)
    BannerView bannerView;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_price_mark)
    TextView tvPriceMark;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_turn)
    TextView tvTurn;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.rl_menu)
    RegularListView rlMenu;
    @BindView(R.id.tv_add_car)
    TextView tvAddCar;
    @BindView(R.id.tv_buy)
    TextView tvBuy;
    @BindView(R.id.ll_reg)
    LinearLayout llReg;
    @BindView(R.id.tv_go)
    TextView tvGo;
    @BindView(R.id.tv_shop_name)
    TextView tvShopName;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.scrollView)
    StickyScrollView scrollView;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.ll_chat)
    LinearLayout llChat;
    @BindView(R.id.tv_detail)
    TextView tvDetail;
    private Subscription mSubscription;
    //    private ProfileInfoHelper infoHelper;
    private int type = 1;
    private BasesPresenter presenter;
    private String id;
    private BannerAdapter bannerAdapter;
    private ListImageAdapter imgadapter;

    @Override
    public void initViews() {
        setContentView(R.layout.shopdetail_activity);
        ButterKnife.bind(this);
        bannerView.requestFocus();
        bannerView.setFocusable(true);
        bannerView.setFocusableInTouchMode(true);
        scrollView.setFocusable(false);
        presenter = new BasesPresenter(this);
        id = getIntent().getStringExtra("id");
        type = getIntent().getIntExtra("type", 1);
        presenter.getProDetail(id);
        bannerAdapter = new BannerAdapter(this);
        imgadapter = new ListImageAdapter(this);
        rlMenu.setAdapter(imgadapter);
        bannerView.setAdapter(bannerAdapter);
    }

    @Override
    public void bindEvents() {
        llChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtil.isNotEmpty(product.userInfoId)) {
                    Intent intentc = new Intent(ShopMallDetailActivity.this, ChatActivity.class);
                    intentc.putExtra("userId", product.userInfoId);
                    startActivity(intentc);
                } else {
                    showToasts(getString(R.string.sjwkt));
                }

            }
        });
        mSubscription = RxBus.getInstance().toObserverable(SubscriptionBean.RxBusSendBean.class).subscribe(new Action1<SubscriptionBean.RxBusSendBean>() {
            @Override
            public void call(SubscriptionBean.RxBusSendBean bean) {
                if (bean == null) return;

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
        RxBus.getInstance().addSubscription(mSubscription);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void initData() {
        showSelectType();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscription.unsubscribe();
    }


    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        onViewClicked(v);
    }

    private int select = 0;
    private Oparam param = new Oparam();
    private OrderParam params = new OrderParam();

    @OnClick({R.id.ll_reg, R.id.tv_add_car, R.id.tv_buy, R.id.tv_go})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.ll_reg:
                shoptypeWindow.showPopView(line);
                break;
            case R.id.tv_add_car:
                select = 0;
                shopBuyWindow.showPopView(line);
                break;
            case R.id.tv_buy:
                select = 1;
                if (TextUtil.isNotEmpty(sku_id)) {
                    goBuy();

                } else {
                    shopBuyWindow.showPopView(line);
                }
                break;
            case R.id.tv_go:
                if (product == null || TextUtil.isEmpty(product.m_id)) {
                    return;
                }
                Intent intent = new Intent(ShopMallDetailActivity.this, ShopDetailActivity.class);

                intent.putExtra("id", product.m_id);
                startActivity(intent);
                break;


        }
    }

    public void goBuy() {
        param.productSkuId = sku_id;
        param.number = cout;

        if (product.pro_site.equals("1")) {
            param.integralPrice = 0 + "";
        } else {
            param.integralPrice = product.integral_price;
        }
        if (CanTingAppLication.totalintegral < Double.valueOf(product.integral_price)) {
            showToasts(getString(R.string.ndjfbzwfgm));
            return;
        }
        List<Oparam> datas = new ArrayList<>();
        datas.add(param);
        params.productList = datas;
        params.proSite = type == 1 ? "1" : "2";
        params.userInfoId = SpUtil.getUserInfoId(this);
        Intent intent = new Intent(ShopMallDetailActivity.this, EditorOrderActivity.class);
        intent.putExtra("data", params);
        intent.putExtra("cont", (bug.firstSpeciValue) + (TextUtil.isNotEmpty(bug.secondSpeciValue) ? "," + bug.secondSpeciValue : "") +
                (TextUtil.isNotEmpty(bug.threeSpeciValue) ? "," + bug.threeSpeciValue : "") +
                (TextUtil.isNotEmpty(bug.fourSpeciValue) ? "," + bug.fourSpeciValue : "") +
                (TextUtil.isNotEmpty(bug.fiveSpeciValue) ? "," + bug.fiveSpeciValue : "")
        );
        intent.putExtra("type", type);
        startActivity(intent);
    }

    //    private ShopTagDiglog shopTagDiglog;
    private ShopBuyWindow shopBuyWindow;
    private ShopTypeWindow shoptypeWindow;
    private String sku_id;
    private String cout;
    private ProductBuy bug;

    private void showSelectType() {
        shopBuyWindow = new ShopBuyWindow(this);
        shoptypeWindow = new ShopTypeWindow(this);
        shopBuyWindow.setSureListener(new ShopBuyWindow.ClickListener() {
            @Override
            public void clickListener(String types, String couts, ProductBuy product) {
                sku_id = types;
                cout = couts;
                bug = product;
                if (TextUtil.isNotEmpty(types)) {
                    if (select == 1) {
                        goBuy();
                    } else {
                        presenter.addToCart(shopId, product_platform_id, cout, type + "", types);
                        showProgress(getString(R.string.tjz));
                        presenter.getProParameter(types);
                    }

                }
            }
        });
//        shopTagDiglog  = new ShopTagDiglog.Builder(this)
//                .setBanViewColor(new TagContainerLayout.ViewColor())
//                .setDefaultViewColor(new TagContainerLayout.ViewColor(ContextCompat.getColor(this, R.color.e6), 0, ContextCompat.getColor(this, R.color.color6)))
//                .setClickViewColor(new TagContainerLayout.ViewColor(Color.parseColor("#2A93FF"), 0, ContextCompat.getColor(this, R.color.white)))
//                .setTagBean(listTwo)
//                .create();
//        LogUtil.d("------=" + new Gson().toJson(listTwo));
//        shopTagDiglog.setData(map);
//        shopTagDiglog.setOnClickListener(new ShopTagDiglog.onClickListener() {
//            @Override
//            public void clickListener(String sku, String cout) {
//                shopTagDiglog.dismiss();
//            }
//        });
//        shopTagDiglog.show();

    }


    private List<Banner> data = new ArrayList<>();
    private String product_platform_id;
    private String shopId;
    private ProductDel product;

    @Override
    public <T> void toEntity(T entity, int type) {
        dimessProgress();
        if (type == 2) {
            product = (ProductDel) entity;
            if (product != null) {
                if (TextUtil.isNotEmpty(product.picture_url)) {
                    String[] split = product.picture_url.split(",");
                    for (String url : split) {
                        Banner banner = new Banner();
                        banner.image_url = url;
                        data.add(banner);
                    }
                    bannerAdapter.setData(data);

                }
                if (TextUtil.isNotEmpty(product.picture_description_url)) {
                    String[] split = product.picture_description_url.split(",");
                    List<String> dat = new ArrayList<>();
                    for (String url : split) {
                        dat.add(url);
                    }
                    imgadapter.setData(dat);

                }
                product_platform_id = product.product_platform_id;
                shopId = product.shop_id;
                presenter.getProudctSku(product.product_platform_id);
                presenter.getProParameter(product.product_sku_id);
                if (TextUtil.isNotEmpty(product.market_price)) {

                    tvPriceMark.setText("￥" + product.market_price);

                }
                if (product.pro_site.equals("1")) {
                    if (TextUtil.isNotEmpty(product.pro_price)) {
                        if (Integer.valueOf(product.integral_price) > 0) {
                            tvPrice.setText("￥" + product.pro_price + "+" + product.integral_price + "积分");
                        } else {
                            tvPrice.setText("￥" + product.pro_price);
                        }
//                        tvPrice.setText("￥" + product.pro_price);
                    }
                } else if (product.pro_site.equals("3")) {
                    if (TextUtil.isNotEmpty(product.pro_price)) {

                        tvPrice.setText("￥" + product.pro_price);

                    }
                } else {

                    if (TextUtil.isNotEmpty(product.integral_price)) {
                        tvPrice.setText(getString(R.string.jf) + product.integral_price);
                    }
                }
                if (TextUtil.isNotEmpty(product.pro_name)) {
                    tvName.setText(getString(R.string.spmc) + product.pro_name);
                }
                if (TextUtil.isNotEmpty(product.ship_address)) {
                    tvAddress.setText(getString(R.string.fhdzs) + product.ship_address);
                }

                if (TextUtil.isNotEmpty(product.mer_name)) {
                    tvShopName.setText(getString(R.string.dpmc) + product.mer_name);
                }
                if (product.express_type == 1) {
                    if (TextUtil.isNotEmpty(product.express_content) && product.express_content.equals("0.00")) {
                        tvTurn.setText(getString(R.string.kds) + product.express_content);
                    } else if (TextUtil.isNotEmpty(product.express_content)) {
                        tvTurn.setText(getString(R.string.kds) + product.express_content);
                    }
                } else if (product.express_type == 2) {

                    if (TextUtil.isNotEmpty(product.express_content)) {
                        tvDetail.setVisibility(View.VISIBLE);
                        tvTurn.setText(R.string.wldf);
                    }
                    tvTurn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (TextUtil.isNotEmpty(product.express_content)) {
                                showPopwindow(product.express_content);
                            }

                        }
                    });

                }


            }
        } else if (type == 12) {
            List<Param> productBuy = (List<Param>) entity;

            shoptypeWindow.selectData(productBuy);
        } else if (type == 13) {
            showToasts(getString(R.string.tjcg));
        } else {
            ProductBuy productBuy = (ProductBuy) entity;
            if (productBuy.listResult != null) {
                shopBuyWindow.selectData(productBuy.listResult, productBuy.tierNumber);
            }

//            for(ProductBuy bug:productBuy.listResult){
//                map.put(bug.firstSpeciValue+"#"+bug.secondSpeciValue,bug.product_sku_id+"#"+bug.pro_stock+"#"+(TextUtil.isEmpty(bug.picture_url)?"url":bug.picture_url)+"#"+(bug.pro_site==1?bug.pro_price:bug.integral_price));
//            }
//            for(String stu:productBuy.secondSet){
//                maps.put(stu,stu);
//            }
//            for(ProductBuy bug:productBuy.speciMap){
//                List<TagBean> tagBeans = new ArrayList<>();
//                for(String size:bug.secondValue){
//                    String sku = map.get(bug.firstValue + "#" + size);
//
//                    if(TextUtil.isNotEmpty(sku)){
//                        String[] split = sku.split("#");
//
//                        tagBeans.add(new TagBean(size,Double.valueOf(split[3]), Integer.valueOf(split[1]),split[2]));
//                    }else {
//                        tagBeans.add(new TagBean(size, 0, 0,""));
//                    }
//
//                }
//                TagBean tagBean1 = new TagBean();
//                tagBean1.setTitle(bug.firstValue);
//                tagBean1.setTagBean(tagBeans);
//                listTwo.add(tagBean1);
//            }
        }

    }

    private View views;
    private TextView tv_content;
    private ImageView ivClose;

    public void showPopwindow(String content) {

        views = View.inflate(this, R.layout.pop_expross_view, null);
        tv_content = (TextView) views.findViewById(R.id.tv_content);

        ivClose = (ImageView) views.findViewById(R.id.iv_close);


        dialog = BaseDailogManager.getInstance().getBuilder(this).setMessageView(views).create();
        dialog.show();
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_content.setText(content);


    }

    private MarkaBaseDialog dialog;

    @Override
    public void toNextStep(int type) {
        dimessProgress();
    }

    @Override
    public void showTomast(String msg) {
        dimessProgress();
        showToasts(msg);
    }



}




