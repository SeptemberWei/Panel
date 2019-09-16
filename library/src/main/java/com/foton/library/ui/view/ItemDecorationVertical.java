package com.foton.library.ui.view;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Item space
 */
public class ItemDecorationVertical extends RecyclerView.ItemDecoration {
    private int mSpace;

    public ItemDecorationVertical(int space) {
        this.mSpace = space;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int pos = parent.getChildLayoutPosition(view);  //当前条目的position
        int itemCount = state.getItemCount() - 1;           //最后一条的postion
        if (pos == itemCount) {   //最后一条
            outRect.bottom = mSpace;
            outRect.top = mSpace;
        } else {
            outRect.top = mSpace;
        }
    }
}
