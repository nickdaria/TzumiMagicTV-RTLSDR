package com.eardatek.special.player.system;

import com.blazevideo.libdtv.LibDTV;
import com.blazevideo.libdtv.LibDtvException;

public class C0563b {
    public static LibDTV m756a() throws LibDtvException {
        LibDTV existingInstance = LibDTV.getExistingInstance();
        if (existingInstance != null) {
            return existingInstance;
        }
        existingInstance = LibDTV.getInstance();
        existingInstance.init(DTVApplication.m750a());
        return existingInstance;
    }
}
