package com.h6ah4i.android.widget.advrecyclerview.draggable;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import com.h6ah4i.android.widget.advrecyclerview.utils.CustomRecyclerViewUtils;

public class DraggingItemInfo {
    public final int grabbedPositionX;
    public final int grabbedPositionY;
    public final int height;
    public final long id;
    public final int initialItemLeft;
    public final int initialItemTop;
    public final Rect margins;
    public final int spanSize;
    public final int width;

    public DraggingItemInfo(RecyclerView recyclerView, ViewHolder viewHolder, int i, int i2) {
        this.width = viewHolder.itemView.getWidth();
        this.height = viewHolder.itemView.getHeight();
        this.id = viewHolder.getItemId();
        this.initialItemLeft = viewHolder.itemView.getLeft();
        this.initialItemTop = viewHolder.itemView.getTop();
        this.grabbedPositionX = i - this.initialItemLeft;
        this.grabbedPositionY = i2 - this.initialItemTop;
        this.margins = new Rect();
        CustomRecyclerViewUtils.getLayoutMargins(viewHolder.itemView, this.margins);
        this.spanSize = CustomRecyclerViewUtils.getSpanSize(viewHolder);
    }

    private DraggingItemInfo(DraggingItemInfo draggingItemInfo, ViewHolder viewHolder) {
        this.id = draggingItemInfo.id;
        this.width = viewHolder.itemView.getWidth();
        this.height = viewHolder.itemView.getHeight();
        this.margins = new Rect(draggingItemInfo.margins);
        this.spanSize = CustomRecyclerViewUtils.getSpanSize(viewHolder);
        this.initialItemLeft = draggingItemInfo.initialItemLeft;
        this.initialItemTop = draggingItemInfo.initialItemTop;
        float f = ((float) this.width) * 0.5f;
        float f2 = ((float) this.height) * 0.5f;
        float f3 = (((float) draggingItemInfo.grabbedPositionX) - (((float) draggingItemInfo.width) * 0.5f)) + f;
        float f4 = (((float) draggingItemInfo.grabbedPositionY) - (((float) draggingItemInfo.height) * 0.5f)) + f2;
        if (f3 < 0.0f || f3 >= ((float) this.width)) {
            f3 = f;
        }
        this.grabbedPositionX = (int) f3;
        f3 = (f4 < 0.0f || f4 >= ((float) this.height)) ? f2 : f4;
        this.grabbedPositionY = (int) f3;
    }

    public static DraggingItemInfo createWithNewView(DraggingItemInfo draggingItemInfo, ViewHolder viewHolder) {
        return new DraggingItemInfo(draggingItemInfo, viewHolder);
    }
}
