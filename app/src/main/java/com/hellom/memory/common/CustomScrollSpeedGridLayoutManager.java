package com.hellom.memory.common;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CustomScrollSpeedGridLayoutManager extends GridLayoutManager {

    private double scrollSpeedRatio = 0.7d;

    public CustomScrollSpeedGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CustomScrollSpeedGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public CustomScrollSpeedGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    public void setScrollSpeedRatio(double scrollSpeedRatio) {
        this.scrollSpeedRatio = scrollSpeedRatio;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int tempDy = super.scrollVerticallyBy((int) (scrollSpeedRatio * dy), recycler, state);
        if (tempDy == (int) (scrollSpeedRatio * dy)) {
            return dy;
        }
        return tempDy;
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int tempDx = super.scrollHorizontallyBy((int) (scrollSpeedRatio * dx), recycler, state);
        if (tempDx == (int) (scrollSpeedRatio * dx)) {
            return dx;
        }
        return tempDx;
    }
}
