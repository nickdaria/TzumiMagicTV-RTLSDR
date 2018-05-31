package android.support.transition;

abstract class TransitionManagerImpl {
    TransitionManagerImpl() {
    }

    public abstract void setTransition(SceneImpl sceneImpl, SceneImpl sceneImpl2, TransitionImpl transitionImpl);

    public abstract void setTransition(SceneImpl sceneImpl, TransitionImpl transitionImpl);

    public abstract void transitionTo(SceneImpl sceneImpl);
}
