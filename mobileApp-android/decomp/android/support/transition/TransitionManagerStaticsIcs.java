package android.support.transition;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.ViewGroup;

@TargetApi(14)
@RequiresApi(14)
class TransitionManagerStaticsIcs extends TransitionManagerStaticsImpl {
    TransitionManagerStaticsIcs() {
    }

    public void beginDelayedTransition(ViewGroup viewGroup) {
        TransitionManagerPort.beginDelayedTransition(viewGroup);
    }

    public void beginDelayedTransition(ViewGroup viewGroup, TransitionImpl transitionImpl) {
        TransitionManagerPort.beginDelayedTransition(viewGroup, transitionImpl == null ? null : ((TransitionIcs) transitionImpl).mTransition);
    }

    public void go(SceneImpl sceneImpl) {
        TransitionManagerPort.go(((SceneIcs) sceneImpl).mScene);
    }

    public void go(SceneImpl sceneImpl, TransitionImpl transitionImpl) {
        TransitionManagerPort.go(((SceneIcs) sceneImpl).mScene, transitionImpl == null ? null : ((TransitionIcs) transitionImpl).mTransition);
    }
}
