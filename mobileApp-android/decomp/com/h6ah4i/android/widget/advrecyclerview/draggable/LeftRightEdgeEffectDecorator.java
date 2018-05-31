package com.h6ah4i.android.widget.advrecyclerview.draggable;

import android.support.v7.widget.RecyclerView;

class LeftRightEdgeEffectDecorator extends BaseEdgeEffectDecorator {
    public LeftRightEdgeEffectDecorator(RecyclerView recyclerView) {
        super(recyclerView);
    }

    protected int getEdgeDirection(int i) {
        switch (i) {
            case 0:
                return 0;
            case 1:
                return 2;
            default:
                throw new IllegalArgumentException();
        }
    }
}
