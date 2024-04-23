package com.example.myapplication.fragment;

import android.view.View;
import android.widget.RadioGroup;

import com.example.myapplication.App;
import com.example.myapplication.Danxuan;
import com.example.myapplication.R;
import com.example.myapplication.base.BaseNormalFragment;
import com.example.myapplication.databinding.FragmentQuestionBinding;

import org.xutils.ex.DbException;

import java.util.Collections;
import java.util.List;

public class QuestionFragment extends BaseNormalFragment<FragmentQuestionBinding> {

    List<Danxuan> panduanList;
    int COUNT = 0;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_question;
    }

    @Override
    protected void initData() {
        mBinding.llAnswer.setVisibility(View.GONE);
        mBinding.llYour.setVisibility(View.GONE);
        panduanList = getPanduanList();
        mBinding.radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId ==R.id.radioButton_a){
                    check("a");
                }else if (checkedId ==R.id.radioButton_b){
                    check("b");
                }else if (checkedId ==R.id.radioButton_c){
                    check("c");
                }else {
                    check("d");
                }
                mBinding.llAnswer.setVisibility(View.VISIBLE);
                mBinding.llYour.setVisibility(View.VISIBLE);
                disableRadioGroup(mBinding.radioGroupGender);
            }
        });
        mBinding.tvQuestion.setText((COUNT+1)+"："+panduanList.get(COUNT).getQuestion());
        mBinding.radioButtonA.setText(panduanList.get(COUNT).getA());
        mBinding.radioButtonB.setText(panduanList.get(COUNT).getB());
        mBinding.radioButtonC.setText(panduanList.get(COUNT).getC());
        mBinding.radioButtonD.setText(panduanList.get(COUNT).getD());
        mBinding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (COUNT== panduanList.size()-1){
                    showToast(
                            "It's the last question！！！");
                    return;
                }
                ++COUNT;
                clearRadioGroup(mBinding.radioGroupGender);

                mBinding.tvQuestion.setText((COUNT+1)+"："+panduanList.get(COUNT).getQuestion());
                mBinding.radioButtonA.setText(panduanList.get(COUNT).getA());
                mBinding.radioButtonB.setText(panduanList.get(COUNT).getB());
                mBinding.radioButtonC.setText(panduanList.get(COUNT).getC());
                mBinding.radioButtonD.setText(panduanList.get(COUNT).getD());
                mBinding.llAnswer.setVisibility(View.GONE);
                mBinding.llYour.setVisibility(View.GONE);
                enableRadioGroup(mBinding.radioGroupGender);
            }
        });
    }

    /**
     * 获取单选题
     * @return
     */
    private List<Danxuan> getPanduanList(){
        List<Danxuan> panduanList = App.getInstance().danxuanList;
        if (panduanList ==null){
            return null;
        }
        //打乱
        Collections.shuffle(panduanList);
        return panduanList;
    }

    /**
     *
     * @param testRadioGroup
     *        RadioGroup 不可点击
     */
    public void disableRadioGroup(RadioGroup testRadioGroup) {
        for (int i = 0; i < testRadioGroup.getChildCount(); i++) {
            testRadioGroup.getChildAt(i).setEnabled(false);
        }
    }

    /**
     *
     * @param testRadioGroup
     *       RadioGroup 可点击
     */
    public void enableRadioGroup(RadioGroup testRadioGroup) {
        for (int i = 0; i < testRadioGroup.getChildCount(); i++) {
            testRadioGroup.getChildAt(i).setEnabled(true);
        }
    }
    private void check(String msg){
        if (msg.equals(panduanList.get(COUNT).getAnswer().toLowerCase())){
            mBinding.tvAnswer.setText(panduanList.get(COUNT).getAnswer().toLowerCase());
            mBinding.tvYouAnswer.setText(msg.toLowerCase());
            mBinding.tvYouAnswer.setTextColor(getResources().getColor(R.color.teal_200));
        }else {
            mBinding.tvYouAnswer.setTextColor(getResources().getColor(R.color.red));
            mBinding.tvAnswer.setText(panduanList.get(COUNT).getAnswer().toLowerCase());
            mBinding.tvYouAnswer.setText(msg.toLowerCase());
        }
    }

    /**
     * 清空选项
     * @param testRadioGroup
     */
    public void clearRadioGroup(RadioGroup testRadioGroup) {
        mBinding.radioButtonA.setChecked(false);
        mBinding.radioButtonB.setChecked(false);
        mBinding.radioButtonC.setChecked(false);
        mBinding.radioButtonD.setChecked(false);
    }
}
