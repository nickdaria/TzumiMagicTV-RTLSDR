package com.eardatek.special.player.p005i;

import java.util.regex.Pattern;

public class C0551p {
    public static boolean m715a(String str) {
        return Pattern.compile("^[0-9]+(.[0-9]+)?$").matcher(str).matches();
    }
}
