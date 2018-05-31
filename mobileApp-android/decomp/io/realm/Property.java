package io.realm;

class Property {
    private final long f743a;

    protected Property(long j) {
        this.f743a = j;
    }

    public Property(String str, RealmFieldType realmFieldType, boolean z, boolean z2, boolean z3) {
        this.f743a = nativeCreateProperty(str, realmFieldType.getNativeValue(), z, z2, !z3);
    }

    private static native void nativeClose(long j);

    private static native long nativeCreateProperty(String str, int i, boolean z, boolean z2, boolean z3);

    protected long m944a() {
        return this.f743a;
    }

    public void m945b() {
        if (this.f743a != 0) {
            nativeClose(this.f743a);
        }
    }
}
