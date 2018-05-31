package com.eardatek.special.player.p004f;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ItemAnimator;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import com.eardatek.special.atsc.R;
import com.eardatek.special.player.actitivy.EardatekVersion2Activity;
import com.eardatek.special.player.p003a.C0440d;
import com.eardatek.special.player.p003a.C0440d.C0435a;
import com.eardatek.special.player.p003a.C0440d.C0437c;
import com.eardatek.special.player.p005i.C0533d;
import com.eardatek.special.player.p005i.C0536g;
import com.eardatek.special.player.p005i.C0539i;
import com.eardatek.special.player.p005i.C0549n;
import com.eardatek.special.player.p006c.C0502a;
import com.eardatek.special.player.p009e.C0508a;
import com.eardatek.special.player.p009e.C0508a.C0507a;
import com.eardatek.special.player.system.DTVApplication;
import com.eardatek.special.player.widget.CustomEditText;
import com.h6ah4i.android.widget.advrecyclerview.animator.SwipeDismissItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.decoration.ItemShadowDecorator;
import com.h6ah4i.android.widget.advrecyclerview.decoration.SimpleListDividerDecorator;
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.RecyclerViewSwipeManager;
import com.h6ah4i.android.widget.advrecyclerview.touchguard.RecyclerViewTouchActionGuardManager;
import com.h6ah4i.android.widget.advrecyclerview.utils.WrapperAdapterUtils;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.greenrobot.eventbus.C0732c;
import org.greenrobot.eventbus.C0739j;
import org.greenrobot.eventbus.ThreadMode;

public class C0520b extends C0511a implements C0437c {
    private static final String f389c = C0520b.class.getSimpleName();
    private static C0447a f390n;
    private static int f391o;
    private static OnScrollListener f392p = new C05132();
    C0440d f393b;
    private RecyclerView f394d;
    private LayoutManager f395e;
    private Adapter f396f;
    private Adapter f397g;
    private RecyclerViewDragDropManager f398h;
    private RecyclerViewSwipeManager f399i;
    private RecyclerViewTouchActionGuardManager f400j;
    private View f401k;
    private ViewStub f402l;
    private View f403m;

    public interface C0447a {
        void mo2086a();

        void mo2087b();
    }

    class C05121 implements OnTouchListener {
        final /* synthetic */ C0520b f374a;

        C05121(C0520b c0520b) {
            this.f374a = c0520b;
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            this.f374a.m579h();
            return false;
        }
    }

    static class C05132 extends OnScrollListener {
        private int f375a = 0;
        private Lock f376b = new ReentrantLock();

        C05132() {
        }

        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            super.onScrolled(recyclerView, i, i2);
            if (this.f376b.tryLock()) {
                if (C0520b.f390n != null && Math.abs(i2) > C0520b.f391o) {
                    if (i2 > 0) {
                        if (this.f375a != 1) {
                            this.f375a = 1;
                            C0520b.f390n.mo2086a();
                        }
                    } else if (this.f375a != -1) {
                        this.f375a = -1;
                        C0520b.f390n.mo2087b();
                    }
                }
                this.f376b.unlock();
            }
        }
    }

    class C05143 implements OnClickListener {
        final /* synthetic */ C0520b f377a;

        C05143(C0520b c0520b) {
            this.f377a = c0520b;
        }

        public void onClick(View view) {
            C0502a.m504a(7, null);
        }
    }

    class C05154 implements C0435a {
        final /* synthetic */ C0520b f378a;

        C05154(C0520b c0520b) {
            this.f378a = c0520b;
        }

        public void mo2131a(int i) {
        }

        public void mo2132a(View view) {
            this.f378a.m563a(view);
        }

        public void mo2133b(View view) {
            this.f378a.m570b(view);
        }

        public void mo2134c(View view) {
            this.f378a.m574d(view);
        }

        public void mo2135d(View view) {
            this.f378a.m572c(view);
        }
    }

    public static class C0519b implements Runnable {
        private WeakReference<C0520b> f388a;

        public C0519b(C0520b c0520b) {
            this.f388a = new WeakReference(c0520b);
        }

        public void run() {
            C0520b c0520b = (C0520b) this.f388a.get();
            if (c0520b != null) {
                C0539i.m643b("ListUtil", "start....");
                C0536g.m631a(((C0440d) c0520b.f396f).m124d(), "Channel.txt");
            }
        }
    }

    private void m562a(int i) {
        C0502a c0502a = new C0502a();
        c0502a.m506a(9);
        c0502a.m507a(Integer.valueOf(i));
        C0732c.m1501a().m1515c(c0502a);
    }

    private void m563a(View view) {
        if (((C0440d) this.f396f).m121c() >= 0) {
            m579h();
            return;
        }
        int childAdapterPosition = this.f394d.getChildAdapterPosition(view);
        if (childAdapterPosition != -1) {
            C0507a a = mo2138d().mo2128a(childAdapterPosition);
            if (a.mo2123d()) {
                a.mo2120a(false);
                this.f396f.notifyItemChanged(childAdapterPosition);
            } else if (a.mo2126g()) {
                m583l();
            } else {
                String c = ((C0440d) this.f396f).m122c(childAdapterPosition).mo2122c();
                ((C0440d) this.f396f).m115a(((C0440d) this.f396f).m122c(childAdapterPosition).mo2122c(), childAdapterPosition);
                if (c != null) {
                    ((EardatekVersion2Activity) getActivity()).m356a(c, childAdapterPosition);
                }
            }
        }
    }

    public static void m564a(C0447a c0447a) {
        f390n = c0447a;
    }

    private void m567a(String str, int i) {
        View inflate = getActivity().getLayoutInflater().inflate(R.layout.dialog_rename, null);
        final Dialog dialog = new Dialog(getActivity(), R.style.transparentFrameWindowStyle);
        final CustomEditText customEditText = (CustomEditText) inflate.findViewById(R.id.name_edit);
        Button button = (Button) inflate.findViewById(R.id.cancel);
        final String str2 = str;
        final int i2 = i;
        ((Button) inflate.findViewById(R.id.confirm)).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ C0520b f383e;

            public void onClick(View view) {
                Object obj = customEditText.getText().toString();
                if (!TextUtils.isEmpty(obj)) {
                    this.f383e.mo2138d().mo2130a(str2, obj, i2);
                    this.f383e.f396f.notifyItemChanged(i2);
                }
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        button.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ C0520b f385b;

            public void onClick(View view) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        dialog.setContentView(inflate, new LayoutParams(-1, -2));
        Window window = dialog.getWindow();
        window.setGravity(17);
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams attributes = window.getAttributes();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
        attributes.x = 0;
        attributes.y = 0;
        attributes.width = C0533d.m627a(DTVApplication.m750a(), 345.0f);
        attributes.height = -2;
        attributes.alpha = 0.9f;
        dialog.onWindowAttributesChanged(attributes);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void m568a(boolean z) {
        if (this.f402l == null) {
            this.f402l = (ViewStub) this.f401k.findViewById(R.id.viewstub);
            this.f403m = this.f402l.inflate();
            ((Button) this.f403m.findViewById(R.id.scan_channel)).setOnClickListener(new C05143(this));
        }
        if (z) {
            this.f403m.setVisibility(0);
        } else {
            this.f403m.setVisibility(8);
        }
    }

    private void m570b(View view) {
        int childAdapterPosition = this.f394d.getChildAdapterPosition(view);
        if (childAdapterPosition != -1) {
            if (((C0440d) this.f396f).m121c() == childAdapterPosition) {
                ((C0440d) this.f396f).m118b(-1);
            }
            if (((C0440d) this.f396f).m122c(childAdapterPosition).mo2124e() == 1) {
                ((C0440d) this.f396f).m110a();
            }
            if (((C0440d) this.f396f).m128f().equals(((C0440d) this.f396f).m123d(childAdapterPosition))) {
                ((C0440d) this.f396f).m115a("", 0);
                C0502a c0502a = new C0502a();
                c0502a.m506a(8);
                C0732c.m1501a().m1515c(c0502a);
            }
            ((C0440d) this.f396f).m126e(childAdapterPosition);
            this.f396f.notifyItemRemoved(childAdapterPosition);
            this.f396f.notifyDataSetChanged();
            if (this.f396f.getItemCount() == 0) {
                m568a(true);
            }
        }
        new Thread(new C0519b(this)).start();
    }

    private void m572c(View view) {
        int childAdapterPosition = this.f394d.getChildAdapterPosition(view);
        if (childAdapterPosition != -1) {
            C0507a a = mo2138d().mo2128a(childAdapterPosition);
            if (a.mo2123d()) {
                a.mo2120a(false);
                this.f396f.notifyItemChanged(childAdapterPosition);
            }
            if (((C0440d) this.f396f).m127f(childAdapterPosition) == 1) {
                ((C0440d) this.f396f).m131h(childAdapterPosition);
                ((C0440d) this.f396f).m110a();
                if (((C0440d) this.f396f).m128f().equals(((C0440d) this.f396f).m123d(childAdapterPosition))) {
                    m562a(C0549n.m709b(getActivity(), "topCount"));
                }
                m581j();
                new Thread(new C0519b(this)).start();
                return;
            }
            ((C0440d) this.f396f).m130g(childAdapterPosition);
            ((C0440d) this.f396f).m117b();
            if (((C0440d) this.f396f).m128f().equals(((C0440d) this.f396f).m123d(childAdapterPosition))) {
                m562a(0);
            }
            m581j();
            new Thread(new C0519b(this)).start();
        }
    }

    private void m574d(View view) {
        C0539i.m643b(f389c, "handleContainerLongClick");
        m579h();
        int childAdapterPosition = this.f394d.getChildAdapterPosition(view);
        if (childAdapterPosition != -1) {
            C0539i.m643b(f389c, "showRenameDialog");
            m567a(((C0440d) this.f396f).m122c(childAdapterPosition).mo2122c(), childAdapterPosition);
        }
    }

    private void m578g() {
        this.f395e = new LinearLayoutManager(getContext());
        this.f400j = new RecyclerViewTouchActionGuardManager();
        this.f400j.setInterceptVerticalScrollingWhileAnimationRunning(true);
        this.f400j.setEnabled(true);
        this.f398h = new RecyclerViewDragDropManager();
        this.f398h.setDraggingItemShadowDrawable((NinePatchDrawable) ContextCompat.getDrawable(getContext(), R.drawable.material_shadow_z3));
        this.f399i = new RecyclerViewSwipeManager();
        this.f393b = m580i();
        this.f393b.m111a(C0549n.m709b(getActivity(), "topCount"));
        this.f396f = this.f393b;
        if (this.f393b.m124d().size() == 0) {
            m568a(true);
        } else {
            m568a(false);
        }
        this.f397g = this.f398h.createWrappedAdapter(this.f393b);
        this.f397g = this.f399i.createWrappedAdapter(this.f397g);
        ItemAnimator swipeDismissItemAnimator = new SwipeDismissItemAnimator();
        swipeDismissItemAnimator.setSupportsChangeAnimations(false);
        this.f394d.setLayoutManager(this.f395e);
        this.f394d.setAdapter(this.f397g);
        this.f394d.setItemAnimator(swipeDismissItemAnimator);
        if (!m584m()) {
            this.f394d.addItemDecoration(new ItemShadowDecorator((NinePatchDrawable) ContextCompat.getDrawable(getContext(), R.drawable.material_shadow_z1)));
        }
        this.f394d.addItemDecoration(new SimpleListDividerDecorator(ContextCompat.getDrawable(getContext(), R.drawable.list_divider_h), true));
        this.f400j.attachRecyclerView(this.f394d);
        this.f399i.attachRecyclerView(this.f394d);
        this.f398h.attachRecyclerView(this.f394d);
        this.f394d.setOnTouchListener(new C05121(this));
        this.f394d.setOnScrollListener(f392p);
    }

    private void m579h() {
        if (((C0440d) this.f396f).m121c() >= 0) {
            C0507a a = mo2138d().mo2128a(((C0440d) this.f396f).m121c());
            if (a.mo2123d()) {
                a.mo2120a(false);
                this.f396f.notifyItemChanged(((C0440d) this.f396f).m121c());
            }
            ((C0440d) this.f396f).m118b(-1);
        }
    }

    private C0440d m580i() {
        C0440d c0440d = new C0440d(mo2138d());
        c0440d.m112a(new C05154(this));
        c0440d.m114a((C0437c) this);
        return c0440d;
    }

    private void m581j() {
        Collections.sort(((C0440d) this.f396f).m124d());
        this.f396f.notifyDataSetChanged();
    }

    private void m582k() {
        if (this.f398h != null) {
            this.f398h.release();
            this.f398h = null;
        }
        if (this.f399i != null) {
            this.f399i.release();
            this.f399i = null;
        }
        if (this.f397g != null) {
            WrapperAdapterUtils.releaseAll(this.f397g);
            this.f397g = null;
        }
        if (this.f400j != null) {
            this.f400j.release();
            this.f400j = null;
        }
    }

    private void m583l() {
        View inflate = getActivity().getLayoutInflater().inflate(R.layout.encrypt_dialog, null);
        final Dialog dialog = new Dialog(getActivity(), R.style.transparentFrameWindowStyle);
        ((Button) inflate.findViewById(R.id.yes)).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ C0520b f387b;

            public void onClick(View view) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        dialog.setContentView(inflate, new LayoutParams(-1, -2));
        Window window = dialog.getWindow();
        window.setGravity(17);
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams attributes = window.getAttributes();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
        attributes.x = 0;
        attributes.y = 0;
        attributes.width = C0533d.m627a(DTVApplication.m750a(), 345.0f);
        attributes.height = -2;
        attributes.alpha = 0.9f;
        dialog.onWindowAttributesChanged(attributes);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    private boolean m584m() {
        return VERSION.SDK_INT >= 21;
    }

    public void mo2136a() {
        m562a(((C0440d) this.f396f).m129g());
        new Thread(new C0519b(this)).start();
    }

    public void mo2137c() {
        C0549n.m706a(getActivity(), "topCount", 0);
        m582k();
        m578g();
    }

    public C0508a mo2138d() {
        return ((EardatekVersion2Activity) getActivity()).m360f();
    }

    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        C0732c.m1501a().m1510a((Object) this);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        mo2137c();
    }

    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        this.f401k = layoutInflater.inflate(R.layout.fragment_program, viewGroup, false);
        this.f394d = (RecyclerView) this.f401k.findViewById(R.id.gridViewChannels);
        m578g();
        return this.f401k;
    }

    public void onDestroyView() {
        super.onDestroyView();
        m582k();
        if (this.f394d != null) {
            this.f394d.setItemAnimator(null);
            this.f394d.setAdapter(null);
            this.f394d = null;
        }
        this.f396f = null;
        this.f395e = null;
        C0732c.m1501a().m1514b(this);
    }

    @C0739j(a = ThreadMode.MAIN)
    public void onMainEventBus(C0502a c0502a) {
        switch (c0502a.m505a()) {
            case 2:
                ((C0440d) this.f396f).m125e();
                C0549n.m706a(getActivity(), "topCount", 0);
                this.f394d.setAdapter(this.f396f);
                m568a(true);
                return;
            case 5:
                int intValue = ((Integer) c0502a.m508b()).intValue();
                ((C0440d) this.f396f).m115a(((C0440d) this.f396f).m123d(intValue), intValue);
                return;
            default:
                return;
        }
    }

    public void onPause() {
        this.f398h.cancelDrag();
        super.onPause();
    }

    public void onResume() {
        super.onResume();
    }
}
