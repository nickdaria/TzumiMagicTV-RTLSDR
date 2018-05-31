package com.anthony.ultimateswipetool;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.app.Activity;
import android.app.ActivityOptions;
import android.graphics.drawable.ColorDrawable;
import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import com.anthony.ultimateswipetool.activity.SwipeBackLayout;
import com.anthony.ultimateswipetool.activity.SwipeBackLayout.SwipeListener;
import java.lang.reflect.Method;

public class SwipeHelper {
    private Activity mActivity;
    private SwipeBackLayout mSwipeBackLayout;

    class C04051 implements SwipeListener {
        C04051() {
        }

        public void onEdgeTouch(int i) {
            SwipeHelper.convertActivityToTranslucent(SwipeHelper.this.mActivity);
        }

        public void onScrollOverThreshold() {
        }

        public void onScrollStateChange(int i, float f) {
        }
    }

    public static abstract class AnimationEndListener implements AnimatorListener {
        public void onAnimationCancel(Animator animator) {
        }

        public void onAnimationRepeat(Animator animator) {
        }

        public void onAnimationStart(Animator animator) {
        }
    }

    public SwipeHelper(Activity activity) {
        this.mActivity = activity;
    }

    public static void convertActivityFromTranslucent(Activity activity) {
        try {
            Method declaredMethod = Activity.class.getDeclaredMethod("convertFromTranslucent", new Class[0]);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(activity, new Object[0]);
        } catch (Throwable th) {
        }
    }

    public static void convertActivityToTranslucent(Activity activity) {
        if (VERSION.SDK_INT >= 21) {
            convertActivityToTranslucentAfterL(activity);
        } else {
            convertActivityToTranslucentBeforeL(activity);
        }
    }

    private static void convertActivityToTranslucentAfterL(Activity activity) {
        Class cls = null;
        try {
            Method declaredMethod = Activity.class.getDeclaredMethod("getActivityOptions", new Class[0]);
            declaredMethod.setAccessible(true);
            Object invoke = declaredMethod.invoke(activity, new Object[0]);
            Class[] declaredClasses = Activity.class.getDeclaredClasses();
            int length = declaredClasses.length;
            int i = 0;
            while (i < length) {
                Class cls2 = declaredClasses[i];
                if (!cls2.getSimpleName().contains("TranslucentConversionListener")) {
                    cls2 = cls;
                }
                i++;
                cls = cls2;
            }
            Method declaredMethod2 = Activity.class.getDeclaredMethod("convertToTranslucent", new Class[]{cls, ActivityOptions.class});
            declaredMethod2.setAccessible(true);
            declaredMethod2.invoke(activity, new Object[]{null, invoke});
        } catch (Throwable th) {
        }
    }

    public static void convertActivityToTranslucentBeforeL(Activity activity) {
        Class cls = null;
        try {
            Class[] declaredClasses = Activity.class.getDeclaredClasses();
            int length = declaredClasses.length;
            int i = 0;
            while (i < length) {
                Class cls2 = declaredClasses[i];
                if (!cls2.getSimpleName().contains("TranslucentConversionListener")) {
                    cls2 = cls;
                }
                i++;
                cls = cls2;
            }
            Method declaredMethod = Activity.class.getDeclaredMethod("convertToTranslucent", new Class[]{cls});
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(activity, new Object[]{null});
        } catch (Throwable th) {
        }
    }

    public static void replaceContentView(Window window, ViewGroup viewGroup) {
        ViewGroup viewGroup2 = (ViewGroup) window.getDecorView();
        View childAt = viewGroup2.getChildAt(0);
        viewGroup2.removeView(childAt);
        viewGroup.addView(childAt);
        viewGroup2.addView(viewGroup);
    }

    public View findViewById(int i) {
        return this.mSwipeBackLayout != null ? this.mSwipeBackLayout.findViewById(i) : null;
    }

    public SwipeBackLayout getSwipeBackLayout() {
        return this.mSwipeBackLayout;
    }

    public void onActivityCreate() {
        this.mActivity.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.mActivity.getWindow().getDecorView().setBackgroundDrawable(null);
        this.mSwipeBackLayout = new SwipeBackLayout(this.mActivity);
        this.mSwipeBackLayout.setLayoutParams(new LayoutParams(-1, -1));
        this.mSwipeBackLayout.addSwipeListener(new C04051());
    }

    public void onPostCreate() {
        this.mSwipeBackLayout.attachToActivity(this.mActivity);
    }
}
