package com.eardatek.special.player.p005i;

public class C0538h {

    public static class C0537a {
        private int f435a;
        private int f436b;
        private int f437c;
        private int f438d;
        private boolean f439e;
        private boolean f440f;

        public int m633a() {
            return this.f435a;
        }

        public void m634a(int i) {
            this.f435a = i;
        }

        public void m635a(boolean z) {
            this.f439e = z;
        }

        public int m636b() {
            return this.f438d;
        }

        public void m637b(int i) {
            this.f436b = i;
        }

        public void m638b(boolean z) {
            this.f440f = z;
        }

        public void m639c(int i) {
            this.f437c = i;
        }

        public void m640d(int i) {
            this.f438d = i;
        }
    }

    public static C0537a m641a(String str) {
        boolean z = true;
        String[] split = str.split("-");
        if (split.length != 7) {
            return null;
        }
        int parseInt = Integer.parseInt(split[0].substring(4));
        int parseInt2 = Integer.parseInt(split[1].substring(2));
        int parseInt3 = Integer.parseInt(split[2].substring(3));
        int parseInt4 = Integer.parseInt(split[3].substring(4));
        int parseInt5 = Integer.parseInt(split[4].substring(7));
        int parseInt6 = Integer.parseInt(split[5].substring(9));
        Integer.parseInt(split[6].substring(9));
        C0537a c0537a = new C0537a();
        c0537a.m634a(parseInt);
        c0537a.m637b(parseInt2);
        c0537a.m639c(parseInt3);
        c0537a.m640d(parseInt4);
        c0537a.m635a(parseInt5 == 1);
        if (parseInt6 != 1) {
            z = false;
        }
        c0537a.m638b(z);
        return c0537a;
    }
}
