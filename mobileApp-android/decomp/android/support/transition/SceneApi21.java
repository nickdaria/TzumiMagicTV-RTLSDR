package android.support.transition;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.transition.Scene;
import android.view.View;
import android.view.ViewGroup;

@TargetApi(21)
@RequiresApi(21)
class SceneApi21 extends SceneWrapper {
    SceneApi21() {
    }

    public void enter() {
        this.mScene.enter();
    }

    public void init(ViewGroup viewGroup) {
        this.mScene = new Scene(viewGroup);
    }

    public void init(ViewGroup viewGroup, View view) {
        this.mScene = new Scene(viewGroup, view);
    }
}
