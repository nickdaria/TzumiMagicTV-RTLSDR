package com.anthony.ultimateswipetool.dialogFragment;

import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import com.anthony.ultimateswipetool.SwipeHelper;
import com.anthony.ultimateswipetool.dialogFragment.SwipeLayout.DismissCallbacks;

public class SwipeDialogFragment extends DialogFragment {
    private boolean mSwipeLayoutGenerated = false;
    private boolean mSwipeable = true;
    private boolean mTiltEnabled = true;
    private SwipeLayout swipeLayout;

    class C04121 implements DismissCallbacks {
        C04121() {
        }

        public boolean canDismiss(Object obj) {
            return SwipeDialogFragment.this.isCancelable() && SwipeDialogFragment.this.mSwipeable;
        }

        public void onDismiss(View view, boolean z, Object obj) {
            if (!SwipeDialogFragment.this.onSwipedAway(z)) {
                SwipeDialogFragment.this.dismiss();
            }
        }
    }

    public boolean isSwipeable() {
        return this.mSwipeable;
    }

    public boolean isTiltEnabled() {
        return this.mTiltEnabled;
    }

    public void onStart() {
        super.onStart();
        if (!this.mSwipeLayoutGenerated && getShowsDialog()) {
            Window window = getDialog().getWindow();
            ViewGroup viewGroup = (ViewGroup) window.getDecorView();
            this.swipeLayout = new SwipeLayout(getActivity());
            SwipeHelper.replaceContentView(window, this.swipeLayout);
            this.swipeLayout.addSwipeListener(viewGroup, "layout", new C04121());
            this.swipeLayout.setTiltEnabled(this.mTiltEnabled);
            this.swipeLayout.setClickable(true);
            this.mSwipeLayoutGenerated = true;
        }
    }

    public boolean onSwipedAway(boolean z) {
        return false;
    }

    public void setSwipeable(boolean z) {
        this.mSwipeable = z;
    }

    public void setTiltEnabled(boolean z) {
        this.mTiltEnabled = z;
        if (this.swipeLayout != null) {
            this.swipeLayout.setTiltEnabled(z);
        }
    }
}
