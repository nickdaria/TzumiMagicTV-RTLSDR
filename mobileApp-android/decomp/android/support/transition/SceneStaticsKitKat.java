package android.support.transition;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.transition.Scene;
import android.view.ViewGroup;

@TargetApi(19)
@RequiresApi(19)
class SceneStaticsKitKat extends SceneStaticsImpl {
    SceneStaticsKitKat() {
    }

    public SceneImpl getSceneForLayout(ViewGroup viewGroup, int i, Context context) {
        SceneImpl sceneKitKat = new SceneKitKat();
        sceneKitKat.mScene = Scene.getSceneForLayout(viewGroup, i, context);
        return sceneKitKat;
    }
}
