package com.sherwin.tvbox.firmwareupdateclient;

import android.content.Context;
import android.os.Environment;
import java.util.HashMap;

public class C0622b {
    public static final String f706a = (Environment.getExternalStorageDirectory() + "/Tevemo/update");
    private static final HashMap<String, String> f707b = new HashMap();
    private static int[] f708c = new int[]{C0601R.string.update_type_normal, C0601R.string.update_type_security, C0601R.string.update_type_necessity};

    static {
        f707b.put("DTMB", "http://www.eardatek.com/UploadFiles/dtvboxupdate/update.asp");
        f707b.put("DVB-T/T2", "http://www.eardatek.com/UploadFiles/dtvboxupdate_dvbt/update.asp");
        f707b.put("ATSC", "http://www.eardatek.com/UploadFiles/dtvboxupdate_atsc/update.asp");
        f707b.put("ISDBT", "http://www.eardatek.com/UploadFiles/dtvboxupdate_isdbt/update.asp");
        f707b.put("DVBS", "http://www.eardatek.com/UploadFiles/dtvboxupdate_dvbs/update.asp");
    }

    public static String m892a(Context context, int i) {
        try {
            return context.getString(f708c[i]);
        } catch (Exception e) {
            return "";
        }
    }

    public static String m893a(String str) {
        return (String) f707b.get(str);
    }
}
