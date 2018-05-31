package com.eardatek.special.player.p003a;

import android.content.Context;
import android.support.v4.view.InputDeviceCompat;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import com.eardatek.special.atsc.R;
import com.sherwin.libepgparser.p014b.C0600a;
import java.util.Date;
import java.util.List;

public class C0424b extends Adapter<ViewHolder> {
    private Context f65a;
    private List<C0600a> f66b;
    private LayoutInflater f67c;
    private String f68d;

    class C0423a extends ViewHolder {
        final /* synthetic */ C0424b f59a;
        private TextView f60b;
        private TextView f61c;
        private ImageView f62d;
        private View f63e;
        private View f64f;

        public C0423a(C0424b c0424b, View view) {
            this.f59a = c0424b;
            super(view);
            this.f60b = (TextView) view.findViewById(R.id.showepg_content_descrriptor);
            this.f61c = (TextView) view.findViewById(R.id.showepg_content_time);
            this.f62d = (ImageView) view.findViewById(R.id.showepg_content_timepoint);
            this.f63e = view.findViewById(R.id.showepg_content_topp);
            this.f64f = view.findViewById(R.id.showepg_content_bottomp);
        }
    }

    public C0424b(Context context, List<C0600a> list, String str) {
        this.f65a = context;
        this.f66b = list;
        this.f68d = str;
        this.f67c = LayoutInflater.from(context);
    }

    public int getItemCount() {
        return this.f66b.size();
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        C0600a c0600a = (C0600a) this.f66b.get(i);
        C0423a c0423a = (C0423a) viewHolder;
        ((C0423a) viewHolder).f60b.setText(c0600a.m840d());
        ((C0423a) viewHolder).f61c.setText(c0600a.m841e().m830a("HH:mm:ss").m832c(" - HH:mm:ss").toString());
        int minutes = c0600a.m838b().getMinutes() + (c0600a.m838b().getHours() * 60);
        LayoutParams layoutParams = ((C0423a) viewHolder).f64f.getLayoutParams();
        layoutParams.height = (minutes * 2) + 120;
        ((C0423a) viewHolder).f64f.setLayoutParams(layoutParams);
        Date date = new Date();
        if (date.after(c0600a.m837a()) && date.before(c0600a.m839c())) {
            ((C0423a) viewHolder).f60b.setTextColor(InputDeviceCompat.SOURCE_ANY);
            ((C0423a) viewHolder).f61c.setTextColor(InputDeviceCompat.SOURCE_ANY);
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new C0423a(this, this.f67c.inflate(R.layout.showepg_content_item, viewGroup, false));
    }
}
