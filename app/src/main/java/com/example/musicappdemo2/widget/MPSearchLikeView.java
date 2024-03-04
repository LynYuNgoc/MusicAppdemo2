package com.example.musicappdemo2.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.musicappdemo2.R;

public class MPSearchLikeView extends ConstraintLayout {
    public MPSearchLikeView(Context context) {
        super(context);
        init(context, null);
    }

    public MPSearchLikeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MPSearchLikeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.compound_search_like_view, this, true);
    }
}
