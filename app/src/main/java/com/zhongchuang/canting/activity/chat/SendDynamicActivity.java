package com.zhongchuang.canting.activity.chat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongchuang.canting.R;;
import com.zhongchuang.canting.base.BaseActivity;
import com.zhongchuang.canting.base.BaseActivity1;
import com.zhongchuang.canting.been.SubscriptionBean;
import com.zhongchuang.canting.been.TOKEN;
import com.zhongchuang.canting.been.UploadFileBean;
import com.zhongchuang.canting.net.HXRequestService;
import com.zhongchuang.canting.net.netService;
import com.zhongchuang.canting.permission.PermissionConst;
import com.zhongchuang.canting.permission.PermissionFail;
import com.zhongchuang.canting.permission.PermissionGen;
import com.zhongchuang.canting.permission.PermissionSuccess;
import com.zhongchuang.canting.presenter.BaseContract;
import com.zhongchuang.canting.presenter.BasesPresenter;
import com.zhongchuang.canting.utils.QiniuUtils;
import com.zhongchuang.canting.utils.TextUtil;
import com.zhongchuang.canting.widget.BaseDailogManager;
import com.zhongchuang.canting.widget.ClearEditText;
import com.zhongchuang.canting.widget.ImageUploadView;
import com.zhongchuang.canting.widget.MarkaBaseDialog;
import com.zhongchuang.canting.widget.NavigationBar;
import com.zhongchuang.canting.widget.PhotoPopupWindow;
import com.zhongchuang.canting.widget.RxBus;
import com.zhongchuang.canting.widget.popupwindow.PopView_UploadImg;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.valuesfeng.picker.ImageSelectActivity;
import io.valuesfeng.picker.Picker;
import io.valuesfeng.picker.widget.ImageLoaderEngine;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


//发布朋友圈
public class SendDynamicActivity extends BaseActivity1 implements BaseContract.View  {


    @BindView(R.id.navigationBar)
    NavigationBar navigationBar;
    @BindView(R.id.et_content)
    ClearEditText etContent;
    @BindView(R.id.iv_add_photo)
    ImageView ivAddPhoto;
    @BindView(R.id.piuv_remark_image)
    ImageUploadView piuvRemarkImage;
    @BindView(R.id.txt_send)
    TextView txtSend;
    @BindView(R.id.ll_bottom_button)
    View llBottomButton;

    private int upload_position;

    private BasesPresenter presenter;
    private List<File> files = new ArrayList<>();
    private PopView_UploadImg popView_uploadImg;
    private ArrayList<UploadFileBean> img_path = new ArrayList<>();
    private int i = 1;
    private String url;
    private String content;

    private int count = 9;


    @Override
    public void initViews() {
        setContentView(R.layout.activity_send_dynamic);
        ButterKnife.bind(this);
        presenter = new BasesPresenter(this);
        navigationBar.setNavigationBarListener(this);
        haveFous(false);
    }

    @Override
    public void bindEvents() {
        piuvRemarkImage.setOnActionListener(new ImageUploadView.OnActionListener() {
            @Override
            public void onItemDelete(int position) {
                img_path.remove(position);
                if (img_path.size() == 0&&TextUtil.isEmpty(etContent.getText().toString())) {
                    haveFous(false);
                }
                notifyImageDataChange();
            }

            @Override
            public void onItemClick(int position) {
            }

            @Override
            public void onAddMoreClick() {
                if (count - img_path.size() >= 1) {
                   showAddPhotoWindow(count - img_path.size());

                }

            }

            @Override
            public void onItemPositionChange() {
                //mUploadView.notifyItemChange(0);
            }
        });
        etContent.setOnClearEditTextListener(new ClearEditText.ClearEditTextListener() {
            @Override
            public void afterTextChanged4ClearEdit(Editable s) {

            }

            @Override
            public void changeText(CharSequence s) {
                if (TextUtil.isNotEmpty(s.toString())) {
                    content = s.toString();
                    haveFous(true);
                } else {
                    content = "";
                    haveFous(false);
                }

            }
        });
        txtSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showProgress(getString(R.string.fbz));
                url="";
                haveFous(false);
                if(img_path!=null&&img_path.size()>0){

                    getUpToken(img_path.get(0).getForderPath());
                }else {
                    if(TextUtil.isEmpty(etContent.getText().toString())){
                        dimessProgress();

                        return;
                    }else {
                        presenter.addInfo(etContent.getText().toString(),"");
                    }

                }

            }
        });
        ivAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyBoard();
                PermissionGen.with(SendDynamicActivity.this)
                        .addRequestCode(PermissionConst.REQUECT_CODE_CAMERA)
                        .permissions(Manifest.permission.CAMERA)
                        .request();

            }
        });
    }

    public void haveFous(boolean isFous) {
        if (isFous) {
            txtSend.setBackground(getResources().getDrawable(R.drawable.login_selector));
            txtSend.setClickable(true);
        } else {

            txtSend.setBackground(getResources().getDrawable(R.drawable.hui_bg));
            txtSend.setClickable(false);
        }
    }

    @Override
    public void initData() {

    }

    @PermissionSuccess(requestCode = PermissionConst.REQUECT_CODE_CAMERA)
    public void requestSdcardSuccess() {
      showAddPhotoWindow(count-img_path.size());

    }



    @PermissionFail(requestCode = PermissionConst.REQUECT_CODE_CAMERA)
    public void requestSdcardFailed() {

    }


    /**
     * 删除照片和修改相册的回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //上传照片
                case 66:
                    List<String> imgs = data.getStringArrayListExtra(ImageSelectActivity.EXTRA_RESULT_SELECTION);

                    for (String imgPath : imgs) {
                        if (imgPath != null) {
                            UploadFileBean bean = new UploadFileBean(1);
                            bean.setForderPath(imgPath);
                            img_path.add(bean);
                        }
                    }

                    haveFous(true);
                    notifyImageDataChange();
                    upload_position = 0;
                    break;

            }
        }
    }


    public void getUpImgSuccdess(String info) {
        if (img_path.size() > i) {
            if (i == 1) {
                url = info;
            } else {
                url = url + "," + info;
            }
            upFlile(img_path.get(i).getForderPath());
            i++;
        } else {
            if (i == 1) {
                url = info;
            } else {
                url = url + "," + info;
            }

            presenter.addInfo(etContent.getText().toString(),url);
        }
    }
    private String token;
    private void getUpToken(final String path) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(netService.TOM_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        HXRequestService api = retrofit.create(HXRequestService.class);


        Call<TOKEN> call = api.getUpToken();
        call.enqueue(new Callback<TOKEN>() {
            @Override
            public void onResponse(Call<TOKEN> call, Response<TOKEN> response) {
                token=response.body().data.upToken;
                upFlile(path);
            }

            @Override
            public void onFailure(Call<TOKEN> call, Throwable t) {

            }
        });
    }

    public void upFlile(String path) {


        QiniuUtils.getInstance().upFile(path, token, new QiniuUtils.CompleteListener() {
            @Override
            public void completeListener(String urls) {


                getUpImgSuccdess(urls);

            }
        });

    }

    private void notifyImageDataChange() {
        if (img_path == null || img_path.size() == 0) {
            ivAddPhoto.setVisibility(View.VISIBLE);
            piuvRemarkImage.setVisibility(View.GONE);
        } else {
            piuvRemarkImage.setVisibility(View.VISIBLE);
            ivAddPhoto.setVisibility(View.GONE);
        }
        piuvRemarkImage.setData(img_path);
    }

    public void  showAddPhotoWindow(int count) {
        View view = LayoutInflater.from(this).inflate(R.layout.view_add_photo_window, null);

        view.findViewById(R.id.tv_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPhotos();
            }
        });
        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWindowAddPhoto.dismiss();


            }
        });
        mWindowAddPhoto = new PhotoPopupWindow(this).bindView(view);
        if (count == 3 || count == 1) {
            mWindowAddPhoto.showAtLocation(llBottomButton, Gravity.BOTTOM, 0, 0);
        } else {
            mWindowAddPhoto.showAtLocation(this.findViewById(R.id.ll_bottom_button), Gravity.BOTTOM, 0, 0);
        }


    }


    public void getPhotos() {
        Picker.from(this)
                .count(count-img_path.size())
                .enableCamera(true)
                .setEngine(new ImageLoaderEngine())
                .setAdd_watermark(false)
                .forResult(66);
        mWindowAddPhoto.dismiss();
    }

    private PhotoPopupWindow mWindowAddPhoto;
    public void sendSuccess() {
        dimessProgress();
        finish();
        showToasts(getString(R.string.fbcg));
        RxBus.getInstance().send(SubscriptionBean.createSendBean(SubscriptionBean.QFRIED_FRESH, ""));
    }


    @Override
    public <T> void toEntity(T entity, int type) {
        sendSuccess();
    }

    @Override
    public void toNextStep(int type) {

    }


    private MarkaBaseDialog dialog;

    public void showPopwindows() {
        TextView sure = null;
        TextView cancel = null;
        TextView title = null;
        EditText reson = null;
        View views = View.inflate(this, R.layout.base_dailog_view, null);
        sure = (TextView) views.findViewById(R.id.txt_sure);
        cancel = (TextView) views.findViewById(R.id.txt_cancel);
        title = (TextView) views.findViewById(R.id.txt_title);

        title.setText(R.string.sftcccbj);
        dialog = BaseDailogManager.getInstance().getBuilder(this).setMessageView(views).create();
        dialog.show();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                dialog.dismiss();

            }
        });
    }
    @Override
    public void showTomast(String msg) {
       dimessProgress();
        haveFous(true);
       showToasts(msg);
    }


        @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
           if(TextUtil.isNotEmpty(etContent.getText().toString())||(img_path.size()>0)){
               showPopwindows();
               return true;
           }
        }
        return super.onKeyDown(keyCode, event);
    }

}
