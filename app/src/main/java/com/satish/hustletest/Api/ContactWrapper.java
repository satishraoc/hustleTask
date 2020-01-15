package com.satish.hustletest.Api;

import android.provider.ContactsContract;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.satish.hustletest.model.CartItem;

import java.util.List;

public class ContactWrapper {

    @SerializedName("items")
    @Expose
    private List<CartItem> contacts = null;

    public List<CartItem> getContacts() {
        return contacts;
    }

    public void setContacts(List<CartItem> contacts) {
        this.contacts = contacts;
    }

}
