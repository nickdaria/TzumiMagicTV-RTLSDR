package android.support.transition;

import android.animation.Animator;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

public abstract class Visibility extends Transition implements VisibilityInterface {
    public Visibility() {
        this(false);
    }

    Visibility(boolean z) {
        super(true);
        if (!z) {
            if (VERSION.SDK_INT >= 19) {
                this.mImpl = new VisibilityKitKat();
            } else {
                this.mImpl = new VisibilityIcs();
            }
            this.mImpl.init(this);
        }
    }

    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        this.mImpl.captureEndValues(transitionValues);
    }

    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        this.mImpl.captureStartValues(transitionValues);
    }

    public boolean isVisible(TransitionValues transitionValues) {
        return ((VisibilityImpl) this.mImpl).isVisible(transitionValues);
    }

    public Animator onAppear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
        return ((VisibilityImpl) this.mImpl).onAppear(viewGroup, transitionValues, i, transitionValues2, i2);
    }

    public Animator onDisappear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
        return ((VisibilityImpl) this.mImpl).onDisappear(viewGroup, transitionValues, i, transitionValues2, i2);
    }
}
