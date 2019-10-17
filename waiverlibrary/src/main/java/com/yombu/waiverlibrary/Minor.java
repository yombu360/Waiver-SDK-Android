package com.yombu.waiverlibrary;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Minor {

    private String firstName;
    private String lastName;
    private String birthday;
    private Map<String, String> minorData;
    private SimpleDateFormat formatter;

    public Minor(String firstName, String lastName, Date birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        formatter = new SimpleDateFormat(Constants.DOB_FORMAT, Locale.US);
        this.birthday = formatter.format(birthday);
        minorData = new HashMap<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = formatter.format(birthday);
    }

    public void addString(String key, String value) {
        String string = null;
        if (value != null) {
            string = value.trim();
        }
        minorData.put(key, string);
    }

    public void addDate(String key, Date value) {
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.DOB_FORMAT, Locale.US);
        String birthday = formatter.format(value);
        minorData.put(key, birthday);
    }

    public void addGender(String key, YombuWaiver.Gender value) {
        String gender = null;
        switch (value) {
            case MALE:
                gender = Constants.GENDER_MALE;
                break;

            case FEMALE:
                gender = Constants.GENDER_FEMALE;
                break;

            case OTHER:
                gender = Constants.GENDER_OTHER;
                break;
        }
        minorData.put(key, gender);
    }

    public void addList(String key, List<String> value) {
        String string = null;
        if (value.size() > 0) {
            string = TextUtils.join("||", value);
        }
        minorData.put(key, string);
    }

    public Map<String, String> getMinorData() {
        return minorData;
    }

    public void clearMinorData() {
        minorData.clear();
    }

}
