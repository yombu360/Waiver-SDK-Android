package com.yombu.waiverlibrary;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class LibraryAndroid {
    public static String getSerialNumber() {
        String serialNumber;

        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);

            serialNumber = (String) get.invoke(c, "gsm.sn1");
            if (serialNumber.equals(""))
                serialNumber = (String) get.invoke(c, "ril.serialnumber");
            if (serialNumber.equals(""))
                serialNumber = (String) get.invoke(c, "ro.serialno");
            if (serialNumber.equals(""))
                serialNumber = (String) get.invoke(c, "sys.serialnumber");
            if (serialNumber.equals(""))
                serialNumber = Build.SERIAL;

            // If none of the methods above worked
            if (serialNumber.equals(""))
                serialNumber = null;

        } catch (NoSuchMethodException | IllegalAccessException | ClassNotFoundException | InvocationTargetException e) {
            serialNumber = null;
        }

        return serialNumber;
    }

    public static String getDeviceName(Context context) {
        return Settings.Global.getString(context.getApplicationContext().getContentResolver(), "device_name");
    }
}
