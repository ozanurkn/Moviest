package com.ozan.moviest.helper;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

public abstract class BaseFragment<T extends ViewDataBinding> extends android.support.v4.app.Fragment {

    public View mainLayout;
    private ViewDataBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        int layoutId=getLayoutId();
        try {
            binding= DataBindingUtil.inflate(inflater,layoutId, container, false);
            mainLayout=binding.getRoot();
        }catch (NoClassDefFoundError e){
            mainLayout=inflater.inflate(layoutId, container, false);
        }
        mainLayout.setClickable(true);
        initView();
        Controller.deviceLanguage = String.valueOf(Locale.getDefault());
        return mainLayout;
    }

    public T getBinding(){
        return (T) binding;
    }

    public  abstract int getLayoutId();
    public  abstract void initView();
}
