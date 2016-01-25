package com.wuyinlei.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;


public class CustomRelativeLayout extends RelativeLayout {
    private DragLayout dl;

    public CustomRelativeLayout(Context context) {
        super(context);
    }

    public CustomRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setDragLayout(DragLayout dl) {
        this.dl = dl;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (dl.getStatus() != DragLayout.Status.Close) {
            return true;
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (dl.getStatus() != DragLayout.Status.Close) {
            //侧滑菜单如果是打开的状态，并且此时触摸已经抬起(点击的不是侧滑菜单区域)
            if (event.getAction() == MotionEvent.ACTION_UP) {
                dl.close();//侧滑菜单隐藏
            }
            return true;
        }
        return super.onTouchEvent(event);
    }

}
