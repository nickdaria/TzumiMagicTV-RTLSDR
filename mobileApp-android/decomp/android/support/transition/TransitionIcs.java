package android.support.transition;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.support.transition.TransitionPort.TransitionListener;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@TargetApi(14)
@RequiresApi(14)
class TransitionIcs extends TransitionImpl {
    private CompatListener mCompatListener;
    TransitionInterface mExternalTransition;
    TransitionPort mTransition;

    private class CompatListener implements TransitionListener {
        private final ArrayList<TransitionInterfaceListener> mListeners = new ArrayList();

        CompatListener() {
        }

        public void addListener(TransitionInterfaceListener transitionInterfaceListener) {
            this.mListeners.add(transitionInterfaceListener);
        }

        public boolean isEmpty() {
            return this.mListeners.isEmpty();
        }

        public void onTransitionCancel(TransitionPort transitionPort) {
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                ((TransitionInterfaceListener) it.next()).onTransitionCancel(TransitionIcs.this.mExternalTransition);
            }
        }

        public void onTransitionEnd(TransitionPort transitionPort) {
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                ((TransitionInterfaceListener) it.next()).onTransitionEnd(TransitionIcs.this.mExternalTransition);
            }
        }

        public void onTransitionPause(TransitionPort transitionPort) {
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                ((TransitionInterfaceListener) it.next()).onTransitionPause(TransitionIcs.this.mExternalTransition);
            }
        }

        public void onTransitionResume(TransitionPort transitionPort) {
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                ((TransitionInterfaceListener) it.next()).onTransitionResume(TransitionIcs.this.mExternalTransition);
            }
        }

        public void onTransitionStart(TransitionPort transitionPort) {
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                ((TransitionInterfaceListener) it.next()).onTransitionStart(TransitionIcs.this.mExternalTransition);
            }
        }

        public void removeListener(TransitionInterfaceListener transitionInterfaceListener) {
            this.mListeners.remove(transitionInterfaceListener);
        }
    }

    private static class TransitionWrapper extends TransitionPort {
        private TransitionInterface mTransition;

        public TransitionWrapper(TransitionInterface transitionInterface) {
            this.mTransition = transitionInterface;
        }

        public void captureEndValues(TransitionValues transitionValues) {
            this.mTransition.captureEndValues(transitionValues);
        }

        public void captureStartValues(TransitionValues transitionValues) {
            this.mTransition.captureStartValues(transitionValues);
        }

        public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
            return this.mTransition.createAnimator(viewGroup, transitionValues, transitionValues2);
        }
    }

    TransitionIcs() {
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
        this.mTransition.captureEndValues(transitionValues);
    }

    public void captureStartValues(TransitionValues transitionValues) {
        this.mTransition.captureStartValues(transitionValues);
    }

    public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return this.mTransition.createAnimator(viewGroup, transitionValues, transitionValues2);
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
        return this.mTransition.getTransitionValues(view, z);
    }

    public void init(TransitionInterface transitionInterface, Object obj) {
        this.mExternalTransition = transitionInterface;
        if (obj == null) {
            this.mTransition = new TransitionWrapper(transitionInterface);
        } else {
            this.mTransition = (TransitionPort) obj;
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
        this.mTransition.removeTarget(i);
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
