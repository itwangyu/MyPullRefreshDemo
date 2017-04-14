package com.refresh.mypullrefreshdemo;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by wangyu on 2017/4/14 0014.
 */

public class RefreshHeadView extends FrameLayout implements PtrUIHandler {
    private Context context;
    private ImageView iv;
    private TextView tv;
    private AnimationDrawable animationDrawable;

    public RefreshHeadView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    private void initView() {
        View.inflate(context, R.layout.ptrheadview, this);
        iv = (ImageView) findViewById(R.id.iv_ptr);
        tv = (TextView) findViewById(R.id.tv_ptr);
        animationDrawable = (AnimationDrawable) iv.getDrawable();
    }

    public RefreshHeadView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshHeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        //onUIRefreshComplete之后调用，用于复位
        setImageAndText(R.mipmap.ptr_loading_9, "刷新完成...");
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        //开始下拉的时候调用一次
        setImageAndText(R.mipmap.ptr_loading_1, "下拉刷新...");
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        //正在刷新的时候调用，开始帧动画
        iv.setImageDrawable(animationDrawable);
        animationDrawable.start();
        tv.setText("F5的能量女朋友出现了");
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        //刷新完成的时候调用
        animationDrawable.stop();
        setImageAndText(R.mipmap.ptr_loading_9, "刷新完成...");
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        //触发刷新的高度  默认是头部高度的1.2倍
        final int offsetToRefresh = ptrIndicator.getOffsetToRefresh();
        //当前下拉的高度
        final int currentPos = ptrIndicator.getCurrentPosY();
        //计算下拉的百分比
        int persent = (int) (((double) currentPos / offsetToRefresh) * 100);
        //只有在prepare状态的时候才执行下面的替换图片
        if (status!=PtrFrameLayout.PTR_STATUS_PREPARE) {
            return;
        }
        //根据不同的比例替换不同的图片和文字
        if (persent < 50) {
            setImageAndText(R.mipmap.ptr_loading_1, "下拉刷新");
        } else if (persent < 60) {
            setImageAndText(R.mipmap.ptr_loading_2, "下拉刷新");
        } else if (persent < 70) {
            setImageAndText(R.mipmap.ptr_loading_3, "下拉刷新");
        } else if (persent < 80) {
            setImageAndText(R.mipmap.ptr_loading_4, "下拉刷新");
        } else if (persent < 100) {
            setImageAndText(R.mipmap.ptr_loading_5, "下拉刷新");
        } else {
            setImageAndText(R.mipmap.ptr_loading_6, "松开松开~");
        }
    }

    private void setImageAndText(int res, String text) {
        iv.setImageResource(res);
        tv.setText(text);
    }
}
