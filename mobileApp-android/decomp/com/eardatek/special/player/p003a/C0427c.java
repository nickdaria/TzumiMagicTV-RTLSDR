package com.eardatek.special.player.p003a;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import com.eardatek.special.atsc.R;
import com.eardatek.special.player.p005i.C0538h;
import com.eardatek.special.player.p007b.C0500a;
import com.eardatek.special.player.p010g.C0521a;
import com.eardatek.special.player.system.DTVApplication;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class C0427c extends Adapter<ViewHolder> implements OnClickListener {
    private static final String f71a = C0427c.class.getSimpleName();
    private LayoutInflater f72b;
    private Context f73c;
    private List<C0500a> f74d;
    private C0426b f75e;
    private Map<String, Button> f76f = new HashMap();
    private C0521a f77g;
    private Button f78h = null;

    class C0425a extends ViewHolder {
        Button f69a;
        final /* synthetic */ C0427c f70b;

        public C0425a(C0427c c0427c, View view) {
            this.f70b = c0427c;
            super(view);
            this.f69a = (Button) view.findViewById(R.id.showepg_handler_btn);
        }
    }

    public interface C0426b {
        void mo2109a(View view);
    }

    public C0427c(Context context) {
        this.f72b = LayoutInflater.from(context);
        this.f73c = context;
        this.f77g = new C0521a(DTVApplication.m750a());
        try {
            this.f74d = this.f77g.m589a();
            this.f77g.m594c();
            Log.e(f71a, "list:" + this.f74d.size());
        } catch (Exception e) {
            e.printStackTrace();
            this.f74d = null;
        }
    }

    public Button m79a(String str) {
        return (Button) this.f76f.get(str);
    }

    public void m80a(Button button) {
        if (this.f78h != null) {
            this.f78h.setTextColor(-1);
            this.f78h.setBackgroundResource(R.drawable.bg_channelname_fullscreen);
        }
        button.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        button.setBackgroundResource(R.drawable.photo_gallery_pressed);
        this.f78h = button;
    }

    public void m81a(C0426b c0426b) {
        this.f75e = c0426b;
    }

    public int getItemCount() {
        Log.e(f71a, "getItemCount" + this.f74d.size());
        return this.f74d.size();
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        C0500a c0500a = (C0500a) this.f74d.get(i);
        if (C0538h.m641a(c0500a.m492b()) != null) {
            C0425a c0425a = (C0425a) viewHolder;
            c0425a.f69a.setText(c0500a.m489a());
            c0425a.f69a.setTag(String.format(Locale.ENGLISH, "%d-%d", new Object[]{Integer.valueOf(r1.m633a()), Integer.valueOf(r1.m636b())}));
            c0425a.f69a.setOnClickListener(this);
            this.f76f.put(String.format(Locale.ENGLISH, "%d-%d", new Object[]{Integer.valueOf(r1.m633a()), Integer.valueOf(r1.m636b())}), c0425a.f69a);
            Log.e(f71a, c0500a.m489a());
        }
    }

    public void onClick(View view) {
        m80a((Button) view);
        if (this.f75e != null) {
            this.f75e.mo2109a(view);
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new C0425a(this, this.f72b.inflate(R.layout.showepg_handler_item, viewGroup, false));
    }

    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        Log.e("TAG", "onDetachedFromRecyclerView");
        try {
            this.f77g.m594c();
        } catch (SQLException e) {
        }
    }
}
