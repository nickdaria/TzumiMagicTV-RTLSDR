package android.support.transition;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.transition.TransitionSet;

@TargetApi(19)
@RequiresApi(19)
class TransitionSetKitKat extends TransitionKitKat implements TransitionSetImpl {
    private TransitionSet mTransitionSet = new TransitionSet();

    public TransitionSetKitKat(TransitionInterface transitionInterface) {
        init(transitionInterface, this.mTransitionSet);
    }

    public TransitionSetKitKat addTransition(TransitionImpl transitionImpl) {
        this.mTransitionSet.addTransition(((TransitionKitKat) transitionImpl).mTransition);
        return this;
    }

    public int getOrdering() {
        return this.mTransitionSet.getOrdering();
    }

    public TransitionSetKitKat removeTransition(TransitionImpl transitionImpl) {
        this.mTransitionSet.removeTransition(((TransitionKitKat) transitionImpl).mTransition);
        return this;
    }

    public TransitionSetKitKat setOrdering(int i) {
        this.mTransitionSet.setOrdering(i);
        return this;
    }
}
