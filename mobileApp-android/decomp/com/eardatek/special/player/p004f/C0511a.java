package com.eardatek.special.player.p004f;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public class C0511a extends Fragment {
    protected Bundle f373a;

    public C0511a() {
        if (getArguments() == null) {
            setArguments(new Bundle());
        }
    }

    private void mo2136a() {
        if (getView() != null) {
            this.f373a = m553e();
        }
        if (this.f373a != null) {
            getArguments().putBundle("internalSavedViewState8954201239547", this.f373a);
        }
    }

    private boolean mo2137c() {
        this.f373a = getArguments().getBundle("internalSavedViewState8954201239547");
        if (this.f373a == null) {
            return false;
        }
        mo2138d();
        return true;
    }

    private void mo2138d() {
        if (this.f373a != null) {
            m556b(this.f373a);
        }
    }

    private Bundle m553e() {
        Bundle bundle = new Bundle();
        m554a(bundle);
        return bundle;
    }

    protected void m554a(Bundle bundle) {
    }

    protected void m555b() {
    }

    protected void m556b(Bundle bundle) {
    }

    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        mo2138d();
        if (!mo2137c()) {
            m555b();
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        mo2136a();
    }

    public void onDetach() {
        super.onDetach();
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        mo2136a();
    }
}
