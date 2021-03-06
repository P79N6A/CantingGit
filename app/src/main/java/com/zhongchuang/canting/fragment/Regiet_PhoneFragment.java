package com.zhongchuang.canting.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhongchuang.canting.R;;
import com.zhongchuang.canting.activity.CityPickerActivity;
import com.zhongchuang.canting.activity.RegistActivity;
import com.zhongchuang.canting.app.CanTingAppLication;
import com.zhongchuang.canting.been.BaseResponse;
import com.zhongchuang.canting.been.CodeCheckBean;
import com.zhongchuang.canting.been.PhoneBackBean;
import com.zhongchuang.canting.been.UserLoginBean;
import com.zhongchuang.canting.net.netService;
import com.zhongchuang.canting.presenter.RegisterPresenter;
import com.zhongchuang.canting.presenter.impl.RegisterPresenterImpl;
import com.zhongchuang.canting.utils.PhoneCheck;
import com.zhongchuang.canting.utils.SpUtil;
import com.zhongchuang.canting.viewcallback.RegisterViewCallback;
import com.zhongchuang.canting.widget.ClearEditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Administrator on 2017/10/29.
 */

public class Regiet_PhoneFragment extends Fragment implements RegisterViewCallback {


    @BindView(R.id.login_yanzhen_phone)
    ClearEditText loginYanzhenPhone;
    @BindView(R.id.login_yanzhen_send)
    TextView sendCode;
    @BindView(R.id.login_yanzhen_yanzhenma)
    ClearEditText loginYanzhenYanzhenma;
    @BindView(R.id.login_yanzhen_login)
    TextView loginYanzhenLogin;
    @BindView(R.id.regist_ps_xieyi_yes)
    ImageView registPsXieyiYes;
    @BindView(R.id.regisit2_yanzhen_weixin)
    ImageView regisit2YanzhenWeixin;
    @BindView(R.id.regisit2_yanzhen_qq)
    ImageView regisit2YanzhenQq;
    @BindView(R.id.regisit2_yanzhen_weibo)
    ImageView regisit2YanzhenWeibo;
    @BindView(R.id.regist_phone)
    LinearLayout registPhone;
    private Unbinder unbinder;
    @BindView(R.id.txt_choose)
    TextView txt_choose;



    //    private int code;
    private Call<PhoneBackBean> call;

    private RegisterPresenter presenter;

   private TimeCount timeCount;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.regist_phone, container, false);
        unbinder = ButterKnife.bind(this, view);
        timeCount = new TimeCount(60000, 1000);
        presenter = new RegisterPresenterImpl(this);
        txt_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), CityPickerActivity.class),0);
            }
        });
        return view;

    }
    @Override
    public void onResume() {
        super.onResume();
        txt_choose.setText("+"+CanTingAppLication.code+">");
    }
    //计时器
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if(sendCode!=null){
                sendCode.setEnabled(false);
                sendCode.setText(millisUntilFinished / 1000 + getString(R.string.cshq));
            }

        }

        @Override
        public void onFinish() {
            if(sendCode!=null){
                sendCode.setText(getString(R.string.cxhq));
                sendCode.setBackground(getResources().getDrawable(R.drawable.login_selector));
                sendCode.setEnabled(true);
            }

        }
    }
    private String checkNum;
    @OnClick({R.id.login_yanzhen_send, R.id.login_yanzhen_login})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.login_yanzhen_send:
                mobileNumber = loginYanzhenPhone.getText().toString().trim();
                SpUtil.putString(getActivity(), "mobileNumber", (CanTingAppLication.code.equals("86")?"": CanTingAppLication.code)+mobileNumber);
                // Log.d(TAG, "onCreateView1:" + mobileNumber);
//                ChekPhoneNet();
                if (!PhoneCheck.judgePhoneNums(mobileNumber)) {
                    Toast.makeText(getActivity(), R.string.sjhbnsryw, Toast.LENGTH_SHORT).show();
                    return;
                }
                CanTingAppLication.mobileNumber = mobileNumber;
                mDialog = new ProgressDialog(getActivity());
                mDialog.setMessage(getString(R.string.hqyzms));
                mDialog.show();
                presenter.getYzm("1", (CanTingAppLication.code.equals("86")?"": CanTingAppLication.code)+mobileNumber,CanTingAppLication.code);
                break;
            case R.id.login_yanzhen_login:
                checkNum = loginYanzhenYanzhenma.getText().toString().trim();
                // SMSSDK.submitVerificationCode("86", Regiet_PhoneFragment.mobileNumber, registCheckPhoneyanzhen.getText().toString());
                if (TextUtils.isEmpty(checkNum) || checkNum.equals("")) {
                    Toast.makeText(getActivity(), R.string.yzmbnwk, Toast.LENGTH_SHORT).show();
                }

                Map<String, String> map = new HashMap<>();
                map.put("mobileNumber", (CanTingAppLication.code.equals("86")?"": CanTingAppLication.code)+mobileNumber);
                map.put("code", checkNum);
                presenter.checkCode(map);
                mDialog = new ProgressDialog(getActivity());
                mDialog.setMessage(getString(R.string.jxz));
                mDialog.show();
                break;

        }
    }

    private ProgressDialog mDialog;


    //注册号码判断

    private void ChekPhoneNet() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(netService.TOM_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Log.d(TAG, "onCreateView12345: " + netService.BASE_URL);

        netService mNetService = retrofit.create(netService.class);


        Map<String, String> map = new HashMap<>();
        map.put("mobileNumber", mobileNumber);
        map.put("smsType", "1");
//        call = mNetService.getCall(map);


        call.enqueue(new Callback<PhoneBackBean>() {
            @Override
            public void onResponse(Call<PhoneBackBean> call, Response<PhoneBackBean> response) {
                //Gson Gson=new Gson();
                PhoneBackBean mPhoneBackBean = response.body();


            }


            @Override
            public void onFailure(Call<PhoneBackBean> call, Throwable t) {
                if (mDialog != null) {
                    mDialog.dismiss();
                }
                Toast.makeText(getActivity(), R.string.wlbglqcs, Toast.LENGTH_SHORT).show();

            }
        });

    }


    //页面跳转
    private void initCheckNmFragment() {

        RegistActivity parentActivity = (RegistActivity) getActivity();
        parentActivity.repalcePassWordFrag();
        if(mDialog!=null){
            mDialog.dismiss();
        }


    }


    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        timeCount.cancel();
    }


    @Override
    public void onResultSuccess(UserLoginBean userLoginBean) {

    }

    @Override
    public void onFail(int code, String msg) {
        sendCode.setBackground(getResources().getDrawable(R.drawable.login_selector));
        sendCode.setEnabled(true);
        loginYanzhenYanzhenma.setText("");
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }


    @Override
    public void getYzm(BaseResponse mPhoneBackBean) {


//        if ( code == 100) {
//            //Log.d(TAG, "onViewClicked: " + code);
//            Toast.makeText(getActivity(), "此号码已经注册过！", Toast.LENGTH_SHORT).show();
//            return;
//        }
        if(mDialog!=null){
            mDialog.dismiss();
        }
        timeCount.start();
        sendCode.setBackground(getResources().getDrawable(R.drawable.hui_blue_rectangle));
        sendCode.setEnabled(false);
        // 2. 通过sdk发送短信验证
        // SMSSDK.getVerificationCode("86", mobileNumber);
        Toast.makeText(getActivity(), R.string.yzmyfs, Toast.LENGTH_SHORT).show();


    }

    private String mobileNumber;
    private String userInfoId;
    private String token;
    private String ringLetterName;
    private String ringLetterPwd;
    @Override
    public void checkCode(CodeCheckBean mCodeCheckBean) {
        userInfoId = mCodeCheckBean.getData().getUserInfoId();
        token = mCodeCheckBean.getData().getToken();
        ringLetterName = mCodeCheckBean.getData().getRingLetterName();
        ringLetterPwd = mCodeCheckBean.getData().getRingLetterPwd();

        SpUtil.putString(getActivity(), "userInfoId", userInfoId);
        SpUtil.putString(getActivity(), "token", token);
        SpUtil.putString(getActivity(), "ringLetterName", ringLetterName);
        SpUtil.putString(getActivity(), "ringLetterPwd", ringLetterPwd);
        SpUtil.putString(getActivity(), "code", checkNum);
        initCheckNmFragment();
    }

    @Override
    public void setPassWordSuccess(BaseResponse baseResponse) {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
