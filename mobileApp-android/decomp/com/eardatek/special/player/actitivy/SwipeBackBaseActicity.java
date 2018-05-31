package com.eardatek.special.player.actitivy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.anthony.ultimateswipetool.activity.AbsSwipeBackActivity;
import com.eardatek.special.player.p005i.C0535f;

public class SwipeBackBaseActicity extends AbsSwipeBackActivity {
    protected void m443c() {
    }

    protected void m444d() {
        C0535f.m629a();
    }

    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        m444d();
    }

    protected void onDestroy() {
        super.onDestroy();
        m443c();
    }
}
