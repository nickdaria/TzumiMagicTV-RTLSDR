package com.anthony.ultimateswipetool.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.anthony.ultimateswipetool.SwipeHelper;

public class AbsSwipeBackActivity extends AppCompatActivity implements SwipeBackActivityBase {
    private SwipeHelper mHelper;

    public View findViewById(int i) {
        View findViewById = super.findViewById(i);
        return (findViewById != null || this.mHelper == null) ? findViewById : this.mHelper.findViewById(i);
    }

    public SwipeBackLayout getSwipeBackLayout() {
        return this.mHelper.getSwipeBackLayout();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mHelper = new SwipeHelper(this);
        this.mHelper.onActivityCreate();
    }

    protected void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        this.mHelper.onPostCreate();
    }

    public void scrollToFinishActivity() {
        SwipeHelper.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

    public void setScrollDirection(int i) {
        getSwipeBackLayout().setEdgeTrackingEnabled(i);
    }

    public void setSwipeBackEnable(boolean z) {
        getSwipeBackLayout().setEnableGesture(z);
    }
}
