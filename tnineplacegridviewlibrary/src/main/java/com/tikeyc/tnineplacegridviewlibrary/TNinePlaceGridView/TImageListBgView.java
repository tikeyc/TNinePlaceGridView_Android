package com.tikeyc.tnineplacegridviewlibrary.TNinePlaceGridView;

import android.animation.Animator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.List;

/**
 * Created by public1 on 2017/5/23.
 */

public class TImageListBgView extends RelativeLayout {

    public int mState = TScallImageView.STATE_NORMAL;
    private TRect originalRect;
    public List<TRect> originalRects;
    public Integer imageId;
    public List<Integer> imageIds;
    public int currentIndex;
    private ImageView animationIV;
    private LinearLayout gridViewBgView;
    private GridView gridView;
    private TPageHorizatalScrollView horizontalScrollView;
    private TPageControl pageControl;

    public TImageListBgView(Context context, TRect originalRect,Integer imageId,List<Integer> imageIds,int currentIndex) {
        super(context);
        this.originalRect = originalRect;
        this.imageId = imageId;
        this.imageIds = imageIds;
        this.currentIndex = currentIndex;
        setBackgroundColor(Color.TRANSPARENT);
        initSubViews();
    }

    public TImageListBgView(Context context) {
        super(context);
    }

    public TImageListBgView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TImageListBgView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**因此View添加在Window，点击手机返回按钮无法响应，
     * 重写此方法可以处理点击手机返回的逻辑处理，缩小图片到原位置
     * @param event
     * @return
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {//2-4
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (mState == TScallImageView.STATE_TRANSFORM_IN) {
                startTransform(TScallImageView.STATE_TRANSFORM_OUT);
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {//1-3
        return super.dispatchKeyEventPreIme(event);
    }


    private WindowManager windowManager;
    private void initSubViews() {
        //
        initImageListBgView();
        //
        initAnimationIV();
        //
        initHorizontalScrollView();
        //
        initGridView();
        //
        initPageControl();
    }

    private void initImageListBgView() {
        Activity activity = (Activity) getContext();
        windowManager = activity.getWindowManager();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = TRect.getScreenWidth(getContext());
        layoutParams.height = TRect.getScreenHeight(getContext());
        //FLAG_LAYOUT_IN_SCREEN
        layoutParams.flags = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        layoutParams.format = PixelFormat.RGBA_8888;//让背景透明，放大过程可以看到当前界面
        layoutParams.verticalMargin = 0;
        windowManager.addView(this,layoutParams);
    }

    private void initAnimationIV() {
        animationIV = new ImageView(getContext());
//        imageView.setBackgroundColor(Color.RED);
        animationIV.setScaleType(ImageView.ScaleType.FIT_CENTER);
        LayoutParams params = new LayoutParams(originalRect.getWidth(),originalRect.getHeight());
        params.leftMargin = originalRect.getLeft();
        params.topMargin = originalRect.getTop();
        addView(animationIV,params);
//        Picasso.with(getContext()).load("http://ww2.sinaimg.cn/mw690/9e6995c9gw1f2uu70bzohj209q06g3yw.jpg").into(animationIV);
        animationIV.setImageResource(imageId);
    }

    private void initHorizontalScrollView() {
        horizontalScrollView = new TPageHorizatalScrollView(getContext());
        LayoutParams hsLayoutParams = new LayoutParams(TRect.getScreenWidth(getContext()),TRect.getScreenHeight(getContext()));
        hsLayoutParams.leftMargin = 0;
        hsLayoutParams.topMargin = 0;
        addView(horizontalScrollView,hsLayoutParams);
        horizontalScrollView.mBaseScrollX = currentIndex*TRect.getScreenWidth(getContext());
        horizontalScrollView.setOnScrollToIndexListen(new TPageHorizatalScrollView.OnScrollToIndexListen() {
            @Override
            public void scrollToIndex(int index) {
                currentIndex = index;
                if (currentIndex >= imageIds.size()){
                    currentIndex = imageIds.size() - 1;
                } else if (currentIndex < 0) {
                    currentIndex = 0;
                }
                Log.e("TAG","currentIndex" + currentIndex);
                animationIV.setImageResource(imageIds.get(currentIndex));
                originalRect = originalRects.get(currentIndex);
                pageControl.setCurrentPage(currentIndex);
            }
        });
        int numColumns = imageIds.size();
        gridViewBgView = new LinearLayout(getContext());
        LinearLayout.LayoutParams testParams = new LinearLayout.LayoutParams(TRect.getScreenWidth(getContext())*numColumns,TRect.getScreenHeight(getContext()));
        horizontalScrollView.addView(gridViewBgView,testParams);
    }


    private void initGridView() {
        int numColumns = imageIds.size();
        //
        gridView = new GridView(getContext());
        gridView.setNumColumns(numColumns);
        gridView.setColumnWidth(TRect.getScreenWidth(getContext()));
        LinearLayout.LayoutParams gridViewLayoutParams = new LinearLayout.LayoutParams(TRect.getScreenWidth(getContext())*numColumns,TRect.getScreenHeight(getContext()));
        gridViewLayoutParams.leftMargin = 0;
        gridViewLayoutParams.topMargin = 0;
        gridViewBgView.addView(gridView,gridViewLayoutParams);
        final TImageGridViewAdapter adapter = new TImageGridViewAdapter(getContext());
        adapter.imageIds = this.imageIds;
        gridView.setAdapter(adapter);
        adapter.setOnItemClickListener(new TImageGridViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int i, View view) {
                startTransform(TScallImageView.STATE_TRANSFORM_OUT);
            }
        });

//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                startTransform(TScallImageView.STATE_TRANSFORM_OUT);
//            }
//        });
    }

    private void initPageControl() {
        pageControl = new TPageControl(getContext(),null);
        pageControl.setPageNumber(imageIds.size());
        pageControl.setSelectedColor(Color.RED);
        LayoutParams layoutParams = new LayoutParams(TRect.getScreenWidth(getContext()),40);
        layoutParams.topMargin = TRect.getScreenHeight(getContext()) - 100;
        addView(pageControl,layoutParams);
    }

    /**放大缩小动画
     * @param state
     */
    public void startTransform(final int state) {

        final int duration = 300;
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(duration);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        if (state == TScallImageView.STATE_TRANSFORM_IN) {
            setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            mState = TScallImageView.STATE_TRANSFORM_IN;
            gridViewBgView.setVisibility(INVISIBLE);
            pageControl.setVisibility(INVISIBLE);
//            PropertyValuesHolder scaleHolder = PropertyValuesHolder.ofFloat("scale", mTransfrom.startScale, mTransfrom.endScale);
            PropertyValuesHolder leftHolder = PropertyValuesHolder.ofFloat("left",originalRect.getLeft(), 0);
            PropertyValuesHolder topHolder = PropertyValuesHolder.ofFloat("top", originalRect.getTop(), 0);
            PropertyValuesHolder widthHolder = PropertyValuesHolder.ofFloat("width", originalRect.getWidth(), TRect.getScreenWidth(getContext()));
            PropertyValuesHolder heightHolder = PropertyValuesHolder.ofFloat("height", originalRect.getHeight(), TRect.getScreenHeight(getContext()));
            PropertyValuesHolder alphaHolder = PropertyValuesHolder.ofInt("alpha", 0, 255);
            valueAnimator.setValues(leftHolder, topHolder, widthHolder, heightHolder, alphaHolder);
        } else {
//            setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
            gridViewBgView.setVisibility(INVISIBLE);
            pageControl.setVisibility(INVISIBLE);
            animationIV.setVisibility(VISIBLE);
            setBackgroundColor(Color.TRANSPARENT);
            mState = TScallImageView.STATE_TRANSFORM_OUT;
//            PropertyValuesHolder scaleHolder = PropertyValuesHolder.ofFloat("scale", mTransfrom.endScale, mTransfrom.startScale);
            PropertyValuesHolder leftHolder = PropertyValuesHolder.ofFloat("left", animationIV.getLeft(), originalRect.getLeft());
            PropertyValuesHolder topHolder = PropertyValuesHolder.ofFloat("top", animationIV.getTop(), originalRect.getTop());
            PropertyValuesHolder widthHolder = PropertyValuesHolder.ofFloat("width", animationIV.getWidth(), originalRect.getWidth());
            PropertyValuesHolder heightHolder = PropertyValuesHolder.ofFloat("height", animationIV.getHeight(), originalRect.getHeight());
            PropertyValuesHolder alphaHolder = PropertyValuesHolder.ofInt("alpha", 255, 0);
            valueAnimator.setValues(leftHolder, topHolder, widthHolder, heightHolder, alphaHolder);

//            Handler handler = new Handler(){
//                @Override
//                public void handleMessage(Message msg) {
//                    super.handleMessage(msg);
//                    animationIV.setScaleType(ImageView.ScaleType.CENTER_CROP);//根据九宫格中的图片的显示模式设置
//                }
//            };
//            handler.sendEmptyMessageDelayed(0,duration*9/10);
        }

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public synchronized void onAnimationUpdate(ValueAnimator animation) {
                //                mTransfrom.scale = (Float) animation.getAnimatedValue("scale");
                Float left = (Float) animation.getAnimatedValue("left");
                Float top = (Float) animation.getAnimatedValue("top");
                Float width = (Float) animation.getAnimatedValue("width");
                Float height = (Float) animation.getAnimatedValue("height");
                Integer mBgAlpha = (Integer) animation.getAnimatedValue("alpha");

                LayoutParams layoutParams = new LayoutParams(width.intValue(),height.intValue());
                layoutParams.leftMargin = left.intValue();
                layoutParams.topMargin = top.intValue();
                animationIV.setLayoutParams(layoutParams);
                setAlpha(mBgAlpha);
            }
        });
        final TImageListBgView[] imageListBgView = {this};
        valueAnimator.addListener(new ValueAnimator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                /*
                 * 如果是进入的话，当然是希望最后停留在center_crop的区域。但是如果是out的话，就不应该是center_crop的位置了
                 * ， 而应该是最后变化的位置，因为当out的时候结束时，不回复视图是Normal，要不然会有一个突然闪动回去的bug
                 */
                // TODO 这个可以根据实际需求来修改
                if (mState == TScallImageView.STATE_TRANSFORM_IN) {
                    horizontalScrollView.baseSmoothScrollTo(0);
                    setBackgroundColor(Color.BLACK);
                    pageControl.setVisibility(VISIBLE);
                    Handler handler = new Handler(){
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            gridViewBgView.setVisibility(VISIBLE);
                            animationIV.setVisibility(INVISIBLE);
                        }
                    };
                    handler.sendEmptyMessageDelayed(0, duration);
                } else if (mState == TScallImageView.STATE_TRANSFORM_OUT) {
                    gridViewBgView.removeView(gridView);
                    gridView = null;
                    horizontalScrollView.removeView(gridViewBgView);
                    gridViewBgView = null;
                    removeView(animationIV);
                    animationIV = null;
                    removeView(pageControl);
                    pageControl = null;
                    windowManager.removeView(imageListBgView[0]);
                    imageListBgView[0] = null;
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }
        });
        valueAnimator.start();


    }

}

