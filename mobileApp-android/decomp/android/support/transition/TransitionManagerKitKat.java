package android.support.transition;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.transition.TransitionManager;

@TargetApi(19)
@RequiresApi(19)
class TransitionManagerKitKat extends TransitionManagerImpl {
    private final TransitionManager mTransitionManager = new TransitionManager();

    TransitionManagerKitKat() {
    }

    public void setTransition(SceneImpl sceneImpl, SceneImpl sceneImpl2, TransitionImpl transitionImpl) {
        this.mTransitionManager.setTransition(((SceneWrapper) sceneImpl).mScene, ((SceneWrapper) sceneImpl2).mScene, transitionImpl == null ? null : ((TransitionKitKat) transitionImpl).mTransition);
    }

    public void setTransition(SceneImpl sceneImpl, TransitionImpl transitionImpl) {
        this.mTransitionManager.setTransition(((SceneWrapper) sceneImpl).mScene, transitionImpl == null ? null : ((TransitionKitKat) transitionImpl).mTransition);
    }

    public void transitionTo(SceneImpl sceneImpl) {
        this.mTransitionManager.transitionTo(((SceneWrapper) sceneImpl).mScene);
    }
}
