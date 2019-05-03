package com.yombu.waiverlibrary;

import android.content.Context;

class LibraryTextReplacer {

    public static String getText(Context context, String text) {
        if (Terminal.getInstance(context.getApplicationContext()).isRegistered() && text != null) {
            ModelConfig deviceConfig = Terminal.getInstance(context.getApplicationContext()).getDeviceConfig(context.getApplicationContext());

            if (deviceConfig != null) {
                if (deviceConfig.getLocationName() != null) {
                    String locationName = Terminal.getInstance(context.getApplicationContext()).getDeviceConfig(context.getApplicationContext()).getLocationName().get(Constants.LANGUAGE_SHORT_CODE_ENGLISH);
                    if (locationName != null) {
                        text = text.replace(Constants.REPLACE_LOCATION_NAME, locationName);
                    }
                }
            }

            String customerName = null;
            if (Session.getInstance().getFullName() != null
                    && !Session.getInstance().getFullName().isEmpty()) {
                customerName = Session.getInstance().getFullName();
            }

            if (customerName != null) {
                text = text.replace(Constants.REPLACE_CUSTOMER_NAME, customerName);
            }
        }

        return text;
    }

}
