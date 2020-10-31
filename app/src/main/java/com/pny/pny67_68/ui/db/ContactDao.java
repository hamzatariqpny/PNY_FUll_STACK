package com.pny.pny67_68.ui.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDao {

    @Query("SELECT * FROM Contact")
    List<Contact> getAll();

    @Query("SELECT * FROM Contact where id == :id")
    List<Contact> getSingleContact(int id);

    @Insert
    void insertContacts(Contact contact);

    @Delete
    void deleteContact(Contact contact);

    @Update
    void updateContact(Contact contact);

}
