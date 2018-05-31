package com.eardatek.special.player.p005i;

import com.eardatek.special.player.p009e.C0510b.C0509a;
import com.eardatek.special.player.system.DTVApplication;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class C0536g {
    private static final String f434a = C0536g.class.getSimpleName();

    public static List<C0509a> m630a(String str) {
        List<C0509a> list;
        FileNotFoundException e;
        IOException e2;
        ClassNotFoundException e3;
        ArrayList arrayList = new ArrayList();
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(DTVApplication.m750a().openFileInput(str));
            list = (List) objectInputStream.readObject();
            try {
                objectInputStream.close();
            } catch (FileNotFoundException e4) {
                e = e4;
                C0539i.m643b(f434a, "FileNotFoundException");
                e.printStackTrace();
                return list;
            } catch (IOException e5) {
                e2 = e5;
                C0539i.m643b(f434a, "IOException");
                e2.printStackTrace();
                return list;
            } catch (ClassNotFoundException e6) {
                e3 = e6;
                C0539i.m643b(f434a, "ClassNotFoundException");
                e3.printStackTrace();
                return list;
            }
        } catch (FileNotFoundException e7) {
            FileNotFoundException fileNotFoundException = e7;
            list = arrayList;
            e = fileNotFoundException;
            C0539i.m643b(f434a, "FileNotFoundException");
            e.printStackTrace();
            return list;
        } catch (IOException e8) {
            IOException iOException = e8;
            list = arrayList;
            e2 = iOException;
            C0539i.m643b(f434a, "IOException");
            e2.printStackTrace();
            return list;
        } catch (ClassNotFoundException e9) {
            ClassNotFoundException classNotFoundException = e9;
            list = arrayList;
            e3 = classNotFoundException;
            C0539i.m643b(f434a, "ClassNotFoundException");
            e3.printStackTrace();
            return list;
        }
        return list;
    }

    public static void m631a(List<C0509a> list, String str) {
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(DTVApplication.m750a().openFileOutput(str, 0));
            objectOutputStream.writeObject(list);
            C0539i.m643b(f434a, "success");
            objectOutputStream.close();
        } catch (FileNotFoundException e) {
            C0539i.m643b(f434a, "FileNotFoundException");
            e.printStackTrace();
        } catch (IOException e2) {
            C0539i.m643b(f434a, "IOException");
            e2.printStackTrace();
        }
    }

    public static void m632b(String str) {
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
        ArrayList arrayList = new ArrayList();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(DTVApplication.m750a().openFileOutput(str, 0));
            objectOutputStream.writeObject(arrayList);
            C0539i.m643b(f434a, "success");
            objectOutputStream.close();
        } catch (FileNotFoundException e) {
            C0539i.m643b(f434a, "FileNotFoundException");
            e.printStackTrace();
        } catch (IOException e2) {
            C0539i.m643b(f434a, "IOException");
            e2.printStackTrace();
        }
    }
}
