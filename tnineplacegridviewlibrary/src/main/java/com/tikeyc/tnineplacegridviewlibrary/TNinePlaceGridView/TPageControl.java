package com.tikeyc.tnineplacegridviewlibrary.TNinePlaceGridView;

/**
 * Created by public1 on 2017/5/25.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tikeyc.tnineplacegridviewlibrary.R;

import java.util.ArrayList;

public class TPageControl extends LinearLayout {

    private static final int PAGE_CONTROL_PADDING = 15;

    private int pageNumber;
    private int currentPage = 0;
    private int selectedColor = Color.BLUE;
    private int normalColor = Color.GRAY;

    private ArrayList<TextView> pageCircleList;

    public TPageControl(Context context) {
//        super(context);

        this(context,null);
    }

    public TPageControl(Context context, AttributeSet attrs) {
//        super(context, attrs);
        this(context,attrs,0);
    }



    public TPageControl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initSubView(context,attrs);
    }

    private void initSubView(Context context, AttributeSet attrs) {

        setHorizontalGravity(View.TEXT_ALIGNMENT_GRAVITY);

        TypedArray typedAry = context.obtainStyledAttributes(attrs,
                R.styleable.PageControl);
        pageNumber = typedAry.getInteger(R.styleable.PageControl_column_num,
                0);
        selectedColor = typedAry.getColor(R.styleable.PageControl_color,
                Color.WHITE);
        typedAry.recycle();

//        this.pageCircleList = new ArrayList<TextView>();
//        for (int i = 0; i < columnNum; i++) {
//            TextView circle = new TextView(context);
//            circle.setText("●");
//            circle.setTextColor(color);
//            if (i != 0) {
//                circle.setPadding(PAGE_CONTROL_PADDING, 0, 0, 0);
//            }
//            this.pageCircleList.add(circle);
//            this.addView(circle, new LayoutParams(
//                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//        }
//
//        this.setCurrentPage(0);
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        if (pageCircleList == null)return;
        for (int i = 0; i < this.pageCircleList.size(); i++) {
            TextView circle = this.pageCircleList.get(i);
            circle.setTextColor((i == currentPage) ? selectedColor : normalColor);
        }
    }


    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
        this.pageCircleList = new ArrayList<TextView>();
        for (int i = 0; i < pageNumber; i++) {
            TextView circle = new TextView(getContext());
            circle.setText("●");
            circle.setTextColor(selectedColor);
            if (i != 0) {
                circle.setPadding(PAGE_CONTROL_PADDING, 0, 0, 0);
            }
            this.pageCircleList.add(circle);
            this.addView(circle, new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        }

        setCurrentPage(0);
    }

    public void setSelectedColor(int selectedColor) {
        this.selectedColor = selectedColor;
        setCurrentPage(currentPage);
    }

    public void setNormalColor(int normalColor) {
        this.normalColor = normalColor;
        setCurrentPage(currentPage);
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getSelectedColor() {
        return selectedColor;
    }

    public int getNormalColor() {
        return normalColor;
    }



    public int getCurrentPage() {
        return currentPage;
    }


}