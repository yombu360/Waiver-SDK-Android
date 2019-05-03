package com.yombu.waiverlibrary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class ModelWaiverMinor {

    @SerializedName("waiver_minor_id")
    @Expose
    private String waiverMinorId;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("dob")
    @Expose
    private String dob;

    public String getWaiverMinorId() {
        return waiverMinorId;
    }

    public void setWaiverMinorId(String waiverMinorId) {
        this.waiverMinorId = waiverMinorId;
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

    public String getName() {
        return firstName + " " + lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "WaiverMinorObject{" +
                "waiverMinorId='" + waiverMinorId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dob='" + dob + '\'' +
                '}';
    }

}
