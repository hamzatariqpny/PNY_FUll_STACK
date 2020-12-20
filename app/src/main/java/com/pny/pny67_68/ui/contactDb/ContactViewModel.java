package com.pny.pny67_68.ui.contactDb;

import android.app.Activity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pny.pny67_68.repository.db.AppDataBase;
import com.pny.pny67_68.repository.db.Contact;

import java.util.List;

public class ContactViewModel extends ViewModel {

    AppDataBase appDataBase;

    public static final MutableLiveData<List<Contact>> ContactsLiveData = new MutableLiveData<>();

    public void initDB(Activity activity){
        appDataBase = AppDataBase.getAppDataBase(activity);
    }

    public void InsertContact(String name , String number){
        appDataBase.contactDao().insertContacts(getContact(name,number));
    }

    public void getContact(){
        ContactsLiveData.postValue(appDataBase.contactDao().getAll());
    }
    
    // return Contact entity object
    private Contact getContact(String name , String number) {

        Contact contact = new Contact();
        contact.contactName = name;
        contact.contactNumber = number;

        return contact;

    }

}
