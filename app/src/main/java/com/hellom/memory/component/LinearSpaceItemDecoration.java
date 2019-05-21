package com.hellom.memory.component;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LinearSpaceItemDecoration extends RecyclerView.ItemDecoration {
    public static final int VERTICAL = 1;
    public static final int HORIZONTAL = 2;

    private int orientation;
    private int space;

    public LinearSpaceItemDecoration(int orientation, int space) {
        if (orientation != VERTICAL && orientation != HORIZONTAL) {
            throw new IllegalArgumentException("Orientation must be VERTICAL or HORIZONTAL!");
        }
        this.orientation = orientation;
        this.space = space;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (orientation == HORIZONTAL) {
            outRect.right = space;
        }
        /*outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = space;
        }*/
    }
}
