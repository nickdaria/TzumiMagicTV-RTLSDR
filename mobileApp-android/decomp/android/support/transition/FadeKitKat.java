package android.support.transition;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.transition.Fade;
import android.view.ViewGroup;

@TargetApi(19)
@RequiresApi(19)
class FadeKitKat extends TransitionKitKat implements VisibilityImpl {
    public FadeKitKat(TransitionInterface transitionInterface) {
        init(transitionInterface, new Fade());
    }

    public FadeKitKat(TransitionInterface transitionInterface, int i) {
        init(transitionInterface, new Fade(i));
    }

    public boolean isVisible(TransitionValues transitionValues) {
        return ((Fade) this.mTransition).isVisible(TransitionKitKat.convertToPlatform(transitionValues));
    }

    public Animator onAppear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
        return ((Fade) this.mTransition).onAppear(viewGroup, TransitionKitKat.convertToPlatform(transitionValues), i, TransitionKitKat.convertToPlatform(transitionValues2), i2);
    }

    public Animator onDisappear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
        return ((Fade) this.mTransition).onDisappear(viewGroup, TransitionKitKat.convertToPlatform(transitionValues), i, TransitionKitKat.convertToPlatform(transitionValues2), i2);
    }
}
