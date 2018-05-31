package android.support.transition;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.transition.Scene;
import android.view.View;
import android.view.ViewGroup;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@TargetApi(19)
@RequiresApi(19)
class SceneKitKat extends SceneWrapper {
    private static Field sEnterAction;
    private static Method sSetCurrentScene;
    private View mLayout;

    SceneKitKat() {
    }

    private void invokeEnterAction() {
        if (sEnterAction == null) {
            try {
                sEnterAction = Scene.class.getDeclaredField("mEnterAction");
                sEnterAction.setAccessible(true);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
        try {
            Runnable runnable = (Runnable) sEnterAction.get(this.mScene);
            if (runnable != null) {
                runnable.run();
            }
        } catch (Throwable e2) {
            throw new RuntimeException(e2);
        }
    }

    private void updateCurrentScene(View view) {
        if (sSetCurrentScene == null) {
            try {
                sSetCurrentScene = Scene.class.getDeclaredMethod("setCurrentScene", new Class[]{View.class, Scene.class});
                sSetCurrentScene.setAccessible(true);
            } catch (Throwable e) {
                Throwable e2;
                throw new RuntimeException(e2);
            }
        }
        try {
            sSetCurrentScene.invoke(null, new Object[]{view, this.mScene});
        } catch (IllegalAccessException e3) {
            e2 = e3;
            throw new RuntimeException(e2);
        } catch (InvocationTargetException e4) {
            e2 = e4;
            throw new RuntimeException(e2);
        }
    }

    public void enter() {
        if (this.mLayout != null) {
            View sceneRoot = getSceneRoot();
            sceneRoot.removeAllViews();
            sceneRoot.addView(this.mLayout);
            invokeEnterAction();
            updateCurrentScene(sceneRoot);
            return;
        }
        this.mScene.enter();
    }

    public void init(ViewGroup viewGroup) {
        this.mScene = new Scene(viewGroup);
    }

    public void init(ViewGroup viewGroup, View view) {
        if (view instanceof ViewGroup) {
            this.mScene = new Scene(viewGroup, (ViewGroup) view);
            return;
        }
        this.mScene = new Scene(viewGroup);
        this.mLayout = view;
    }
}
