package com.tikeyc.tnineplacegridviewlibrary.TNinePlaceGridView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * Created by public1 on 2017/5/23.
 */

public class TPageHorizatalScrollView extends HorizontalScrollView {

    public int mBaseScrollX;//滑动基线。也就是点击并滑动之前的x值，以此值计算相对滑动距离。
    private int mScreenWidth;
    private int mScreenHeight;
    private int mPageCount;//页面数量

    private int mScrollX = 200;//滑动多长距离翻页


    public TPageHorizatalScrollView(Context context) {
        super(context);

        init(context);
    }

    public TPageHorizatalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }



    public TPageHorizatalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private void init(Context context) {
        DisplayMetrics dm = context.getApplicationContext().getResources()
                .getDisplayMetrics();
        mScreenWidth = dm.widthPixels;
        mScreenHeight = dm.heightPixels;
    }

    /**
     * 获取页面数量
     * @return
     */
    public int getPageCount() {
        return mPageCount;
    }

    /**
     * 获取相对滑动位置。由右向左滑动，返回正值；由左向右滑动，返回负值。
     * @return
     */
    private int getBaseScrollX() {
        return getScrollX() - mBaseScrollX;
    }

    /**
     * 使相对于基线移动x距离。
     * @param x x为正值时右移；为负值时左移。
     */
    public void baseSmoothScrollTo(int x) {
        smoothScrollTo(x + mBaseScrollX, 0);
        if (onScrollToIndexListen != null) {
            int index = (x + mBaseScrollX)/mScreenWidth;
            onScrollToIndexListen.scrollToIndex(index);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                int scrollX = getBaseScrollX();
                //左滑，大于一半，移到下一页
                if (scrollX > mScrollX) {
                    baseSmoothScrollTo(mScreenWidth);
                    mBaseScrollX += mScreenWidth;
                }
                //左滑，不到一半，返回原位
                else if (scrollX > 0) {
                    baseSmoothScrollTo(0);
                }
                //右滑，不到一半，返回原位
                else if(scrollX > -mScrollX) {
                    baseSmoothScrollTo(0);
                }
                //右滑，大于一半，移到下一页
                else {
                    baseSmoothScrollTo(-mScreenWidth);
                    mBaseScrollX -= mScreenWidth;
                }
                return false;
        }
        return super.onTouchEvent(ev);
    }

    private OnScrollToIndexListen onScrollToIndexListen;

    public void setOnScrollToIndexListen(OnScrollToIndexListen onScrollToIndexListen) {
        this.onScrollToIndexListen = onScrollToIndexListen;
    }

    public OnScrollToIndexListen getOnScrollToIndexListen() {
        return onScrollToIndexListen;
    }

    public interface OnScrollToIndexListen {
        void scrollToIndex(int index);
    }

}
