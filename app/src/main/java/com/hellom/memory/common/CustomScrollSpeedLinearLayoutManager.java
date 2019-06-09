package com.hellom.memory.common;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CustomScrollSpeedLinearLayoutManager extends LinearLayoutManager {
    private double scrollSpeedRatio = 0.7d;

    public void setScrollSpeedRatio(double scrollSpeedRatio) {
        this.scrollSpeedRatio = scrollSpeedRatio;
    }

    public CustomScrollSpeedLinearLayoutManager(Context context) {
        super(context);
    }

    public CustomScrollSpeedLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public CustomScrollSpeedLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
