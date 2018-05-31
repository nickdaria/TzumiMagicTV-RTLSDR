package com.eardatek.special.player.actitivy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.eardatek.special.player.p005i.C0535f;

public class BaseActivity extends AppCompatActivity {
    protected void m132a() {
    }

    protected void m133b() {
        C0535f.m629a();
    }

    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        m133b();
    }

    protected void onDestroy() {
        super.onDestroy();
        m132a();
    }
}
