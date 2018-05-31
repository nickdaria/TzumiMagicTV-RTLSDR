package android.support.transition;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.ViewGroup;

@TargetApi(14)
@RequiresApi(14)
class VisibilityIcs extends TransitionIcs implements VisibilityImpl {

    private static class VisibilityWrapper extends VisibilityPort {
        private VisibilityInterface mVisibility;

        VisibilityWrapper(VisibilityInterface visibilityInterface) {
            this.mVisibility = visibilityInterface;
        }

        public void captureEndValues(TransitionValues transitionValues) {
            this.mVisibility.captureEndValues(transitionValues);
        }

        public void captureStartValues(TransitionValues transitionValues) {
            this.mVisibility.captureStartValues(transitionValues);
        }

        public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
            return this.mVisibility.createAnimator(viewGroup, transitionValues, transitionValues2);
        }

        public boolean isVisible(TransitionValues transitionValues) {
            return this.mVisibility.isVisible(transitionValues);
        }

        public Animator onAppear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
            return this.mVisibility.onAppear(viewGroup, transitionValues, i, transitionValues2, i2);
        }

        public Animator onDisappear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
            return this.mVisibility.onDisappear(viewGroup, transitionValues, i, transitionValues2, i2);
        }
    }

    VisibilityIcs() {
    }

    public void init(TransitionInterface transitionInterface, Object obj) {
        this.mExternalTransition = transitionInterface;
        if (obj == null) {
            this.mTransition = new VisibilityWrapper((VisibilityInterface) transitionInterface);
        } else {
            this.mTransition = (VisibilityPort) obj;
        }
    }

    public boolean isVisible(TransitionValues transitionValues) {
        return ((VisibilityPort) this.mTransition).isVisible(transitionValues);
    }

    public Animator onAppear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
        return ((VisibilityPort) this.mTransition).onAppear(viewGroup, transitionValues, i, transitionValues2, i2);
    }

    public Animator onDisappear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
        return ((VisibilityPort) this.mTransition).onDisappear(viewGroup, transitionValues, i, transitionValues2, i2);
    }
}
