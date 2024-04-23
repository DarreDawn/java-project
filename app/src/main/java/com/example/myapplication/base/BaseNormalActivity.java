package com.example.myapplication.base;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class BaseNormalActivity <VB extends ViewDataBinding> extends AppCompatActivity {
    protected VB mBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, getLayoutId());
        mBinding.setLifecycleOwner(this);
        initData();
    }



    /**
     * 加载布局
     *
     * @return
     */
    protected abstract @LayoutRes int getLayoutId();

    /**
     * 执行业务逻辑
     */
    protected abstract void initData();



    /**
     * 展示toast 输入文字字符
     *
     * @param msg
     */
    protected void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 展示toast 引用string.xml下定义字符
     *
     * @param msg
     */
    protected void showToast(@StringRes int msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁DataBinding
        if (mBinding != null) {
            mBinding.unbind();
        }
    }
}
