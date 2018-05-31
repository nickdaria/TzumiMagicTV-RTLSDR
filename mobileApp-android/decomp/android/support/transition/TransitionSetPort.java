package android.support.transition;

import android.animation.TimeInterpolator;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.transition.TransitionPort.TransitionListener;
import android.support.transition.TransitionPort.TransitionListenerAdapter;
import android.util.AndroidRuntimeException;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Iterator;

@TargetApi(14)
@RequiresApi(14)
class TransitionSetPort extends TransitionPort {
    public static final int ORDERING_SEQUENTIAL = 1;
    public static final int ORDERING_TOGETHER = 0;
    int mCurrentListeners;
    private boolean mPlayTogether = true;
    boolean mStarted = false;
    ArrayList<TransitionPort> mTransitions = new ArrayList();

    static class TransitionSetListener extends TransitionListenerAdapter {
        TransitionSetPort mTransitionSet;

        TransitionSetListener(TransitionSetPort transitionSetPort) {
            this.mTransitionSet = transitionSetPort;
        }

        public void onTransitionEnd(TransitionPort transitionPort) {
            TransitionSetPort transitionSetPort = this.mTransitionSet;
            transitionSetPort.mCurrentListeners--;
            if (this.mTransitionSet.mCurrentListeners == 0) {
                this.mTransitionSet.mStarted = false;
                this.mTransitionSet.end();
            }
            transitionPort.removeListener(this);
        }

        public void onTransitionStart(TransitionPort transitionPort) {
            if (!this.mTransitionSet.mStarted) {
                this.mTransitionSet.start();
                this.mTransitionSet.mStarted = true;
            }
        }
    }

    private void setupStartEndListeners() {
        TransitionListener transitionSetListener = new TransitionSetListener(this);
        Iterator it = this.mTransitions.iterator();
        while (it.hasNext()) {
            ((TransitionPort) it.next()).addListener(transitionSetListener);
        }
        this.mCurrentListeners = this.mTransitions.size();
    }

    public TransitionSetPort addListener(TransitionListener transitionListener) {
        return (TransitionSetPort) super.addListener(transitionListener);
    }

    public TransitionSetPort addTarget(int i) {
        return (TransitionSetPort) super.addTarget(i);
    }

    public TransitionSetPort addTarget(View view) {
        return (TransitionSetPort) super.addTarget(view);
    }

    public TransitionSetPort addTransition(TransitionPort transitionPort) {
        if (transitionPort != null) {
            this.mTransitions.add(transitionPort);
            transitionPort.mParent = this;
            if (this.mDuration >= 0) {
                transitionPort.setDuration(this.mDuration);
            }
        }
        return this;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    protected void cancel() {
        super.cancel();
        int size = this.mTransitions.size();
        for (int i = 0; i < size; i++) {
            ((TransitionPort) this.mTransitions.get(i)).cancel();
        }
    }

    public void captureEndValues(TransitionValues transitionValues) {
        int id = transitionValues.view.getId();
        if (isValidTarget(transitionValues.view, (long) id)) {
            Iterator it = this.mTransitions.iterator();
            while (it.hasNext()) {
                TransitionPort transitionPort = (TransitionPort) it.next();
                if (transitionPort.isValidTarget(transitionValues.view, (long) id)) {
                    transitionPort.captureEndValues(transitionValues);
                }
            }
        }
    }

    public void captureStartValues(TransitionValues transitionValues) {
        int id = transitionValues.view.getId();
        if (isValidTarget(transitionValues.view, (long) id)) {
            Iterator it = this.mTransitions.iterator();
            while (it.hasNext()) {
                TransitionPort transitionPort = (TransitionPort) it.next();
                if (transitionPort.isValidTarget(transitionValues.view, (long) id)) {
                    transitionPort.captureStartValues(transitionValues);
                }
            }
        }
    }

    public TransitionSetPort clone() {
        TransitionSetPort transitionSetPort = (TransitionSetPort) super.clone();
        transitionSetPort.mTransitions = new ArrayList();
        int size = this.mTransitions.size();
        for (int i = 0; i < size; i++) {
            transitionSetPort.addTransition(((TransitionPort) this.mTransitions.get(i)).clone());
        }
        return transitionSetPort;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    protected void createAnimators(ViewGroup viewGroup, TransitionValuesMaps transitionValuesMaps, TransitionValuesMaps transitionValuesMaps2) {
        Iterator it = this.mTransitions.iterator();
        while (it.hasNext()) {
            ((TransitionPort) it.next()).createAnimators(viewGroup, transitionValuesMaps, transitionValuesMaps2);
        }
    }

    public int getOrdering() {
        return this.mPlayTogether ? 0 : 1;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void pause(View view) {
        super.pause(view);
        int size = this.mTransitions.size();
        for (int i = 0; i < size; i++) {
            ((TransitionPort) this.mTransitions.get(i)).pause(view);
        }
    }

    public TransitionSetPort removeListener(TransitionListener transitionListener) {
        return (TransitionSetPort) super.removeListener(transitionListener);
    }

    public TransitionSetPort removeTarget(int i) {
        return (TransitionSetPort) super.removeTarget(i);
    }

    public TransitionSetPort removeTarget(View view) {
        return (TransitionSetPort) super.removeTarget(view);
    }

    public TransitionSetPort removeTransition(TransitionPort transitionPort) {
        this.mTransitions.remove(transitionPort);
        transitionPort.mParent = null;
        return this;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void resume(View view) {
        super.resume(view);
        int size = this.mTransitions.size();
        for (int i = 0; i < size; i++) {
            ((TransitionPort) this.mTransitions.get(i)).resume(view);
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    protected void runAnimators() {
        if (this.mTransitions.isEmpty()) {
            start();
            end();
            return;
        }
        setupStartEndListeners();
        if (this.mPlayTogether) {
            Iterator it = this.mTransitions.iterator();
            while (it.hasNext()) {
                ((TransitionPort) it.next()).runAnimators();
            }
            return;
        }
        for (int i = 1; i < this.mTransitions.size(); i++) {
            final TransitionPort transitionPort = (TransitionPort) this.mTransitions.get(i);
            ((TransitionPort) this.mTransitions.get(i - 1)).addListener(new TransitionListenerAdapter() {
                public void onTransitionEnd(TransitionPort transitionPort) {
                    transitionPort.runAnimators();
                    transitionPort.removeListener(this);
                }
            });
        }
        TransitionPort transitionPort2 = (TransitionPort) this.mTransitions.get(0);
        if (transitionPort2 != null) {
            transitionPort2.runAnimators();
        }
    }

    void setCanRemoveViews(boolean z) {
        super.setCanRemoveViews(z);
        int size = this.mTransitions.size();
        for (int i = 0; i < size; i++) {
            ((TransitionPort) this.mTransitions.get(i)).setCanRemoveViews(z);
        }
    }

    public TransitionSetPort setDuration(long j) {
        super.setDuration(j);
        if (this.mDuration >= 0) {
            int size = this.mTransitions.size();
            for (int i = 0; i < size; i++) {
                ((TransitionPort) this.mTransitions.get(i)).setDuration(j);
            }
        }
        return this;
    }

    public TransitionSetPort setInterpolator(TimeInterpolator timeInterpolator) {
        return (TransitionSetPort) super.setInterpolator(timeInterpolator);
    }

    public TransitionSetPort setOrdering(int i) {
        switch (i) {
            case 0:
                this.mPlayTogether = true;
                break;
            case 1:
                this.mPlayTogether = false;
                break;
            default:
                throw new AndroidRuntimeException("Invalid parameter for TransitionSet ordering: " + i);
        }
        return this;
    }

    TransitionSetPort setSceneRoot(ViewGroup viewGroup) {
        super.setSceneRoot(viewGroup);
        int size = this.mTransitions.size();
        for (int i = 0; i < size; i++) {
            ((TransitionPort) this.mTransitions.get(i)).setSceneRoot(viewGroup);
        }
        return this;
    }

    public TransitionSetPort setStartDelay(long j) {
        return (TransitionSetPort) super.setStartDelay(j);
    }

    String toString(String str) {
        String transitionPort = super.toString(str);
        int i = 0;
        while (i < this.mTransitions.size()) {
            String str2 = transitionPort + "\n" + ((TransitionPort) this.mTransitions.get(i)).toString(str + "  ");
            i++;
            transitionPort = str2;
        }
        return transitionPort;
    }
}
