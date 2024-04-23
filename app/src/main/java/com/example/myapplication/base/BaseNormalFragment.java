package com.example.myapplication.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

public abstract class BaseNormalFragment <VB extends ViewDataBinding> extends Fragment {
    protected VB mBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        mBinding =  DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        mBinding.setLifecycleOwner(this);
        initData();
        return mBinding.getRoot();
    }

    /**
     * 加载布局
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 执行业务逻辑
     */
    protected abstract void initData();


    /**
     * 展示toast 输入文字字符
     * @param msg
     */
    protected void showToast(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }

    /**
     * 展示toast 引用string.xml下定义字符
     * @param msg
     */
    protected void showToast(@StringRes int msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }
}
