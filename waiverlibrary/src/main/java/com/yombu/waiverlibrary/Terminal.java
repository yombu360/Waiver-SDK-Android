package com.yombu.waiverlibrary;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.yakivmospan.scytale.Crypto;
import com.yakivmospan.scytale.Options;
import com.yakivmospan.scytale.Store;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;

import javax.crypto.SecretKey;

class Terminal {

    private static volatile Terminal ourInstance = null;

    private Gson gson;

    private boolean isRegistered = false;
    private String accessTokenLocation = "";
    private String accessToken = "";

    private String macAddress;
    private String deviceId;

    private ModelConfig deviceConfig;

    private Terminal(Context context) {
        gson = new Gson();
        String filePath = context.getFilesDir().getAbsolutePath();
        String accessTokenFilename = "access.dat";
        accessTokenLocation = filePath + System.getProperty("file.separator") + accessTokenFilename;

        setMacAddress();
        loadAccessToken(context);
    }

    public static Terminal getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new Terminal(context);
        }

        return ourInstance;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(Context context, String accessToken) {
        this.accessToken = accessToken;
        isRegistered = saveAccessToken(context) && getDeviceConfig(context) != null;
    }

    public String getMacAddress() {
        return macAddress;
    }

    private void setMacAddress() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return;
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    String hexValue = (Integer.toHexString(b & 0xFF)).toUpperCase();

                    res1.append((hexValue.length() == 1 ? "0" : "") + hexValue + ":");
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }

                macAddress = res1.toString();
            }
        } catch (SocketException e) {
        }

    }

    private boolean saveAccessToken(Context context) {
        boolean bResult = false;

        // Encrypting tokenString before writing in file using key from Android Key Store
        SecretKey key = getKeyFromKeyStore(context);
        Crypto crypto = new Crypto(Options.TRANSFORMATION_SYMMETRIC);
        String encryptedAccessToken = crypto.encrypt(accessToken, key);

        File f = new File(accessTokenLocation);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            if (accessToken != null) {
                writer.write(encryptedAccessToken);
                writer.flush();
                writer.close();

                bResult = true;
            }
        } catch (Exception e) {
        }

        return bResult;
    }

    private void loadAccessToken(Context context) {

        isRegistered = false;

        File f = new File(accessTokenLocation);

        if (f.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(f));
                String sToken = reader.readLine();

                reader.close();
                if (sToken != null) {
                    SecretKey key = getKeyFromKeyStore(context);
                    Crypto crypto = new Crypto(Options.TRANSFORMATION_SYMMETRIC);
                    accessToken = crypto.decrypt(sToken, key);

                    String deviceInfo = context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE).getString(Constants.SHARED_PREF_DEVICE_CONFIG, null);
                    if (accessToken.length() > 0 && deviceInfo != null) {
                        isRegistered = true;
                    }
                }

            } catch (Exception e) {
            }
        }

    }

    public ModelConfig getDeviceConfig(Context context) {
        if (deviceConfig == null) {
            String deviceConfigInfo = retrieveFromSharedPref(context, Constants.SHARED_PREF_DEVICE_CONFIG);
            deviceConfig = gson.fromJson(deviceConfigInfo, ModelConfig.class);
        }
        return deviceConfig;
    }

    public String getDeviceId(Context context) {
        if (deviceId == null) {
            deviceId = retrieveFromSharedPref(context, Constants.SHARED_PREF_DEVICE_ID);
        }
        return deviceId;
    }

    public void setDeviceId(Context context, String deviceId) {
        storeInSharedPref(context, Constants.SHARED_PREF_DEVICE_ID, deviceId);
        this.deviceId = deviceId;
    }

    public void setDeviceConfig(Context context, ModelConfig deviceConfig) {
        String deviceConfigInfo = gson.toJson(deviceConfig);
        storeInSharedPref(context, Constants.SHARED_PREF_DEVICE_CONFIG, deviceConfigInfo);
        this.deviceConfig = deviceConfig;
    }

    private void storeInSharedPref(Context context, String name, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(name, value).apply();
    }

    private String retrieveFromSharedPref(Context context, String name) {
        return context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE).getString(name, null);
    }

    private SecretKey getKeyFromKeyStore(Context context) {

        // Create and save key
        Store store = new Store(context);
        if (!store.hasKey("yombuSDKStorageKey")) {
            store.generateSymmetricKey("yombuSDKStorageKey", null);
        }

        // Get key
        return store.getSymmetricKey("yombuSDKStorageKey", null);

    }
}