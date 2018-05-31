package com.eardatek.special.player.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import com.eardatek.special.atsc.R;

public class CustomEditText extends AppCompatEditText implements TextWatcher, OnFocusChangeListener, OnTouchListener {
    private Drawable f506a;
    private OnFocusChangeListener f507b;
    private OnTouchListener f508c;

    public CustomEditText(Context context) {
        super(context);
        m757a(context);
    }

    public CustomEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m757a(context);
    }

    public CustomEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        m757a(context);
    }

    private void m757a(Context context) {
        Drawable wrap = DrawableCompat.wrap(ContextCompat.getDrawable(context, R.drawable.cleartext));
        DrawableCompat.setTint(wrap, getCurrentHintTextColor());
        this.f506a = wrap;
        this.f506a.setBounds(0, 0, this.f506a.getIntrinsicHeight(), this.f506a.getIntrinsicHeight());
        setClearIconVisible(false);
        super.setOnTouchListener(this);
        super.setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    private void setClearIconVisible(boolean z) {
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], z ? this.f506a : null, getCompoundDrawables()[3]);
    }

    public void afterTextChanged(Editable editable) {
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public void onFocusChange(View view, boolean z) {
        boolean z2 = false;
        if (z) {
            if (getText().length() > 0) {
                z2 = true;
            }
            setClearIconVisible(z2);
        } else {
            setClearIconVisible(false);
        }
        if (this.f507b != null) {
            this.f507b.onFocusChange(view, z);
        }
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        if (isFocused()) {
            setClearIconVisible(charSequence.length() > 0);
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        if (!this.f506a.isVisible() || x <= (getWidth() - getPaddingRight()) - this.f506a.getIntrinsicWidth()) {
            return this.f508c != null && this.f508c.onTouch(view, motionEvent);
        } else {
            if (motionEvent.getAction() != 1) {
                return true;
            }
            setError(null);
            setText("");
            return true;
        }
    }

    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        this.f507b = onFocusChangeListener;
    }

    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.f508c = onTouchListener;
    }
}
