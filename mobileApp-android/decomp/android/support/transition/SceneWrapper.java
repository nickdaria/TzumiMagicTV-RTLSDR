package android.support.transition;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.transition.Scene;
import android.view.ViewGroup;

@TargetApi(19)
@RequiresApi(19)
abstract class SceneWrapper extends SceneImpl {
    Scene mScene;

    SceneWrapper() {
    }

    public void exit() {
        this.mScene.exit();
    }

    public ViewGroup getSceneRoot() {
        return this.mScene.getSceneRoot();
    }

    public void setEnterAction(Runnable runnable) {
        this.mScene.setEnterAction(runnable);
    }

    public void setExitAction(Runnable runnable) {
        this.mScene.setExitAction(runnable);
    }
}
