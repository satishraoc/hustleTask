package com.satish.hustletest.Api;

import com.satish.hustletest.model.CartItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;

public interface CartItemsAPIs {


    @GET("5e1dc3b53600005a00c743c9")
    Call<ContactWrapper> getList();

}
