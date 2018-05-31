package android.support.transition;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.view.ViewGroup;

@TargetApi(14)
@RequiresApi(14)
class SceneStaticsIcs extends SceneStaticsImpl {
    SceneStaticsIcs() {
    }

    public SceneImpl getSceneForLayout(ViewGroup viewGroup, int i, Context context) {
        SceneImpl sceneIcs = new SceneIcs();
        sceneIcs.mScene = ScenePort.getSceneForLayout(viewGroup, i, context);
        return sceneIcs;
    }
}
