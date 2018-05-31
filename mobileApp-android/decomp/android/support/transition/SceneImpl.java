package android.support.transition;

import android.view.View;
import android.view.ViewGroup;

abstract class SceneImpl {
    SceneImpl() {
    }

    public abstract void enter();

    public abstract void exit();

    public abstract ViewGroup getSceneRoot();

    public abstract void init(ViewGroup viewGroup);

    public abstract void init(ViewGroup viewGroup, View view);

    public abstract void setEnterAction(Runnable runnable);

    public abstract void setExitAction(Runnable runnable);
}
