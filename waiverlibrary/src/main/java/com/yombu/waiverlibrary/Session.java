package com.yombu.waiverlibrary;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


class Session {

    private static volatile Session ourInstance = null;

    private String waiverTemplateId;
    private String receiveEmailNotification;

    private String mindbodyId;

    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String birthday;
    private String gender;
    private String ciNumber;
    private String username;
    private String password;
    private String signature;

    private String address;
    private String city;
    private String state;
    private String zip;
    private String country;

    private String guardianFirstName;
    private String guardianLastName;

    private String emergencyContactFirstName;
    private String emergencyContactLastName;
    private String emergencyContactPhone;

    private List<ModelWaiverMinor> minors;
    private List<String> hearAboutUsOptions;

    private Session() {
        minors = new ArrayList<>();
        hearAboutUsOptions = new ArrayList<>();
        reset();
    }

    public static Session getInstance() {
        if (ourInstance == null) {
            ourInstance = new Session();
        }
        return ourInstance;
    }

    public void reset() {
        waiverTemplateId = null;
        receiveEmailNotification = Constants.STATUS_NOTIFICATION_ENABLED;
        mindbodyId = null;

        firstName = null;
        lastName = null;
        phone = null;
        email = null;
        birthday = null;
        gender = null;
        ciNumber = null;
        username = null;
        password = null;
        signature = null;

        address = null;
        city = null;
        state = null;
        zip = null;
        country = null;

        guardianFirstName = null;
        guardianLastName = null;

        emergencyContactFirstName = null;
        emergencyContactLastName = null;
        emergencyContactPhone = null;

        minors.clear();
        hearAboutUsOptions.clear();
    }

    public String getWaiverTemplateId() {
        return waiverTemplateId;
    }

    public void setWaiverTemplateId(String waiverTemplateId) {
        this.waiverTemplateId = waiverTemplateId;
    }

    public String getReceiveEmailNotification() {
        return receiveEmailNotification;
    }

    public void setReceiveEmailNotification(String receiveEmailNotification) {
        this.receiveEmailNotification = receiveEmailNotification;
    }

    public String getMindbodyId() {
        return mindbodyId;
    }

    public void setMindbodyId(String mindbodyId) {
        this.mindbodyId = mindbodyId;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.DOB_FORMAT);
        this.birthday = formatter.format(birthday);
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCiNumber() {
        return ciNumber;
    }

    public void setCiNumber(String ciNumber) {
        this.ciNumber = ciNumber;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        String fullName = "";
        if (firstName != null) {
            fullName += firstName;
        }
        if (lastName != null) {
            if (fullName.isEmpty()) {
                fullName += lastName;
            } else {
                fullName += " " + lastName;
            }
        }
        return fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGuardianFirstName() {
        return guardianFirstName;
    }

    public void setGuardianFirstName(String guardianFirstName) {
        this.guardianFirstName = guardianFirstName;
    }

    public String getGuardianLastName() {
        return guardianLastName;
    }

    public void setGuardianLastName(String guardianLastName) {
        this.guardianLastName = guardianLastName;
    }

    public String getEmergencyContactFirstName() {
        return emergencyContactFirstName;
    }

    public void setEmergencyContactFirstName(String emergencyContactFirstName) {
        this.emergencyContactFirstName = emergencyContactFirstName;
    }

    public String getEmergencyContactLastName() {
        return emergencyContactLastName;
    }

    public void setEmergencyContactLastName(String emergencyContactLastName) {
        this.emergencyContactLastName = emergencyContactLastName;
    }

    public String getEmergencyContactPhone() {
        return emergencyContactPhone;
    }

    public void setEmergencyContactPhone(String emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
    }

    public void addMinor(String firstName, String lastName, Date dateOfBirth) {
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.DOB_FORMAT);
        ModelWaiverMinor waiverMinor = new ModelWaiverMinor();
        waiverMinor.setFirstName(firstName);
        waiverMinor.setLastName(lastName);
        waiverMinor.setDob(formatter.format(dateOfBirth));
        minors.add(waiverMinor);
    }

    public void clearMinors() {
        minors.clear();
    }

    public List<ModelWaiverMinor> getMinors() {
        return minors;
    }

    public String getHearAboutUsOptions() {
        if (hearAboutUsOptions.size() > 0) {
            return TextUtils.join("||", hearAboutUsOptions);
        }
        return null;
    }

    public void setHearAboutUsOptions(List<String> hearAboutUsOptions) {
        this.hearAboutUsOptions = hearAboutUsOptions;
    }
}
