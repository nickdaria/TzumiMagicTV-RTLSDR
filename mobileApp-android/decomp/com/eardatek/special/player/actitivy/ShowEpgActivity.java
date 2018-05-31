package com.eardatek.special.player.actitivy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.View;
import android.widget.TextView;
import com.eardatek.special.atsc.R;
import com.eardatek.special.player.p003a.C0424b;
import com.eardatek.special.player.p003a.C0427c;
import com.eardatek.special.player.p003a.C0427c.C0426b;
import com.sherwin.libepgparser.C0595a;
import com.sherwin.libepgparser.p014b.C0600a;
import java.util.List;

public class ShowEpgActivity extends SwipeBackBaseActicity {
    private RecyclerView f336a;
    private RecyclerView f337b;
    private C0427c f338c;
    private C0424b f339d;
    private TextView f340e;
    private String f341f = null;
    private Handler f342g = new C04961(this);

    class C04961 extends Handler {
        final /* synthetic */ ShowEpgActivity f335a;

        class C04951 implements C0426b {
            final /* synthetic */ C04961 f334a;

            C04951(C04961 c04961) {
                this.f334a = c04961;
            }

            public void mo2109a(View view) {
                String obj = view.getTag().toString();
                if (obj != null) {
                    List b = C0595a.m825b(obj);
                    if (b != null) {
                        this.f334a.f335a.f340e.setVisibility(8);
                        this.f334a.f335a.m473a(b);
                        return;
                    }
                    this.f334a.f335a.f340e.setVisibility(0);
                }
            }
        }

        C04961(ShowEpgActivity showEpgActivity) {
            this.f335a = showEpgActivity;
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    if (this.f335a.f341f != null) {
                        List b = C0595a.m825b(this.f335a.f341f);
                        if (b != null) {
                            try {
                                this.f335a.f338c.m80a(this.f335a.f338c.m79a(this.f335a.f341f));
                            } catch (NullPointerException e) {
                            }
                            this.f335a.f340e.setVisibility(8);
                            this.f335a.m473a(b);
                            return;
                        }
                        this.f335a.f340e.setVisibility(0);
                        return;
                    }
                    return;
                case 1:
                    this.f335a.f338c = new C0427c(this.f335a);
                    this.f335a.f338c.m81a(new C04951(this));
                    this.f335a.f336a.setAdapter(this.f335a.f338c);
                    this.f335a.f336a.setLayoutManager(new LinearLayoutManager(this.f335a));
                    return;
                default:
                    return;
            }
        }
    }

    public static void m471a(Context context, String str) {
        Intent intent = new Intent(context, ShowEpgActivity.class);
        intent.putExtra("DefaultProgramKey", str);
        context.startActivity(intent);
    }

    private void m473a(List<C0600a> list) {
        this.f339d = new C0424b(this, list, this.f341f);
        LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        this.f337b.setAdapter(this.f339d);
        this.f337b.setLayoutManager(linearLayoutManager);
    }

    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_showepg);
        if (getIntent() != null) {
            this.f341f = getIntent().getStringExtra("DefaultProgramKey");
        }
        this.f340e = (TextView) findViewById(R.id.showepg_content_noepg);
        this.f340e.setVisibility(8);
        this.f336a = (RecyclerView) findViewById(R.id.showepg_handler);
        this.f337b = (RecyclerView) findViewById(R.id.showepg_content);
        this.f342g.sendEmptyMessageDelayed(1, 10);
    }

    protected void onStart() {
        super.onStart();
        this.f342g.sendEmptyMessageDelayed(0, 100);
    }
}
