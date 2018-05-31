package com.eardatek.special.player.p003a;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.eardatek.special.atsc.R;
import com.eardatek.special.player.p005i.C0536g;
import com.eardatek.special.player.p005i.C0538h;
import com.eardatek.special.player.p007b.C0500a;
import com.eardatek.special.player.p009e.C0510b.C0509a;
import com.eardatek.special.player.p010g.C0521a;
import com.eardatek.special.player.system.DTVApplication;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemConstants;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class C0422a extends Adapter<C0421b> {
    private List<C0509a> f54a = new ArrayList();
    private LayoutInflater f55b;
    private StringBuffer f56c = new StringBuffer("");
    private C0420a f57d;
    private int f58e = -1;

    public interface C0420a {
        void mo2098a(String str, boolean z, int i);
    }

    public class C0421b extends ViewHolder implements OnClickListener {
        final /* synthetic */ C0422a f50a;
        private TextView f51b;
        private CardView f52c;
        private ImageView f53d;

        public C0421b(C0422a c0422a, View view) {
            this.f50a = c0422a;
            super(view);
            this.f52c = (CardView) view.findViewById(R.id.card_chanel_name);
            this.f51b = (TextView) view.findViewById(R.id.chanel_name);
            this.f51b.setOnClickListener(this);
            this.f52c.setAlpha(0.6f);
            this.f53d = (ImageView) view.findViewById(R.id.play_arrow);
        }

        public void onClick(View view) {
            String obj = view.getTag().toString();
            this.f50a.m71a(obj);
            this.f50a.f57d.mo2098a(obj, true, getLayoutPosition());
            this.f50a.f58e = getLayoutPosition();
        }
    }

    public C0422a(Context context, C0420a c0420a) {
        m72b();
        this.f55b = LayoutInflater.from(context);
        this.f57d = c0420a;
    }

    public C0421b m68a(ViewGroup viewGroup, int i) {
        return new C0421b(this, this.f55b.inflate(R.layout.chanelname, viewGroup, false));
    }

    public void m69a() {
        if (this.f54a != null && this.f54a.size() > 0) {
            this.f54a.clear();
        }
    }

    public void m70a(C0421b c0421b, int i) {
        C0509a c0509a = (C0509a) this.f54a.get(i);
        Object f = c0509a.mo2125f();
        if (!TextUtils.isEmpty(f)) {
            c0421b.f51b.setText((C0538h.m641a(c0509a.mo2122c()).m633a() / 1000) + "MHz   " + f);
            c0421b.f51b.setTag(c0509a.mo2122c());
            if (c0509a.mo2122c().equals(this.f56c.toString())) {
                c0421b.f52c.setBackgroundResource(R.drawable.photo_gallery_pressed);
                c0421b.f53d.setVisibility(0);
                this.f58e = i;
                return;
            }
            c0421b.f52c.setBackgroundResource(R.drawable.bg_channelname_fullscreen);
            c0421b.f53d.setVisibility(4);
        }
    }

    public void m71a(String str) {
        this.f56c = new StringBuffer(str);
        notifyDataSetChanged();
    }

    public void m72b() {
        this.f54a = C0536g.m630a("Channel.txt");
        if (this.f54a.size() == 0) {
            List a;
            List arrayList = new ArrayList();
            C0521a c0521a = new C0521a(DTVApplication.m750a());
            try {
                a = c0521a.m589a();
            } catch (SQLException e) {
                e.printStackTrace();
                a = arrayList;
            }
            for (int i = 0; i < a.size(); i++) {
                String b = ((C0500a) a.get(i)).m492b();
                String a2 = ((C0500a) a.get(i)).m489a();
                boolean c = ((C0500a) a.get(i)).m495c();
                String d = ((C0500a) a.get(i)).m496d();
                if (!c) {
                    this.f54a.add(new C0509a((long) i, 0, b, SwipeableItemConstants.REACTION_CAN_SWIPE_BOTH_V, a2, false, d));
                }
            }
            try {
                c0521a.m594c();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
    }

    public List<C0509a> m73c() {
        return (this.f54a == null || this.f54a.size() <= 0) ? null : this.f54a;
    }

    public int m74d() {
        return this.f58e;
    }

    public int getItemCount() {
        return this.f54a.size();
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public /* synthetic */ void onBindViewHolder(ViewHolder viewHolder, int i) {
        m70a((C0421b) viewHolder, i);
    }

    public /* synthetic */ ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return m68a(viewGroup, i);
    }
}
