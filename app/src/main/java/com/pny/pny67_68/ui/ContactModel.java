package com.pny.pny67_68.ui;

public class ContactModel {

    private String contactName;
    private String contactNumber;
    private int contactImage;

    public ContactModel(String contactName, String contactNumber, int contactImage) {
        this.contactName = contactName;
        this.contactNumber = contactNumber;
        this.contactImage = contactImage;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public int getContactImage() {
        return contactImage;
    }
}
