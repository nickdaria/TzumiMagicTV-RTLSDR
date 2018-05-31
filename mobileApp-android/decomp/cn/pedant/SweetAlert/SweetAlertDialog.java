package cn.pedant.SweetAlert;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.pnikosis.materialishprogress.ProgressWheel;
import java.util.List;

public class SweetAlertDialog extends Dialog implements OnClickListener {
    public static final int CUSTOM_IMAGE_TYPE = 4;
    public static final int ERROR_TYPE = 1;
    public static final int NORMAL_TYPE = 0;
    public static final int PROGRESS_TYPE = 5;
    public static final int SUCCESS_TYPE = 2;
    public static final int WARNING_TYPE = 3;
    private int mAlertType;
    private Button mCancelButton;
    private OnSweetClickListener mCancelClickListener;
    private String mCancelText;
    private boolean mCloseFromCancel;
    private Button mConfirmButton;
    private OnSweetClickListener mConfirmClickListener;
    private String mConfirmText;
    private String mContentText;
    private TextView mContentTextView;
    private ImageView mCustomImage;
    private Drawable mCustomImgDrawable;
    private View mDialogView;
    private FrameLayout mErrorFrame;
    private Animation mErrorInAnim;
    private ImageView mErrorX;
    private AnimationSet mErrorXInAnim;
    private AnimationSet mModalInAnim;
    private AnimationSet mModalOutAnim;
    private Animation mOverlayOutAnim;
    private FrameLayout mProgressFrame;
    private ProgressHelper mProgressHelper;
    private boolean mShowCancel;
    private boolean mShowContent;
    private Animation mSuccessBowAnim;
    private FrameLayout mSuccessFrame;
    private AnimationSet mSuccessLayoutAnimSet;
    private View mSuccessLeftMask;
    private View mSuccessRightMask;
    private SuccessTickView mSuccessTick;
    private String mTitleText;
    private TextView mTitleTextView;
    private FrameLayout mWarningFrame;

    class C03761 implements AnimationListener {

        class C03751 implements Runnable {
            C03751() {
            }

            public void run() {
                if (SweetAlertDialog.this.mCloseFromCancel) {
                    super.cancel();
                } else {
                    super.dismiss();
                }
            }
        }

        C03761() {
        }

        public void onAnimationEnd(Animation animation) {
            SweetAlertDialog.this.mDialogView.setVisibility(8);
            SweetAlertDialog.this.mDialogView.post(new C03751());
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
        }
    }

    class C03772 extends Animation {
        C03772() {
        }

        protected void applyTransformation(float f, Transformation transformation) {
            LayoutParams attributes = SweetAlertDialog.this.getWindow().getAttributes();
            attributes.alpha = 1.0f - f;
            SweetAlertDialog.this.getWindow().setAttributes(attributes);
        }
    }

    public interface OnSweetClickListener {
        void onClick(SweetAlertDialog sweetAlertDialog);
    }

    public SweetAlertDialog(Context context) {
        this(context, 0);
    }

    public SweetAlertDialog(Context context, int i) {
        super(context, C0373R.style.alert_dialog);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        this.mProgressHelper = new ProgressHelper(context);
        this.mAlertType = i;
        this.mErrorInAnim = OptAnimationLoader.loadAnimation(getContext(), C0373R.anim.error_frame_in);
        this.mErrorXInAnim = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), C0373R.anim.error_x_in);
        if (VERSION.SDK_INT <= 10) {
            List animations = this.mErrorXInAnim.getAnimations();
            int i2 = 0;
            while (i2 < animations.size() && !(animations.get(i2) instanceof AlphaAnimation)) {
                i2++;
            }
            if (i2 < animations.size()) {
                animations.remove(i2);
            }
        }
        this.mSuccessBowAnim = OptAnimationLoader.loadAnimation(getContext(), C0373R.anim.success_bow_roate);
        this.mSuccessLayoutAnimSet = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), C0373R.anim.success_mask_layout);
        this.mModalInAnim = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), C0373R.anim.modal_in);
        this.mModalOutAnim = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), C0373R.anim.modal_out);
        this.mModalOutAnim.setAnimationListener(new C03761());
        this.mOverlayOutAnim = new C03772();
        this.mOverlayOutAnim.setDuration(120);
    }

    private void changeAlertType(int i, boolean z) {
        this.mAlertType = i;
        if (this.mDialogView != null) {
            if (!z) {
                restore();
            }
            switch (this.mAlertType) {
                case 1:
                    this.mErrorFrame.setVisibility(0);
                    break;
                case 2:
                    this.mSuccessFrame.setVisibility(0);
                    this.mSuccessLeftMask.startAnimation((Animation) this.mSuccessLayoutAnimSet.getAnimations().get(0));
                    this.mSuccessRightMask.startAnimation((Animation) this.mSuccessLayoutAnimSet.getAnimations().get(1));
                    break;
                case 3:
                    this.mConfirmButton.setBackgroundResource(C0373R.drawable.red_button_background);
                    this.mWarningFrame.setVisibility(0);
                    break;
                case 4:
                    setCustomImage(this.mCustomImgDrawable);
                    break;
                case 5:
                    this.mProgressFrame.setVisibility(0);
                    this.mConfirmButton.setVisibility(8);
                    break;
            }
            if (!z) {
                playAnimation();
            }
        }
    }

    private void dismissWithAnimation(boolean z) {
        this.mCloseFromCancel = z;
        this.mConfirmButton.startAnimation(this.mOverlayOutAnim);
        this.mDialogView.startAnimation(this.mModalOutAnim);
    }

    private void playAnimation() {
        if (this.mAlertType == 1) {
            this.mErrorFrame.startAnimation(this.mErrorInAnim);
            this.mErrorX.startAnimation(this.mErrorXInAnim);
        } else if (this.mAlertType == 2) {
            this.mSuccessTick.startTickAnim();
            this.mSuccessRightMask.startAnimation(this.mSuccessBowAnim);
        }
    }

    private void restore() {
        this.mCustomImage.setVisibility(8);
        this.mErrorFrame.setVisibility(8);
        this.mSuccessFrame.setVisibility(8);
        this.mWarningFrame.setVisibility(8);
        this.mProgressFrame.setVisibility(8);
        this.mConfirmButton.setVisibility(0);
        this.mConfirmButton.setBackgroundResource(C0373R.drawable.blue_button_background);
        this.mErrorFrame.clearAnimation();
        this.mErrorX.clearAnimation();
        this.mSuccessTick.clearAnimation();
        this.mSuccessLeftMask.clearAnimation();
        this.mSuccessRightMask.clearAnimation();
    }

    public void cancel() {
        dismissWithAnimation(true);
    }

    public void changeAlertType(int i) {
        changeAlertType(i, false);
    }

    public void dismissWithAnimation() {
        dismissWithAnimation(false);
    }

    public int getAlerType() {
        return this.mAlertType;
    }

    public String getCancelText() {
        return this.mCancelText;
    }

    public String getConfirmText() {
        return this.mConfirmText;
    }

    public String getContentText() {
        return this.mContentText;
    }

    public ProgressHelper getProgressHelper() {
        return this.mProgressHelper;
    }

    public String getTitleText() {
        return this.mTitleText;
    }

    public boolean isShowCancelButton() {
        return this.mShowCancel;
    }

    public boolean isShowContentText() {
        return this.mShowContent;
    }

    public void onClick(View view) {
        if (view.getId() == C0373R.id.cancel_button) {
            if (this.mCancelClickListener != null) {
                this.mCancelClickListener.onClick(this);
            } else {
                dismissWithAnimation();
            }
        } else if (view.getId() != C0373R.id.confirm_button) {
        } else {
            if (this.mConfirmClickListener != null) {
                this.mConfirmClickListener.onClick(this);
            } else {
                dismissWithAnimation();
            }
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(C0373R.layout.alert_dialog);
        this.mDialogView = getWindow().getDecorView().findViewById(16908290);
        this.mTitleTextView = (TextView) findViewById(C0373R.id.title_text);
        this.mContentTextView = (TextView) findViewById(C0373R.id.content_text);
        this.mErrorFrame = (FrameLayout) findViewById(C0373R.id.error_frame);
        this.mErrorX = (ImageView) this.mErrorFrame.findViewById(C0373R.id.error_x);
        this.mSuccessFrame = (FrameLayout) findViewById(C0373R.id.success_frame);
        this.mProgressFrame = (FrameLayout) findViewById(C0373R.id.progress_dialog);
        this.mSuccessTick = (SuccessTickView) this.mSuccessFrame.findViewById(C0373R.id.success_tick);
        this.mSuccessLeftMask = this.mSuccessFrame.findViewById(C0373R.id.mask_left);
        this.mSuccessRightMask = this.mSuccessFrame.findViewById(C0373R.id.mask_right);
        this.mCustomImage = (ImageView) findViewById(C0373R.id.custom_image);
        this.mWarningFrame = (FrameLayout) findViewById(C0373R.id.warning_frame);
        this.mConfirmButton = (Button) findViewById(C0373R.id.confirm_button);
        this.mCancelButton = (Button) findViewById(C0373R.id.cancel_button);
        this.mProgressHelper.setProgressWheel((ProgressWheel) findViewById(C0373R.id.progressWheel));
        this.mConfirmButton.setOnClickListener(this);
        this.mCancelButton.setOnClickListener(this);
        setTitleText(this.mTitleText);
        setContentText(this.mContentText);
        setCancelText(this.mCancelText);
        setConfirmText(this.mConfirmText);
        changeAlertType(this.mAlertType, true);
    }

    protected void onStart() {
        this.mDialogView.startAnimation(this.mModalInAnim);
        playAnimation();
    }

    public SweetAlertDialog setCancelClickListener(OnSweetClickListener onSweetClickListener) {
        this.mCancelClickListener = onSweetClickListener;
        return this;
    }

    public SweetAlertDialog setCancelText(String str) {
        this.mCancelText = str;
        if (!(this.mCancelButton == null || this.mCancelText == null)) {
            showCancelButton(true);
            this.mCancelButton.setText(this.mCancelText);
        }
        return this;
    }

    public SweetAlertDialog setConfirmClickListener(OnSweetClickListener onSweetClickListener) {
        this.mConfirmClickListener = onSweetClickListener;
        return this;
    }

    public SweetAlertDialog setConfirmText(String str) {
        this.mConfirmText = str;
        if (!(this.mConfirmButton == null || this.mConfirmText == null)) {
            this.mConfirmButton.setText(this.mConfirmText);
        }
        return this;
    }

    public SweetAlertDialog setContentText(String str) {
        this.mContentText = str;
        if (!(this.mContentTextView == null || this.mContentText == null)) {
            showContentText(true);
            this.mContentTextView.setText(this.mContentText);
        }
        return this;
    }

    public SweetAlertDialog setCustomImage(int i) {
        return setCustomImage(getContext().getResources().getDrawable(i));
    }

    public SweetAlertDialog setCustomImage(Drawable drawable) {
        this.mCustomImgDrawable = drawable;
        if (!(this.mCustomImage == null || this.mCustomImgDrawable == null)) {
            this.mCustomImage.setVisibility(0);
            this.mCustomImage.setImageDrawable(this.mCustomImgDrawable);
        }
        return this;
    }

    public SweetAlertDialog setTitleText(String str) {
        this.mTitleText = str;
        if (!(this.mTitleTextView == null || this.mTitleText == null)) {
            this.mTitleTextView.setText(this.mTitleText);
        }
        return this;
    }

    public SweetAlertDialog showCancelButton(boolean z) {
        this.mShowCancel = z;
        if (this.mCancelButton != null) {
            this.mCancelButton.setVisibility(this.mShowCancel ? 0 : 8);
        }
        return this;
    }

    public SweetAlertDialog showContentText(boolean z) {
        this.mShowContent = z;
        if (this.mContentTextView != null) {
            this.mContentTextView.setVisibility(this.mShowContent ? 0 : 8);
        }
        return this;
    }
}
