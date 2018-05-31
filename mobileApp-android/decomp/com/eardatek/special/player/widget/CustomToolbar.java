package com.eardatek.special.player.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.Toolbar.LayoutParams;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.eardatek.special.atsc.R;
import com.eardatek.special.player.C0419R;
import com.eardatek.special.player.system.DTVApplication;

public class CustomToolbar extends Toolbar {
    private TextView f509a;
    private Button f510b;
    private Context f511c;
    private Button f512d;
    private View f513e;

    public CustomToolbar(Context context) {
        this(context, null);
    }

    public CustomToolbar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CustomToolbar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f511c = context;
        m758b();
        setContentInsetsRelative(10, 10);
        if (attributeSet != null) {
            TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(getContext(), attributeSet, C0419R.styleable.CustomToolbar, i, 0);
            Drawable drawable = obtainStyledAttributes.getDrawable(0);
            if (drawable != null) {
                setRightButtonIcon(drawable);
            }
            obtainStyledAttributes.recycle();
        }
    }

    private void m758b() {
        if (this.f513e == null) {
            this.f513e = LayoutInflater.from(getContext()).inflate(R.layout.toolbar, null);
            this.f509a = (TextView) this.f513e.findViewById(R.id.toolbar_title);
            this.f510b = (Button) this.f513e.findViewById(R.id.toolbar_rightButton);
            this.f512d = (Button) this.f513e.findViewById(R.id.toolbar_leftButton);
            addView(this.f513e, new LayoutParams(-1, -2, 1));
        }
    }

    public void m759a() {
        if (this.f509a != null) {
            this.f509a.setVisibility(0);
        }
    }

    public Button getRightButton() {
        return this.f510b;
    }

    public void setNavigationIcon(int i) {
        setNavigationIcon(AppCompatDrawableManager.get().getDrawable(DTVApplication.m750a(), i));
    }

    public void setNavigationIcon(Drawable drawable) {
        m758b();
        if (this.f512d != null && drawable != null) {
            this.f512d.setBackground(drawable);
            this.f512d.setVisibility(0);
        }
    }

    public void setNavigationOnClickListener(OnClickListener onClickListener) {
        if (this.f512d != null) {
            this.f512d.setOnClickListener(onClickListener);
        }
    }

    public void setRightButtonIcon(int i) {
        if (this.f510b != null) {
            this.f510b.setBackgroundResource(i);
            this.f510b.setVisibility(0);
        }
    }

    public void setRightButtonIcon(Drawable drawable) {
        if (this.f510b != null) {
            this.f510b.setBackground(drawable);
            this.f510b.setVisibility(0);
        }
    }

    public void setRightButtonOnClickListener(OnClickListener onClickListener) {
        this.f510b.setOnClickListener(onClickListener);
    }

    public void setTitle(int i) {
        setTitle(getContext().getText(i));
    }

    public void setTitle(CharSequence charSequence) {
        m758b();
        if (this.f509a != null) {
            this.f509a.setText(charSequence);
            m759a();
        }
    }
}
