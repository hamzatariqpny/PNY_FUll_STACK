package com.pny.pny67_68.ui.db;

import android.net.Uri;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.net.URI;

// Table of database
@Entity
public class Contact {

    @PrimaryKey (autoGenerate = true)
    public int id = 0;

    @ColumnInfo(name = "contact_name")
    public String contactName;

    @ColumnInfo(name = "contact_number")
    public String contactNumber;

    @ColumnInfo(name = "contacImage")
    public String imageUri;

}
