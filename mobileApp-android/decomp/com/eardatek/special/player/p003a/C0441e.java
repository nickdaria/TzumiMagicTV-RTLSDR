package com.eardatek.special.player.p003a;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;
import com.eardatek.special.player.p007b.C0501b;
import java.util.ArrayList;
import java.util.List;

public class C0441e extends FragmentPagerAdapter {
    private List<C0501b> f114a;
    private List<Fragment> f115b = new ArrayList(3);
    private FragmentManager f116c;
    private boolean[] f117d = new boolean[]{false, false, false, false};

    public C0441e(FragmentManager fragmentManager, List<C0501b> list) {
        super(fragmentManager);
        this.f114a = list;
        this.f116c = fragmentManager;
    }

    public int getCount() {
        return this.f114a.size();
    }

    public Fragment getItem(int i) {
        Fragment fragment;
        try {
            fragment = (Fragment) ((C0501b) this.f114a.get(i)).m503b().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            fragment = null;
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
            fragment = null;
        }
        this.f115b.add(fragment);
        return fragment;
    }

    public int getItemPosition(Object obj) {
        return -2;
    }

    public Object instantiateItem(ViewGroup viewGroup, int i) {
        Object obj = (Fragment) super.instantiateItem(viewGroup, i);
        String tag = obj.getTag();
        if (this.f117d[i % this.f117d.length]) {
            FragmentTransaction beginTransaction = this.f116c.beginTransaction();
            beginTransaction.remove(obj);
            try {
                obj = (Fragment) ((C0501b) this.f114a.get(i)).m503b().newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
            }
            beginTransaction.add(viewGroup.getId(), obj, tag);
            beginTransaction.attach(obj);
            beginTransaction.commit();
            this.f117d[i % this.f117d.length] = false;
        }
        return obj;
    }
}
