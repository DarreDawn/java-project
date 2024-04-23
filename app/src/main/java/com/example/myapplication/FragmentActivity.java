package com.example.myapplication;

import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import com.example.myapplication.base.BaseNormalActivity;
import com.example.myapplication.databinding.ActivityFragmentBinding;
import com.example.myapplication.fragment.CompareFragment;
import com.example.myapplication.fragment.HomeFragment;
import com.example.myapplication.fragment.QuestionFragment;

import java.util.ArrayList;
import java.util.List;

public class FragmentActivity extends BaseNormalActivity<ActivityFragmentBinding> {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_fragment;
    }

    @Override
    protected void initData() {

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new QuestionFragment());
        fragments.add(new CompareFragment());
        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager(), fragments);
        mBinding.mainPager.setOffscreenPageLimit(2);
        mBinding.mainPager.setAdapter(adapter);
        mBinding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId ==R.id.tab_home){
                    mBinding.mainPager.setCurrentItem(0, true);
                }else  if (checkedId ==R.id.tab_analyse){
                    mBinding.mainPager.setCurrentItem(1, true);
                }else {
                    mBinding.mainPager.setCurrentItem(2, true);
                }
            }
        });
    }
}
