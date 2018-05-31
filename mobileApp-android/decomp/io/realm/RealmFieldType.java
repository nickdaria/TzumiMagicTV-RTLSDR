package io.realm;

import io.realm.internal.Keep;
import java.nio.ByteBuffer;
import java.util.Date;

@Keep
public enum RealmFieldType {
    INTEGER(0),
    BOOLEAN(1),
    STRING(2),
    BINARY(4),
    UNSUPPORTED_TABLE(5),
    UNSUPPORTED_MIXED(6),
    UNSUPPORTED_DATE(7),
    DATE(8),
    FLOAT(9),
    DOUBLE(10),
    OBJECT(12),
    LIST(13);
    
    private static RealmFieldType[] typeList;
    private final int nativeValue;

    static {
        typeList = new RealmFieldType[15];
        RealmFieldType[] values = values();
        int i;
        while (i < values.length) {
            typeList[values[i].nativeValue] = values[i];
            i++;
        }
    }

    private RealmFieldType(int i) {
        this.nativeValue = i;
    }

    public static RealmFieldType fromNativeValue(int i) {
        if (i >= 0 && i < typeList.length) {
            RealmFieldType realmFieldType = typeList[i];
            if (realmFieldType != null) {
                return realmFieldType;
            }
        }
        throw new IllegalArgumentException("Invalid native Realm type: " + i);
    }

    public int getNativeValue() {
        return this.nativeValue;
    }

    public boolean isValid(Object obj) {
        switch (this.nativeValue) {
            case 0:
                return (obj instanceof Long) || (obj instanceof Integer) || (obj instanceof Short) || (obj instanceof Byte);
            case 1:
                return obj instanceof Boolean;
            case 2:
                return obj instanceof String;
            case 4:
                return (obj instanceof byte[]) || (obj instanceof ByteBuffer);
            case 5:
                return obj == null || (obj instanceof Object[][]);
            case 7:
                return obj instanceof Date;
            case 8:
                return obj instanceof Date;
            case 9:
                return obj instanceof Float;
            case 10:
                return obj instanceof Double;
            case 12:
            case 13:
            case 14:
                return false;
            default:
                throw new RuntimeException("Unsupported Realm type:  " + this);
        }
    }
}
