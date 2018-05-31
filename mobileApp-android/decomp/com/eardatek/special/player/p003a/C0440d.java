package com.eardatek.special.player.p003a;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.eardatek.special.atsc.R;
import com.eardatek.special.player.p005i.C0534e;
import com.eardatek.special.player.p005i.C0539i;
import com.eardatek.special.player.p005i.C0540j;
import com.eardatek.special.player.p005i.C0549n;
import com.eardatek.special.player.p005i.C0558s;
import com.eardatek.special.player.p009e.C0508a;
import com.eardatek.special.player.p009e.C0508a.C0507a;
import com.eardatek.special.player.p009e.C0510b;
import com.eardatek.special.player.p009e.C0510b.C0509a;
import com.eardatek.special.player.system.DTVApplication;
import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultAction;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultActionDefault;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultActionMoveToSwipedDirection;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractDraggableSwipeableItemViewHolder;
import com.h6ah4i.android.widget.advrecyclerview.utils.RecyclerViewAdapterUtils;
import com.sherwin.eardatek.labswitch.C0589a;
import com.sherwin.libepgparser.C0595a;
import com.sherwin.libepgparser.C0595a.C0433a;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class C0440d extends Adapter<C0436b> implements DraggableItemAdapter<C0436b>, SwipeableItemAdapter<C0436b> {
    private static final String f99a = C0440d.class.getSimpleName();
    private static Map<String, WeakReference<TextView>> f100n = new HashMap();
    private static Handler f101o = new C04325();
    private int f102b = -1;
    private int f103c = 0;
    private volatile int f104d = -1;
    private boolean f105e = false;
    private StringBuffer f106f = new StringBuffer("");
    private C0437c f107g;
    private C0508a f108h;
    private C0435a f109i;
    private OnClickListener f110j;
    private OnClickListener f111k;
    private OnClickListener f112l;
    private OnLongClickListener f113m;

    class C04281 implements OnClickListener {
        final /* synthetic */ C0440d f79a;

        C04281(C0440d c0440d) {
            this.f79a = c0440d;
        }

        public void onClick(View view) {
            this.f79a.m96a(view);
        }
    }

    class C04292 implements OnClickListener {
        final /* synthetic */ C0440d f80a;

        C04292(C0440d c0440d) {
            this.f80a = c0440d;
        }

        public void onClick(View view) {
            this.f80a.m99b(view);
        }
    }

    class C04303 implements OnLongClickListener {
        final /* synthetic */ C0440d f81a;

        C04303(C0440d c0440d) {
            this.f81a = c0440d;
        }

        public boolean onLongClick(View view) {
            this.f81a.m101c(view);
            return true;
        }
    }

    class C04314 implements OnClickListener {
        final /* synthetic */ C0440d f82a;

        C04314(C0440d c0440d) {
            this.f82a = c0440d;
        }

        public void onClick(View view) {
            this.f82a.m103d(view);
        }
    }

    static class C04325 extends Handler {
        C04325() {
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    try {
                        WeakReference weakReference = (WeakReference) C0440d.f100n.get(C0595a.m819a(message.arg1, message.arg2));
                        if (weakReference != null) {
                            ((TextView) weakReference.get()).setText(message.obj.toString());
                            return;
                        } else {
                            Log.e("JNIMsg", "TextView null");
                            return;
                        }
                    } catch (Exception e) {
                        return;
                    }
                default:
                    return;
            }
        }
    }

    static class C04346 implements C0433a {
        C04346() {
        }

        public void mo2049a(int i, int i2, String str) {
            Log.e("JNIMsg", "OnCurrentEpgStrPutListener");
            C0540j.m646a(C0440d.f101o, 1, i, i2, str);
        }
    }

    public interface C0435a {
        void mo2131a(int i);

        void mo2132a(View view);

        void mo2133b(View view);

        void mo2134c(View view);

        void mo2135d(View view);
    }

    static class C0436b extends AbstractDraggableSwipeableItemViewHolder {
        FrameLayout f83a;
        RelativeLayout f84b;
        Button f85c;
        TextView f86d;
        TextView f87e;
        TextView f88f;
        ImageView f89g;
        private Button f90h;
        private TextView f91i;
        private View f92j;
        private Button f93k;

        C0436b(View view) {
            super(view);
            this.f83a = (FrameLayout) view.findViewById(R.id.container);
            this.f85c = (Button) view.findViewById(R.id.delete);
            this.f90h = (Button) view.findViewById(R.id.top);
            this.f86d = (TextView) view.findViewById(R.id.channel_freq);
            this.f87e = (TextView) view.findViewById(R.id.chanel_name);
            this.f91i = (TextView) view.findViewById(R.id.drag_handle);
            this.f84b = (RelativeLayout) view.findViewById(R.id.program_layout);
            this.f92j = view.findViewById(R.id.line_divide);
            this.f93k = (Button) view.findViewById(R.id.move);
            this.f89g = (ImageView) view.findViewById(R.id.lock_encrypt);
            if (C0589a.m788a().m804h()) {
                this.f88f = (TextView) view.findViewById(R.id.channel_epginfo);
            }
        }

        public View getSwipeableContainerView() {
            return this.f83a;
        }
    }

    public interface C0437c {
        void mo2136a();
    }

    private static class C0438d extends SwipeResultActionMoveToSwipedDirection {
        private C0440d f94a;
        private final int f95b;
        private boolean f96c;

        C0438d(C0440d c0440d, int i) {
            this.f94a = c0440d;
            this.f95b = i;
        }

        protected void onCleanUp() {
            super.onCleanUp();
            this.f94a = null;
        }

        protected void onPerformAction() {
            super.onPerformAction();
            C0507a a = this.f94a.f108h.mo2128a(this.f95b);
            if (!a.mo2123d()) {
                a.mo2120a(true);
                this.f94a.notifyItemChanged(this.f95b);
                this.f96c = true;
            }
        }

        protected void onSlideAnimationEnd() {
            super.onSlideAnimationEnd();
            if (this.f96c && this.f94a.f109i != null) {
                this.f94a.f109i.mo2131a(this.f95b);
            }
        }
    }

    private static class C0439e extends SwipeResultActionDefault {
        private C0440d f97a;
        private final int f98b;

        C0439e(C0440d c0440d, int i) {
            this.f97a = c0440d;
            this.f98b = i;
        }

        protected void onCleanUp() {
            super.onCleanUp();
            this.f97a = null;
        }

        protected void onPerformAction() {
            super.onPerformAction();
            C0507a a = this.f97a.f108h.mo2128a(this.f98b);
            if (a.mo2123d()) {
                a.mo2120a(false);
                this.f97a.notifyItemChanged(this.f98b);
            }
        }
    }

    static {
        C0595a.m824a(new C04346());
    }

    public C0440d(C0508a c0508a) {
        this.f108h = c0508a;
        this.f110j = new C04281(this);
        this.f111k = new C04292(this);
        this.f113m = new C04303(this);
        this.f112l = new C04314(this);
        setHasStableIds(true);
    }

    private void m95a(int i, int i2) {
        this.f108h.mo2129a(i, i2);
        notifyItemMoved(i, i2);
        if (this.f107g != null) {
            this.f107g.mo2136a();
        }
    }

    private void m96a(View view) {
        if (this.f109i != null) {
            this.f109i.mo2132a(RecyclerViewAdapterUtils.getParentViewHolderItemView(view));
        }
    }

    private void m99b(View view) {
        if (this.f109i != null) {
            this.f109i.mo2133b(RecyclerViewAdapterUtils.getParentViewHolderItemView(view));
        }
    }

    private void m101c(View view) {
        if (this.f109i != null) {
            this.f109i.mo2134c(RecyclerViewAdapterUtils.getParentViewHolderItemView(view));
        }
    }

    private void m103d(View view) {
        if (this.f109i != null) {
            this.f109i.mo2135d(RecyclerViewAdapterUtils.getParentViewHolderItemView(view));
        }
    }

    public int m107a(C0436b c0436b, int i, int i2, int i3) {
        return m120b(c0436b, i, i2, i3) ? 8194 : 2;
    }

    public C0436b m108a(ViewGroup viewGroup, int i) {
        return new C0436b(LayoutInflater.from(viewGroup.getContext()).inflate(C0589a.m788a().m804h() ? R.layout.video_list_item_grid_epg : R.layout.video_list_item_grid, viewGroup, false));
    }

    public SwipeResultAction m109a(C0436b c0436b, int i, int i2) {
        C0539i.m643b(f99a, "onSwipeItem(position = " + i + ", result = " + i2 + ")");
        switch (i2) {
            case 2:
                this.f102b = i;
                this.f105e = true;
                return new C0438d(this, i);
            default:
                if (i == -1) {
                    return null;
                }
                this.f102b = -1;
                this.f105e = false;
                return new C0439e(this, i);
        }
    }

    public void m110a() {
        if (this.f103c > 0) {
            this.f103c--;
            C0549n.m706a(DTVApplication.m750a(), "topCount", this.f103c);
        }
    }

    public void m111a(int i) {
        this.f103c = i;
    }

    public void m112a(C0435a c0435a) {
        this.f109i = c0435a;
    }

    public void m113a(C0436b c0436b, int i) {
        C0507a a = this.f108h.mo2128a(i);
        C0539i.m643b(f99a, a.mo2122c());
        String[] split = a.mo2122c().split("-");
        int parseInt = Integer.parseInt(split[0].substring(4));
        CharSequence f = a.mo2125f();
        boolean g = a.mo2126g();
        int parseInt2 = Integer.parseInt(split[3].substring(4));
        c0436b.f87e.setText(f);
        c0436b.f86d.setText(String.format(Locale.ENGLISH, "%dMHz", new Object[]{Integer.valueOf(parseInt / 1000)}));
        if (C0589a.m788a().m804h()) {
            String a2 = C0595a.m819a(parseInt, parseInt2);
            CharSequence a3 = C0595a.m820a(a2);
            f100n.put(a2, new WeakReference(c0436b.f88f));
            if (a3 != null) {
                c0436b.f88f.setText(a3);
            } else {
                c0436b.f88f.setText(DTVApplication.m752b().getString(R.string.noepgtrip));
            }
        }
        if (g) {
            c0436b.f89g.setVisibility(0);
        } else {
            c0436b.f89g.setVisibility(8);
        }
        c0436b.f85c.setOnClickListener(this.f111k);
        c0436b.f83a.setOnClickListener(this.f110j);
        c0436b.f83a.setOnLongClickListener(this.f113m);
        c0436b.f90h.setOnClickListener(this.f112l);
        parseInt2 = c0436b.f83a.getWidth();
        c0436b.f85c.setWidth(parseInt2 / 5);
        c0436b.f90h.setWidth(parseInt2 / 5);
        c0436b.f93k.setWidth(parseInt2 / 5);
        c0436b.f91i.setText(String.format(Locale.ENGLISH, "%d", new Object[]{Integer.valueOf(i + 1)}));
        if (a.mo2124e() == 1 && !this.f106f.toString().equals(a.mo2122c())) {
            c0436b.f84b.setBackgroundResource(R.drawable.top_and_selected_background);
            c0436b.f91i.setBackgroundResource(R.drawable.top_and_selected_background);
            c0436b.f91i.setTextColor(DTVApplication.m750a().getResources().getColor(R.color.app_background));
            c0436b.f90h.setText(R.string.cancel_top);
            c0436b.f92j.setBackgroundResource(R.drawable.photo_camera_normal);
        } else if (a.mo2124e() == 1 && this.f106f.toString().equals(a.mo2122c())) {
            c0436b.f84b.setBackgroundResource(R.drawable.photo_program_card_press);
            c0436b.f91i.setBackgroundResource(R.drawable.photo_program_card_press);
            c0436b.f91i.setTextColor(DTVApplication.m750a().getResources().getColor(R.color.white));
            this.f104d = i;
            c0436b.f90h.setText(R.string.cancel_top);
            c0436b.f92j.setBackgroundResource(R.drawable.photo_program_card_normal);
        } else if (a.mo2124e() == 0 && !this.f106f.toString().equals(a.mo2122c())) {
            c0436b.f84b.setBackgroundResource(R.drawable.photo_program_card_normal);
            c0436b.f91i.setBackgroundResource(R.drawable.photo_program_card_normal);
            c0436b.f91i.setTextColor(DTVApplication.m750a().getResources().getColor(R.color.app_background));
            c0436b.f90h.setText(R.string.top);
            c0436b.f92j.setBackgroundResource(R.drawable.photo_camera_normal);
        } else if (a.mo2124e() == 0 && this.f106f.toString().equals(a.mo2122c())) {
            c0436b.f84b.setBackgroundResource(R.drawable.photo_program_card_press);
            c0436b.f91i.setBackgroundResource(R.drawable.photo_program_card_press);
            c0436b.f91i.setTextColor(DTVApplication.m750a().getResources().getColor(R.color.white));
            this.f104d = i;
            c0436b.f90h.setText(R.string.top);
            c0436b.f92j.setBackgroundResource(R.drawable.photo_program_card_normal);
        }
        parseInt2 = c0436b.getDragStateFlags();
        parseInt = c0436b.getSwipeStateFlags();
        if (!((Integer.MIN_VALUE & parseInt2) == 0 && (Integer.MIN_VALUE & parseInt) == 0)) {
            if ((parseInt2 & 2) != 0) {
                parseInt2 = R.drawable.bg_item_dragging_active_state;
                C0534e.m628a(c0436b.f83a.getForeground());
            } else {
                parseInt2 = (parseInt2 & 1) != 0 ? R.drawable.bg_item_dragging_state : (parseInt & 2) != 0 ? R.drawable.bg_item_swiping_active_state : (parseInt & 1) != 0 ? R.drawable.bg_item_swiping_state : R.drawable.bg_item_normal_state;
            }
            c0436b.f83a.setBackgroundResource(parseInt2);
        }
        c0436b.setMaxLeftSwipeAmount(-0.6f);
        c0436b.setMaxRightSwipeAmount(0.0f);
        c0436b.setSwipeItemHorizontalSlideAmount(a.mo2123d() ? -0.6f : 0.0f);
    }

    public void m114a(C0437c c0437c) {
        this.f107g = c0437c;
    }

    public void m115a(String str, int i) {
        this.f106f = new StringBuffer(str);
        notifyDataSetChanged();
    }

    public ItemDraggableRange m116b(C0436b c0436b, int i) {
        return null;
    }

    public void m117b() {
        this.f103c++;
        C0549n.m706a(DTVApplication.m750a(), "topCount", this.f103c);
    }

    public void m118b(int i) {
        this.f102b = i;
    }

    public void m119b(C0436b c0436b, int i, int i2) {
        switch (i2) {
        }
        c0436b.itemView.setBackgroundResource(R.drawable.bg_swipe_item_neutral);
    }

    public boolean m120b(C0436b c0436b, int i, int i2, int i3) {
        FrameLayout frameLayout = c0436b.f83a;
        return C0558s.m731a(c0436b.f93k, i2, i3) && this.f105e && this.f102b == i;
    }

    public int m121c() {
        return this.f102b;
    }

    public C0509a m122c(int i) {
        return (C0509a) this.f108h.mo2128a(i);
    }

    public String m123d(int i) {
        return m122c(i).mo2122c();
    }

    public List<C0509a> m124d() {
        return ((C0510b) this.f108h).m549c();
    }

    public void m125e() {
        ((C0510b) this.f108h).m547b();
        notifyDataSetChanged();
    }

    public void m126e(int i) {
        ((C0510b) this.f108h).m548b(i);
    }

    public int m127f(int i) {
        return this.f108h.mo2128a(i).mo2124e();
    }

    public String m128f() {
        return this.f106f.toString();
    }

    public int m129g() {
        return this.f104d;
    }

    public void m130g(int i) {
        this.f108h.mo2128a(i).mo2118a(1);
        this.f108h.mo2128a(i).mo2119a(System.currentTimeMillis());
    }

    public int getItemCount() {
        return this.f108h.mo2127a();
    }

    public long getItemId(int i) {
        return this.f108h.mo2128a(i).mo2117a();
    }

    public int getItemViewType(int i) {
        return this.f108h.mo2128a(i).mo2121b();
    }

    public void m131h(int i) {
        this.f108h.mo2128a(i).mo2118a(0);
        this.f108h.mo2128a(i).mo2119a(System.currentTimeMillis());
    }

    public /* synthetic */ void onBindViewHolder(ViewHolder viewHolder, int i) {
        m113a((C0436b) viewHolder, i);
    }

    public boolean onCheckCanDrop(int i, int i2) {
        return true;
    }

    public /* synthetic */ boolean onCheckCanStartDrag(ViewHolder viewHolder, int i, int i2, int i3) {
        return m120b((C0436b) viewHolder, i, i2, i3);
    }

    public /* synthetic */ ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return m108a(viewGroup, i);
    }

    public /* synthetic */ ItemDraggableRange onGetItemDraggableRange(ViewHolder viewHolder, int i) {
        return m116b((C0436b) viewHolder, i);
    }

    public /* synthetic */ int onGetSwipeReactionType(ViewHolder viewHolder, int i, int i2, int i3) {
        return m107a((C0436b) viewHolder, i, i2, i3);
    }

    public void onMoveItem(int i, int i2) {
        C0539i.m643b(f99a, "onMoveItem(fromPosition = " + i + ", toPosition = " + i2 + ")");
        this.f105e = false;
        if (i != i2 && this.f108h.mo2128a(i).mo2124e() != 1) {
            if (i2 >= this.f103c || this.f103c <= 0) {
                if (m122c(i).mo2122c().equals(this.f106f.toString())) {
                    this.f104d = i2;
                }
                m95a(i, i2);
                return;
            }
            int i3 = this.f103c;
            if (m122c(i).mo2122c().equals(this.f106f.toString())) {
                this.f104d = i3;
            }
            m95a(i, i3);
        }
    }

    public /* synthetic */ void onSetSwipeBackground(ViewHolder viewHolder, int i, int i2) {
        m119b((C0436b) viewHolder, i, i2);
    }

    public /* synthetic */ SwipeResultAction onSwipeItem(ViewHolder viewHolder, int i, int i2) {
        return m109a((C0436b) viewHolder, i, i2);
    }
}
