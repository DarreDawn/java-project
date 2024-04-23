package com.example.myapplication;

import android.view.View;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

public class AddressAdapter extends BaseQuickAdapter<Address, BaseViewHolder> {

    IAddressListener listener;
    public AddressAdapter( IAddressListener listener) {
        super(R.layout.item_address);
        this.listener = listener;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, Address address) {
        baseViewHolder.setText(R.id.tv_address,address.address);
        baseViewHolder.getView(R.id.tv_address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onAddressClick(address);
                }
            }
        });
    }
}
