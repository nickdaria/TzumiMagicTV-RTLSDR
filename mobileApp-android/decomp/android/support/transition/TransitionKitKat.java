package android.support.transition;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.transition.Transition;
import android.transition.Transition.TransitionListener;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@TargetApi(19)
@RequiresApi(19)
class TransitionKitKat extends TransitionImpl {
    private CompatListener mCompatListener;
    TransitionInterface mExternalTransition;
    Transition mTransition;

    private class CompatListener implements TransitionListener {
        private final ArrayList<TransitionInterfaceListener> mListeners = new ArrayList();

        CompatListener() {
        }

        void addListener(TransitionInterfaceListener transitionInterfaceListener) {
            this.mListeners.add(transitionInterfaceListener);
        }

        boolean isEmpty() {
            return this.mListeners.isEmpty();
        }

        public void onTransitionCancel(Transition transition) {
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                ((TransitionInterfaceListener) it.next()).onTransitionCancel(TransitionKitKat.this.mExternalTransition);
            }
        }

        public void onTransitionEnd(Transition transition) {
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                ((TransitionInterfaceListener) it.next()).onTransitionEnd(TransitionKitKat.this.mExternalTransition);
            }
        }

        public void onTransitionPause(Transition transition) {
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                ((TransitionInterfaceListener) it.next()).onTransitionPause(TransitionKitKat.this.mExternalTransition);
            }
        }

        public void onTransitionResume(Transition transition) {
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                ((TransitionInterfaceListener) it.next()).onTransitionResume(TransitionKitKat.this.mExternalTransition);
            }
        }

        public void onTransitionStart(Transition transition) {
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                ((TransitionInterfaceListener) it.next()).onTransitionStart(TransitionKitKat.this.mExternalTransition);
            }
        }

        void removeListener(TransitionInterfaceListener transitionInterfaceListener) {
            this.mListeners.remove(transitionInterfaceListener);
        }
    }

    private static class TransitionWrapper extends Transition {
        private TransitionInterface mTransition;

        public TransitionWrapper(TransitionInterface transitionInterface) {
            this.mTransition = transitionInterface;
        }

        public void captureEndValues(TransitionValues transitionValues) {
            TransitionKitKat.wrapCaptureEndValues(this.mTransition, transitionValues);
        }

        public void captureStartValues(TransitionValues transitionValues) {
            TransitionKitKat.wrapCaptureStartValues(this.mTransition, transitionValues);
        }

        public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
            return this.mTransition.createAnimator(viewGroup, TransitionKitKat.convertToSupport(transitionValues), TransitionKitKat.convertToSupport(transitionValues2));
        }
    }

    TransitionKitKat() {
    }

    static TransitionValues convertToPlatform(TransitionValues transitionValues) {
        if (transitionValues == null) {
            return null;
        }
        TransitionValues transitionValues2 = new TransitionValues();
        copyValues(transitionValues, transitionValues2);
        return transitionValues2;
    }

    static TransitionValues convertToSupport(TransitionValues transitionValues) {
        if (transitionValues == null) {
            return null;
        }
        TransitionValues transitionValues2 = new TransitionValues();
        copyValues(transitionValues, transitionValues2);
        return transitionValues2;
    }

    static void copyValues(TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues != null) {
            transitionValues2.view = transitionValues.view;
            if (transitionValues.values.size() > 0) {
                transitionValues2.values.putAll(transitionValues.values);
            }
        }
    }

    static void copyValues(TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues != null) {
            transitionValues2.view = transitionValues.view;
            if (transitionValues.values.size() > 0) {
                transitionValues2.values.putAll(transitionValues.values);
            }
        }
    }

    static void wrapCaptureEndValues(TransitionInterface transitionInterface, TransitionValues transitionValues) {
        TransitionValues transitionValues2 = new TransitionValues();
        copyValues(transitionValues, transitionValues2);
        transitionInterface.captureEndValues(transitionValues2);
        copyValues(transitionValues2, transitionValues);
    }

    static void wrapCaptureStartValues(TransitionInterface transitionInterface, TransitionValues transitionValues) {
        TransitionValues transitionValues2 = new TransitionValues();
        copyValues(transitionValues, transitionValues2);
        transitionInterface.captureStartValues(transitionValues2);
        copyValues(transitionValues2, transitionValues);
    }

    public TransitionImpl addListener(TransitionInterfaceListener transitionInterfaceListener) {
        if (this.mCompatListener == null) {
            this.mCompatListener = new CompatListener();
            this.mTransition.addListener(this.mCompatListener);
        }
        this.mCompatListener.addListener(transitionInterfaceListener);
        return this;
    }

    public TransitionImpl addTarget(int i) {
        this.mTransition.addTarget(i);
        return this;
    }

    public TransitionImpl addTarget(View view) {
        this.mTransition.addTarget(view);
        return this;
    }

    public void captureEndValues(TransitionValues transitionValues) {
        TransitionValues transitionValues2 = new TransitionValues();
        copyValues(transitionValues, transitionValues2);
        this.mTransition.captureEndValues(transitionValues2);
        copyValues(transitionValues2, transitionValues);
    }

    public void captureStartValues(TransitionValues transitionValues) {
        TransitionValues transitionValues2 = new TransitionValues();
        copyValues(transitionValues, transitionValues2);
        this.mTransition.captureStartValues(transitionValues2);
        copyValues(transitionValues2, transitionValues);
    }

    public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        TransitionValues transitionValues3;
        TransitionValues transitionValues4 = null;
        if (transitionValues != null) {
            transitionValues3 = new TransitionValues();
            copyValues(transitionValues, transitionValues3);
        } else {
            transitionValues3 = null;
        }
        if (transitionValues2 != null) {
            transitionValues4 = new TransitionValues();
            copyValues(transitionValues2, transitionValues4);
        }
        return this.mTransition.createAnimator(viewGroup, transitionValues3, transitionValues4);
    }

    public TransitionImpl excludeChildren(int i, boolean z) {
        this.mTransition.excludeChildren(i, z);
        return this;
    }

    public TransitionImpl excludeChildren(View view, boolean z) {
        this.mTransition.excludeChildren(view, z);
        return this;
    }

    public TransitionImpl excludeChildren(Class cls, boolean z) {
        this.mTransition.excludeChildren(cls, z);
        return this;
    }

    public TransitionImpl excludeTarget(int i, boolean z) {
        this.mTransition.excludeTarget(i, z);
        return this;
    }

    public TransitionImpl excludeTarget(View view, boolean z) {
        this.mTransition.excludeTarget(view, z);
        return this;
    }

    public TransitionImpl excludeTarget(Class cls, boolean z) {
        this.mTransition.excludeTarget(cls, z);
        return this;
    }

    public long getDuration() {
        return this.mTransition.getDuration();
    }

    public TimeInterpolator getInterpolator() {
        return this.mTransition.getInterpolator();
    }

    public String getName() {
        return this.mTransition.getName();
    }

    public long getStartDelay() {
        return this.mTransition.getStartDelay();
    }

    public List<Integer> getTargetIds() {
        return this.mTransition.getTargetIds();
    }

    public List<View> getTargets() {
        return this.mTransition.getTargets();
    }

    public String[] getTransitionProperties() {
        return this.mTransition.getTransitionProperties();
    }

    public TransitionValues getTransitionValues(View view, boolean z) {
        TransitionValues transitionValues = new TransitionValues();
        copyValues(this.mTransition.getTransitionValues(view, z), transitionValues);
        return transitionValues;
    }

    public void init(TransitionInterface transitionInterface, Object obj) {
        this.mExternalTransition = transitionInterface;
        if (obj == null) {
            this.mTransition = new TransitionWrapper(transitionInterface);
        } else {
            this.mTransition = (Transition) obj;
        }
    }

    public TransitionImpl removeListener(TransitionInterfaceListener transitionInterfaceListener) {
        if (this.mCompatListener != null) {
            this.mCompatListener.removeListener(transitionInterfaceListener);
            if (this.mCompatListener.isEmpty()) {
                this.mTransition.removeListener(this.mCompatListener);
                this.mCompatListener = null;
            }
        }
        return this;
    }

    public TransitionImpl removeTarget(int i) {
        if (i > 0) {
            getTargetIds().remove(Integer.valueOf(i));
        }
        return this;
    }

    public TransitionImpl removeTarget(View view) {
        this.mTransition.removeTarget(view);
        return this;
    }

    public TransitionImpl setDuration(long j) {
        this.mTransition.setDuration(j);
        return this;
    }

    public TransitionImpl setInterpolator(TimeInterpolator timeInterpolator) {
        this.mTransition.setInterpolator(timeInterpolator);
        return this;
    }

    public TransitionImpl setStartDelay(long j) {
        this.mTransition.setStartDelay(j);
        return this;
    }

    public String toString() {
        return this.mTransition.toString();
    }
}
