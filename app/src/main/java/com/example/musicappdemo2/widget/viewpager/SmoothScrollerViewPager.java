package com.example.musicappdemo2.widget.viewpager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;

public class SmoothScrollerViewPager extends ViewPager {
    private float initialXValue = 0f;
    private int direction = SwipeDirection_ALL;
    private RecyclerView.SmoothScroller ownScroller;

    public SmoothScrollerViewPager(Context context) {
        super(context);
        init();
    }

    public SmoothScrollerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setScrollDuration(275);
        setSwipeGestureEnabled(true);
        direction = SwipeDirection_ALL;
    }

    private void setSwipeGestureEnabled(boolean b) {
        // Implement this method if needed
    }

    public void setScrollDuration(int millis) {
        try {
            Class<?> viewPagerClass = ViewPager.class;
            Field scrollerField = viewPagerClass.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            ownScroller = new RecyclerView.SmoothScroller() {
                @Override
                protected void onStart() {

                }

                @Override
                protected void onStop() {

                }

                @Override
                protected void onSeekTargetStep(int dx, int dy, @NonNull RecyclerView.State state, @NonNull Action action) {

                }

                @Override
                protected void onTargetFound(@NonNull View targetView, @NonNull RecyclerView.State state, @NonNull Action action) {

                }
            };
            scrollerField.set(this, ownScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isSwipeAllowed(ev) && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isSwipeAllowed(ev) && super.onInterceptTouchEvent(ev);
    }

    private boolean isSwipeAllowed(MotionEvent event) {
        if (direction == SwipeDirection_ALL) return true;
        if (direction == SwipeDirection_NONE) return false;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            initialXValue = event.getX();
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            try {
                float diffX = event.getX() - initialXValue;
                if (diffX > 0 && direction == SwipeDirection_RIGHT) {
                    return false;
                } else return diffX < 0 || direction != SwipeDirection_LEFT;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static final int SwipeDirection_RIGHT = 1;
    public static final int SwipeDirection_LEFT = 2;
    public static final int SwipeDirection_ALL = 0;
    public static final int SwipeDirection_NONE = -1;
    public static final int SCROLL_MODE_DEFAULT = 250;
    public static final int SCROLL_MODE_MEDIUM = 750;
    public static final int SCROLL_MODE_SLOW = 1000;
    public static final int SCROLL_MODE_ULTRA_SLOW = 2000;
}
