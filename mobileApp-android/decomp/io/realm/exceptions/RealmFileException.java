package io.realm.exceptions;

import io.realm.internal.Keep;

@Keep
public class RealmFileException extends RuntimeException {
    private final Kind kind;

    @Keep
    public enum Kind {
        ACCESS_ERROR,
        PERMISSION_DENIED,
        EXISTS,
        NOT_FOUND,
        INCOMPATIBLE_LOCK_FILE,
        FORMAT_UPGRADE_REQUIRED;

        static Kind getKind(byte b) {
            switch (b) {
                case (byte) 0:
                    return ACCESS_ERROR;
                case (byte) 1:
                    return PERMISSION_DENIED;
                case (byte) 2:
                    return EXISTS;
                case (byte) 3:
                    return NOT_FOUND;
                case (byte) 4:
                    return INCOMPATIBLE_LOCK_FILE;
                case (byte) 5:
                    return FORMAT_UPGRADE_REQUIRED;
                default:
                    throw new RuntimeException("Unknown value for RealmFileException kind.");
            }
        }
    }

    public RealmFileException(byte b, String str) {
        super(str);
        this.kind = Kind.getKind(b);
    }

    public RealmFileException(Kind kind, String str) {
        super(str);
        this.kind = kind;
    }

    public RealmFileException(Kind kind, String str, Throwable th) {
        super(str, th);
        this.kind = kind;
    }

    public RealmFileException(Kind kind, Throwable th) {
        super(th);
        this.kind = kind;
    }

    public Kind getKind() {
        return this.kind;
    }

    public String toString() {
        return String.format("%s Kind: %s.", new Object[]{super.toString(), this.kind});
    }
}
