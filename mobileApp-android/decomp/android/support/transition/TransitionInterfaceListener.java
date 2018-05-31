package android.support.transition;

interface TransitionInterfaceListener<TransitionT extends TransitionInterface> {
    void onTransitionCancel(TransitionT transitionT);

    void onTransitionEnd(TransitionT transitionT);

    void onTransitionPause(TransitionT transitionT);

    void onTransitionResume(TransitionT transitionT);

    void onTransitionStart(TransitionT transitionT);
}
