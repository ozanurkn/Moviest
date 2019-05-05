package com.ozan.moviest.helper;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity {

    protected View mainView;
    protected ViewDataBinding binding;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        int layoutId = getLayoutId();
        super.onCreate(savedInstanceState);
        try{
            binding = DataBindingUtil.setContentView(this,layoutId);
            if (binding != null){
                mainView = binding.getRoot();
            }else {
                mainView = LayoutInflater.from(this).inflate(layoutId,null);
                setContentView(mainView);
            }
        }catch (NoClassDefFoundError e){
            mainView = LayoutInflater.from(this).inflate(layoutId,null);
        }
        Controller.currentContext = getContext();
        initView();
    }

    public T getBinding() {
        return (T) binding;
    }
    protected abstract Context getContext();
    protected abstract void initView();
    protected abstract int getLayoutId();
}
