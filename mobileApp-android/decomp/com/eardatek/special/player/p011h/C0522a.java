package com.eardatek.special.player.p011h;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import com.eardatek.special.player.p005i.C0535f;

public class C0522a extends IntentService {
    private void m595a() {
        C0535f.m629a();
    }

    public static void m596a(Context context) {
        Intent intent = new Intent(context, C0522a.class);
        intent.setAction("INITIAL");
        context.startService(intent);
    }

    protected void onHandleIntent(Intent intent) {
        if (intent != null && intent.getAction().equals("INITIAL")) {
            m595a();
        }
    }
}
