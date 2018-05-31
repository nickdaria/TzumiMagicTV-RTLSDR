package android.support.transition;

import android.view.ViewGroup;

abstract class TransitionManagerStaticsImpl {
    TransitionManagerStaticsImpl() {
    }

    public abstract void beginDelayedTransition(ViewGroup viewGroup);

    public abstract void beginDelayedTransition(ViewGroup viewGroup, TransitionImpl transitionImpl);

    public abstract void go(SceneImpl sceneImpl);

    public abstract void go(SceneImpl sceneImpl, TransitionImpl transitionImpl);
}
