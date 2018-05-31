package android.support.transition;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.transition.Scene;
import android.view.ViewGroup;

@TargetApi(21)
@RequiresApi(21)
class SceneStaticsApi21 extends SceneStaticsImpl {
    SceneStaticsApi21() {
    }

    public SceneImpl getSceneForLayout(ViewGroup viewGroup, int i, Context context) {
        SceneImpl sceneApi21 = new SceneApi21();
        sceneApi21.mScene = Scene.getSceneForLayout(viewGroup, i, context);
        return sceneApi21;
    }
}
