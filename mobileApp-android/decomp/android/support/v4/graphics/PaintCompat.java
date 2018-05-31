package android.support.v4.graphics;

import android.graphics.Paint;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;

public final class PaintCompat {
    private PaintCompat() {
    }

    public static boolean hasGlyph(@NonNull Paint paint, @NonNull String str) {
        return VERSION.SDK_INT >= 23 ? PaintCompatApi23.hasGlyph(paint, str) : PaintCompatGingerbread.hasGlyph(paint, str);
    }
}
