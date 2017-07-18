package com.anxindeli.library.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.anxindeli.library.R;

/**
 * Fragment的基类，所有Fragment都要继承此类
 * Created by Adam_Lee on 2015/9/2.
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener{

    public boolean isAttached;
    protected  Activity mActivity;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        isAttached = true;
        mActivity=activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeViewData();
    }

    /**
     * 在View初始化之前，需要初始化数据的，实现此方法
     */
    public abstract void beforeViewData();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initLayout(inflater, container, savedInstanceState);
    }

    /**
     * 初化布局文件实现此方法
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public abstract View initLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        requestNetData();
    }

    /**
     * 空方法待实现
     */
    public void onShow(){}

    /**
     * 初始化View实现此方法,并设置需要点击的控件的监听
     * @param view
     * @param savedInstanceState
     */
    public abstract void initView(View view, Bundle savedInstanceState);

    /**
     * 请求网络数据实现此方法
     */
    public abstract void requestNetData();

    /**
     * 重写该方法加入进出动画
     * @param intent
     */
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        getActivity().overridePendingTransition(R.anim.in_to_left, R.anim.out_to_right);
    }

    /**
     *  重写该方法加入进出动画
     * @param intent
     * @param requestCode
     */
    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        getActivity().overridePendingTransition(R.anim.in_to_left, R.anim.out_to_right);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        isAttached = false;
    }

}
