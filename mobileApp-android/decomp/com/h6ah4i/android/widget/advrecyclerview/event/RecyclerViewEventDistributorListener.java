package com.h6ah4i.android.widget.advrecyclerview.event;

public interface RecyclerViewEventDistributorListener {
    void onAddedToEventDistributor(BaseRecyclerViewEventDistributor baseRecyclerViewEventDistributor);

    void onRemovedFromEventDistributor(BaseRecyclerViewEventDistributor baseRecyclerViewEventDistributor);
}
