package com.lz.fram.base;


import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lz.fram.inject.InjectManager;
import com.lz.fram.inject.PresenterProviders;

import io.reactivex.annotations.NonNull;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-07-16       创建class
 */
public abstract class FramBaseFragment extends Fragment implements VisibleFragmentOwner, BaseView {


    protected Context mContext;
    private PresenterProviders mPresenterProviders;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View inflate = InjectManager.inject(this, inflater, container);
        mPresenterProviders = InjectManager.attachPresenter(this, getLifecycle());
        return inflate;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initLisenter();
    }

    protected abstract void initData();

    protected void initLisenter() {
    }

    @Nullable
    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void showErrorMsg(String msg) {

    }


    private VisibleFragmentProxy visibleFragmentProxy = new VisibleFragmentProxy(this);

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        visibleFragmentProxy.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        visibleFragmentProxy.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        visibleFragmentProxy.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        visibleFragmentProxy.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        visibleFragmentProxy.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        visibleFragmentProxy.onDestroy();
        if (mPresenterProviders != null) {
            mPresenterProviders.clear();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        visibleFragmentProxy.onHiddenChanged(hidden);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        visibleFragmentProxy.onConfigurationChanged(newConfig);
    }

    /**
     * 懒加载，在view初始化完成之前执行
     * On lazy after view.
     */
    @Override
    public void onLazyBeforeView() {
    }

    /**
     * 懒加载，在view初始化完成之后执行
     * On lazy before view.
     */
    @Override
    public void onLazyAfterView() {
    }

    /**
     * Fragment用户可见时候调用
     * On visible.
     */
    @Override
    public void onVisible() {
    }

    /**
     * Fragment用户不可见时候调用
     * On invisible.
     */
    @Override
    public void onInvisible() {
    }


}
