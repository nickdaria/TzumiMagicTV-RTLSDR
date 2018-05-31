package android.support.transition;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;

@TargetApi(23)
@RequiresApi(23)
class TransitionApi23 extends TransitionKitKat {
    TransitionApi23() {
    }

    public TransitionImpl removeTarget(int i) {
        this.mTransition.removeTarget(i);
        return this;
    }
}
