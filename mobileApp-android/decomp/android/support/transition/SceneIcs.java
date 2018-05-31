package android.support.transition;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;

@TargetApi(14)
@RequiresApi(14)
class SceneIcs extends SceneImpl {
    ScenePort mScene;

    SceneIcs() {
    }

    public void enter() {
        this.mScene.enter();
    }

    public void exit() {
        this.mScene.exit();
    }

    public ViewGroup getSceneRoot() {
        return this.mScene.getSceneRoot();
    }

    public void init(ViewGroup viewGroup) {
        this.mScene = new ScenePort(viewGroup);
    }

    public void init(ViewGroup viewGroup, View view) {
        this.mScene = new ScenePort(viewGroup, view);
    }

    public void setEnterAction(Runnable runnable) {
        this.mScene.setEnterAction(runnable);
    }

    public void setExitAction(Runnable runnable) {
        this.mScene.setExitAction(runnable);
    }
}
