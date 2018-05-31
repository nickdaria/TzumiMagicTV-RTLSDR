package android.support.transition;

interface TransitionSetImpl {
    TransitionSetImpl addTransition(TransitionImpl transitionImpl);

    int getOrdering();

    TransitionSetImpl removeTransition(TransitionImpl transitionImpl);

    TransitionSetImpl setOrdering(int i);
}
