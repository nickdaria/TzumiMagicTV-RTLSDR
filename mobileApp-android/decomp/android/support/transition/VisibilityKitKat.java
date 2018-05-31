package android.support.transition;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.view.ViewGroup;

@TargetApi(19)
@RequiresApi(19)
class VisibilityKitKat extends TransitionKitKat implements VisibilityImpl {

    private static class VisibilityWrapper extends Visibility {
        private final VisibilityInterface mVisibility;

        VisibilityWrapper(VisibilityInterface visibilityInterface) {
            this.mVisibility = visibilityInterface;
        }

        public void captureEndValues(TransitionValues transitionValues) {
            TransitionKitKat.wrapCaptureEndValues(this.mVisibility, transitionValues);
        }

        public void captureStartValues(TransitionValues transitionValues) {
            TransitionKitKat.wrapCaptureStartValues(this.mVisibility, transitionValues);
        }

        public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
            return this.mVisibility.createAnimator(viewGroup, TransitionKitKat.convertToSupport(transitionValues), TransitionKitKat.convertToSupport(transitionValues2));
        }

        public boolean isVisible(TransitionValues transitionValues) {
            if (transitionValues == null) {
                return false;
            }
            TransitionValues transitionValues2 = new TransitionValues();
            TransitionKitKat.copyValues(transitionValues, transitionValues2);
            return this.mVisibility.isVisible(transitionValues2);
        }

        public Animator onAppear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
            return this.mVisibility.onAppear(viewGroup, TransitionKitKat.convertToSupport(transitionValues), i, TransitionKitKat.convertToSupport(transitionValues2), i2);
        }

        public Animator onDisappear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
            return this.mVisibility.onDisappear(viewGroup, TransitionKitKat.convertToSupport(transitionValues), i, TransitionKitKat.convertToSupport(transitionValues2), i2);
        }
    }

    VisibilityKitKat() {
    }

    public void init(TransitionInterface transitionInterface, Object obj) {
        this.mExternalTransition = transitionInterface;
        if (obj == null) {
            this.mTransition = new VisibilityWrapper((VisibilityInterface) transitionInterface);
        } else {
            this.mTransition = (Visibility) obj;
        }
    }

    public boolean isVisible(TransitionValues transitionValues) {
        return ((Visibility) this.mTransition).isVisible(TransitionKitKat.convertToPlatform(transitionValues));
    }

    public Animator onAppear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
        return ((Visibility) this.mTransition).onAppear(viewGroup, TransitionKitKat.convertToPlatform(transitionValues), i, TransitionKitKat.convertToPlatform(transitionValues2), i2);
    }

    public Animator onDisappear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
        return ((Visibility) this.mTransition).onDisappear(viewGroup, TransitionKitKat.convertToPlatform(transitionValues), i, TransitionKitKat.convertToPlatform(transitionValues2), i2);
    }
}
